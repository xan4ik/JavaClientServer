package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class Main extends JFrame {
    private JTextField inputField;
    private JTextArea outputField;
    private JButton submitButton;
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private ServerInputListenProcess inputListen;


    public static void main(String[] args) {
        new Main();
    }

    private Main()
    {
        InitContent();
        try{
            InitSocket();
            InitInputListener();
        }
        catch (IOException exc){
            JOptionPane.showMessageDialog(this, "No server connection");
        }
    }

    private void InitInputListener() {
        inputListen = new ServerInputListenProcess(outputField, reader);
        inputListen.start();
    }

    private void  InitSocket() throws IOException {
        socket = new Socket("localhost", 5050);
        reader  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    private void InitContent() {
        inputField = new JTextField();
        outputField = new JTextArea();
        submitButton = new JButton();

        outputField.setLocation(20, 20);
        outputField.setSize(340, 300);
        outputField.setEnabled(false);

        inputField.setLocation(20, 330);
        inputField.setSize(260, 20);

        submitButton.setLocation(285,330);
        submitButton.setSize(80,20);
        submitButton.setText("send");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    writer.write(inputField.getText());
                    writer.newLine();
                    writer.flush();
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(new JFrame(), "Can't send the message");
                }
            }
        });

        setLayout(null);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setSize(400, 400);
        this.add(inputField);
        this.add(outputField);
        this.add(submitButton);
        this.setVisible(true);
    }
}
