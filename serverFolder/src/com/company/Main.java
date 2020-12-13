package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        Server server = new Server();
        try {
            server.CreateServer(5050);
            server.Process();
        }
        catch (IOException exc){
            System.out.println("Port isn't exist");
        }
    }
}
