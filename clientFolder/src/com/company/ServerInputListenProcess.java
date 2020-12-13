package com.company;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;

public class ServerInputListenProcess extends Thread {

    private JTextArea outputArea;
    private BufferedReader input;
    private boolean stopRequired;

    public ServerInputListenProcess(JTextArea outputArea, BufferedReader input) {
        this.outputArea = outputArea;
        this.input = input;
    }

    public void RequiredStop(){
        stopRequired = true;
    }

    @Override
    public void run(){
        while(!stopRequired){
            TryApplyMessage();
        }
    }

    private void TryApplyMessage(){
        try {
            System.out.println("wait for message");
            var message = input.readLine();
            outputArea.setText(outputArea.getText() + "\n" + message);
        }
        catch (IOException e) { System.out.println("Message apply error"); }
    }
}
