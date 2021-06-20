package mainPart;

import commands.*;
import collection.*;
import exceptions.ConnectionException;
import exceptions.IdNotFoundException;
import exceptions.IncorrectInputDataException;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.channels.SocketChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ServerPart {

    private final ServerMaker serverMaker;
    private final DataBaseWorker dataBaseWorker;
    private static boolean isClientConnected = true;
    public boolean isItWaiting = false;
    //public Set<String> waitingUsers = new HashSet<>();

    public ServerPart (ServerMaker serverMaker) {
        dataBaseWorker = new DataBaseWorker();
        this.serverMaker = serverMaker;
    }

    public Serializable waitForRead(String userName, Serializable classToRead) throws  ConnectionException {
        while(true) {
            for (Serializable serializable : serverMaker.getResponses().keySet()) {
                if (serializable != null) {
                    try {
                        if (serverMaker.getResponses().get(serializable).equals(userName) && serializable.getClass() == classToRead.getClass()) {
                            serverMaker.removeResponse(serializable);
                            isItWaiting = false;
                            return serializable;
                        }
                    } catch (NullPointerException e) {e.printStackTrace();}
                }

            }
        }
    }

    public void waitForWrite(Serializable request, String userName) throws ConnectionException {
        serverMaker.addRequest(userName, request);
        while(serverMaker.getRequests().contains(request)) {};
        isItWaiting = false;
    }

    private void readCommand(Command command, String userName) {
        try {
            String s = check(command, userName);
            serverMaker.addRequest(userName, s);
        } catch (ConnectionException e) {
            System.out.println(e.getMessage());
            isClientConnected = false;
        }
    }

    public void readCommands() {
        ExecutorService service = Executors.newFixedThreadPool(4);
        while (isClientConnected) {
            try {
                try {
                for (Serializable serializable : serverMaker.getResponses().keySet()) {
                    Command command = (Command) serializable;
                        service.execute(() -> {
                            if (command != null) {
                                String user = serverMaker.getResponses().get(serializable);
                                serverMaker.removeResponse(serializable);
                                if (user != null) {
                                    readCommand(command, user);
                                }
                            }
                        });
                }
                } catch (ClassCastException e) {
                    //System.out.println(serverMaker.getResponses());
                }
            } catch(ConnectionException e) {
                System.out.println(e.getMessage());
                isClientConnected = false;
            }
        }
        service.shutdown();

    }

    private String check(Command command, String userName) {
        try {
            String s = "";
            try {
            if (command != null) {
                ((CommandWithoutAdditionalArgument) command).updateCollection(dataBaseWorker.getTickets());
                Ticket newTicket = null;

                if (command.getClass() == AddCommand.class) {
                    newTicket = ((AddCommand) command).ticket;
                }
                if (command.getClass() == AddIfMaxCommand.class && ((AddIfMaxCommand) command).canNewTicketBeAdded()) {
                    newTicket = ((AddIfMaxCommand) command).ticket;
                }
                if (command.getClass() == UpdateCommand.class) {
                    s = command.execute();
                    newTicket = ((UpdateCommand) command).ticket;
                }
                if (newTicket != null) {
                    updateTicketFields(newTicket, userName);
                    dataBaseWorker.addTicketToDB(newTicket, userName);
                    s += "Ticket created";
                }
                if (command.getClass() != UpdateCommand.class) s += command.execute(userName);
            }
            if (((CommandWithoutAdditionalArgument)command).isCollectionChanged()) dataBaseWorker.updateDB();
            return s;
        } catch (IdNotFoundException e) {return e.getMessage();}
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return "Не удалось это сделать Ticket created";
        }
    }

    private void updateTicketFields(Ticket ticket, String userName) throws SQLException {
        String s;
        String response = "";
        ticket.setCoordinates(null);
        ticket.setType(null);
        if (ticket.getOwner() != null && !ticket.getOwner().equals(userName)) throw new SQLException();
        ticket.setOwner(userName);
        do {
            waitForWrite("Enter the ticket name: ", userName);
            s = (String) waitForRead(userName, "");
            ticket.setName(s);
        } while(s.equals(""));

        do {
            waitForWrite(response + "Enter the coordinates: (in the format x y)", userName);
            response = "";
            String p = (String) waitForRead(userName, "");
            String[] j = p.split(" ");
            String[] jj = p.split("\t");
            if (jj.length > j.length) j = jj;
            try {
                if (j.length != 2) response += "Enter the correct number of arguments \n";
                else if (Double.parseDouble(j[0]) > -48 && Double.parseDouble(j[1]) > -48) ticket.setCoordinates(new Coordinates(Double.parseDouble(j[0]), Double.parseDouble(j[1])));
                else response += "Enter the correct values x и y (they must be greater than -48) \n";
            } catch (NumberFormatException e) {response += "Enter the correct values x и y (they must be greater than -48) \n";}

        } while(ticket.getCoordinates() == null);

        if (ticket.getId() != 0 || ticket.getPrice() == null) {
            ticket.setPrice(-1.0);
            do {
                waitForWrite(response + "Enter the ticket price: (it must be greater than 0)", userName);
                response = "";
                String[] j = ((String) waitForRead(userName, "")).split(" ");
                try {
                    if (j.length == 1) ticket.setPrice(Double.parseDouble(j[0]));
                    else {
                        response += "Enter the correct number \n";
                    }
                } catch (NumberFormatException e) {
                    response += "Enter the correct price \n";
                }
            } while (ticket.getPrice() <= 0);
        }
        do {
            waitForWrite(response + "Enter the ticket type: (leave the field blank if you want) \nList of possible types: VIP, USUAL, BUDGETARY, CHEAP", userName);
            response = "";
            s = (String) waitForRead(userName, "");
            if (!s.equals("")) {
                try {
                    ticket.setType(TicketType.valueOf(s.toUpperCase()));
                } catch (IllegalArgumentException e) { response += "Enter the correct type name \n";}
            }
        } while (ticket.getType() == null && !s.equals(""));

        waitForWrite("Where to buy a ticket? (if you do not want to enter, leave the field empty, and write the name of the place to continue)", userName);
        s = (String) waitForRead(userName, "");

        if (!s.equals("")) {
            ticket.setVenue(new Venue(s));
            s = "";
            do {
                waitForWrite(response + "Enter the audience capacity: (leave it blank if it is unknown)", userName);
                response = "";
                try {
                    s = (String) waitForRead(userName, "");
                    if (s != null && !s.equals("") && Integer.parseInt(s) > 0) ticket.getVenue().setCapacity(Integer.parseInt(s));
                } catch (NumberFormatException | NullPointerException e) {response += "Enter the correct value \n";}
            } while (ticket.getVenue().getCapacity() == null && s != null && !s.equals(""));

            do {
                waitForWrite(response + "Enter the audience type: (leave it blank if it is unknown) \nList of possible types:   BAR, LOFT, THEATRE, MALL, STADIUM", userName);
                response = "";
                s = (String) waitForRead(userName, "");
                if (!s.equals("")) {
                    try {
                        ticket.getVenue().setType(VenueType.valueOf(s.toUpperCase()));
                    } catch (IllegalArgumentException e) {response += "Enter the correct type name \n";}
                }
            } while (ticket.getVenue().getType() == null && !s.equals(""));
        }
    }

    public DataBaseWorker getDataBaseWorker() {return dataBaseWorker;}
    public boolean isIsClientConnected() {return isClientConnected;}


}
