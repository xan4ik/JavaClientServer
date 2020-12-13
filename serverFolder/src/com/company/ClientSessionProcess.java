package com.company;

import java.io.*;
import java.net.Socket;

public class ClientSessionProcess extends  Thread {

    private Socket source;
    private IServer server;
    private BufferedReader inputStream;
    private BufferedWriter outputStream;
    private boolean stopRequired;

    public ClientSessionProcess(IServer server, Socket socket) throws IOException {
        this.source = socket;
        this.server = server;
        inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public IServer GetServer(){
        return  server;
    }

    public void SetServer(IServer server){
        this.server = server;
    }

    public void RequireStop(){
        stopRequired = true;
    }

    @Override
    public void run(){
        while (!stopRequired){
            TryApplyMessage();
        }
    }

    private void TryApplyMessage(){
        System.out.println("Message wait");
        try{
            var message = inputStream.readLine();
            BroadсastMessage(message);
        }
        catch (IOException exc)
        {
            this.interrupt();
        }
    }

    private void BroadсastMessage(String message) {
        server.BroadcastMessage(message);
    }

    public void SendMessageToClient(String message){
        try {
            outputStream.write(message);
            outputStream.newLine();
            outputStream.flush();
        }
        catch (IOException e)
        {
            server.RemoveClient(this);
            this.interrupt();
        }
    }
}
