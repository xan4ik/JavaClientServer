package com.company;

import java.net.ServerSocket;
import java.net.Socket;

public interface IServer{
    void ApplyClient(ClientSessionProcess client);
    void RemoveClient(ClientSessionProcess client);
    void BroadcastMessage(String message);
    ServerSocket ServerDomain();
}
