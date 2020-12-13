package com.company;

import java.io.IOException;
import java.net.Socket;

public class ServerListenProcess extends Thread
{
    private IServer server;
    private boolean stopRequired;

    public ServerListenProcess(IServer domain) {
        this.server = domain;
    }

    public void RequireStop(){
        stopRequired = true;
    }

    @Override
    public void run(){
        while(!stopRequired){
            TryAcceptClient();
        }
    }

    private void TryAcceptClient(){
        System.out.println("Wait for client");
        try{
            var clientSocket = server.ServerDomain().accept();
            var client = CreateClientConnection(clientSocket);
            CompleteClient(client);
        }
        catch (IOException exc){
            System.out.println("Apply client exception: " +  exc.getMessage());
        }
        catch (NullPointerException exc){
            this.interrupt();
            System.out.println("Apply client exception: " +  exc.getMessage());
        }
    }

    private ClientSessionProcess CreateClientConnection(Socket socket) throws IOException{
        var client = new ClientSessionProcess(server, socket);
        return client;
    }

    private void CompleteClient(ClientSessionProcess client){
        server.ApplyClient(client);
        client.start();
        System.out.println("Client accepted");
    }
}
