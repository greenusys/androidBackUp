package com.icosom.social.Icosom_Chat_Package.activity.Network_Package;

import com.github.nkzawa.engineio.client.transports.WebSocket;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class Socket_URL {

    Socket socket;
    public Socket_URL() {

            try {
                socket = IO.socket("https://icosom.icosom.com");
            } catch (URISyntaxException e) {
                System.out.println("kaif_socket_exception"+e);
            }
        }

    public Socket getmSocket() {
        return socket;
    }
}
