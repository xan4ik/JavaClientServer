package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements IServer {

    private ServerSocket domain;
    private ArrayList<ClientSessionProcess> clients;
    private ServerListenProcess listenProcess;

    public Server(){
        clients = new ArrayList<ClientSessionProcess>();
    }

    public void CreateServer(int port) throws  IOException {
            domain = new ServerSocket(port);
    }

    public void ShutDown() throws IOException, NullPointerException {
       if(domain != null){
           listenProcess.interrupt();
           for (var session: clients) {
               session.interrupt();
           }
           domain.close();
       }
       else throw new NullPointerException("Didn't create the server");
    }

    public void Process() throws NullPointerException {
        if(domain != null) {
            listenProcess = new ServerListenProcess(this);
            listenProcess.start();
        }
        else throw new NullPointerException("Didn't create the server");
    }

    @Override
    public void ApplyClient(ClientSessionProcess client) {
        if(!clients.contains(client)){
            clients.add(client);
        }
        else System.out.println("Can't add client in second time");
    }

    @Override
    public void RemoveClient(ClientSessionProcess client) {
        if(clients.contains(client)){
            clients.remove(client);
        }
        else System.out.println("Client didn't register");
    }

    @Override
    public void BroadcastMessage(String message) {
        for (ClientSessionProcess session: clients) {
            session.SendMessageToClient(message);
      }
    }

    @Override
    public ServerSocket ServerDomain() throws NullPointerException {
        if(domain != null){
            return domain;
        }
        else throw new NullPointerException("Didn't create the server");
    }
}
