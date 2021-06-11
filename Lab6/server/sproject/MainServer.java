package server.sproject;

import server.commands.AllCommands;

public class MainServer {
    public static void main(String[] args) throws Exception {
        AllCommands allCommands = new AllCommands();
//        Serv2 serv2 = new Serv2(allCommands);
//        serv2.run();
        //allCommands.reading(args);
        ServerSide serverSide = new ServerSide(allCommands);
        serverSide.run();
    }
}
