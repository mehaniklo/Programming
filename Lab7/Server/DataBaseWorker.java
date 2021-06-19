package Server;

import Folder.collection.Coordinates;
import Folder.collection.Ticket;
import Folder.collection.TicketType;
import Folder.collection.VenueType;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DataBaseWorker {

    private final String url = "jdbc:postgresql://pg:5432/studs";
    private final String name = "s312713";
    private final String password = "kam937";
    private LinkedList<Ticket> tickets = new LinkedList<>();
    private Connection connection = null;

    public void createNewAccountInDB(String userName, String userPassword) throws NoSuchAlgorithmException, SQLException {
        String cryptedPassword = cryptData(userPassword);
        connection.prepareStatement(String.format("INSERT INTO users VALUES ('%s','%s');", userName, cryptedPassword)).executeUpdate();
    }
    public void checkAccountInDB(String userName, String password) throws SQLException, NoSuchAlgorithmException {
        if (userName.equals("alreadyLoggedInUser")) throw new SQLException("Invalid user name");
        ResultSet users = connection.prepareStatement(String.format("SELECT * FROM users WHERE login = '%s';", userName)).executeQuery();
        String cryptPassword = cryptData(password);
        int k = 0;
        while (users.next()) {
            k++;
            if (!users.getString("password").equals(cryptPassword)) throw new SQLException("Invalid password");
        }
        if (k == 0) throw new SQLException("No user with this name found");
    }
    public String cryptData(Serializable data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] cryptPassword = md.digest(ServerMaker.serialize(data + "UniqueSalt"));
        //System.out.println(cryptPassword);
        BigInteger no = new BigInteger(1, cryptPassword);
        return no.toString(16);
    }
    public void connectToDB() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        System.out.println("The driver is connected");
        connection = DriverManager.getConnection(url, name, password);
        System.out.println("Connection established");

    }

    public void getCollectionFromDB() throws SQLException {
        ResultSet resultTicketSet = connection.prepareStatement("SELECT * FROM tickets;").executeQuery();
        while (resultTicketSet.next()) {
            int ticketId = resultTicketSet.getInt("id");
            String ticketOwner = resultTicketSet.getString("owner");
            String ticketName = resultTicketSet.getString("name");
            Double ticketCoordinateX = resultTicketSet.getDouble("coordinateX");
            double ticketCoordinateY = resultTicketSet.getDouble("coordinateY");
            Double ticketPrice = resultTicketSet.getDouble("price");
            TicketType ticketType = null;
            try {
                ticketType = TicketType.valueOf(resultTicketSet.getString("type"));
            } catch (NullPointerException e) {}
            Integer venueId = resultTicketSet.getInt("venueId");
            String venueName = null;
            Integer venueCapacity = null;
            VenueType venueType = null;
            if (venueId != 0) {
                ResultSet resultVenueSet = connection.prepareStatement(String.format("SELECT * FROM venues WHERE id = '%s';", venueId)).executeQuery();
                resultVenueSet.next();
                venueName = resultVenueSet.getString("name");
                venueCapacity = resultVenueSet.getInt("capacity");
                venueType = VenueType.valueOf(resultVenueSet.getString("type"));
            }
            Ticket ticket = new Ticket(ticketId, ticketOwner, ticketName, new Coordinates(ticketCoordinateX, ticketCoordinateY), ticketPrice, ticketType, venueId, venueName, venueCapacity, venueType);
            tickets.add(ticket);
        }
        CommandDecoder.sort(tickets);
    }
    public void addTicketToDB(Ticket ticket, String userName) throws SQLException {
        if (ticket.getId() == 0) {
            Integer venueId = null;
            if (ticket.getVenue() != null) {
                connection.prepareStatement(String.format("INSERT INTO venues (name, capacity, type) VALUES ('%s', '%s', '%s');", ticket.getVenue().getName(), ticket.getVenue().getCapacity().toString().replace(',', '.'), ticket.getVenue().getType()).replace("'null'", "NULL")).executeUpdate();
                ResultSet resultSet = connection.prepareStatement(String.format("SELECT id FROM venues WHERE name = '%s'", ticket.getVenue().getName())).executeQuery();
                while (resultSet.next()) {
                    venueId = resultSet.getInt("id");
                };

            }
            connection.prepareStatement(String.format("INSERT INTO tickets (owner, name, \"coordinateX\", \"coordinateY\", price, type, \"venueId\") VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s');",
                    ticket.getOwner(), ticket.getName(), ticket.getCoordinates().getX().toString().replace(',','.'),
                    ticket.getCoordinates().getY().toString().replace(',','.'),
                    ticket.getPrice().toString().replace(',','.'), ticket.getType(),
                    venueId).replace("'null'", "NULL")).executeUpdate();
            setTickets(new LinkedList<>());
            getCollectionFromDB();

        } else updateTicketInDB(ticket, userName);
    }

    public void updateTicketInDB(Ticket ticket, String userName) throws SQLException {
        if (!ticket.getOwner().equals(userName)) throw new SQLException();
        if (ticket.getVenue() != null) {
            connection.prepareStatement(String.format("UPDATE venues SET name = '%s', capacity = '%s', type = '%s' WHERE id = '%s';", ticket.getVenue().getName(), ticket.getVenue().getCapacity().toString().replace(',', '.'), ticket.getVenue().getType(), ticket.getVenue().getId()).replace("'null'","NULL")).executeUpdate();
        }
            connection.prepareStatement(String.format("UPDATE tickets SET owner = '%s', name = '%s', \"coordinateX\" = '%s', \"coordinateY\" = '%s', price = '%s', type = '%s' WHERE id = '%s';",
                    ticket.getOwner(), ticket.getName(), ticket.getCoordinates().getX().toString().replace(',','.'),
                    ticket.getCoordinates().getY().toString().replace(',','.'),
                    ticket.getPrice().toString().replace(',','.'), ticket.getType(),
                    ticket.getId()).replace("'null'", "NULL")).executeUpdate();
    }

    public void removeTicketFromDB(Ticket ticket) throws SQLException {
        connection.prepareStatement(String.format("DELETE FROM tickets WHERE id = '%s';", ticket.getId())).executeUpdate();
        if (ticket.getVenue() != null) connection.prepareStatement(String.format("DELETE FROM venues WHERE id = '%s';", ticket.getVenue().getId())).executeUpdate();
    }

    public void updateDB() throws SQLException {
        LinkedList<Ticket> newTickets = getTickets();
        setTickets(new LinkedList<>());
        getCollectionFromDB();
        Set<Integer> ticketId = new HashSet<>();
        for (Ticket ticket : newTickets) {
            ticketId.add(ticket.getId());
        }
        for (Ticket ticket : getTickets()) {
            if (!ticketId.contains(ticket.getId())) removeTicketFromDB(ticket);
        }
        setTickets(newTickets);
    }

    public synchronized LinkedList<Ticket> getTickets() {return tickets;}
    public synchronized void setTickets(LinkedList<Ticket> tickets) {this.tickets = tickets;}
}
