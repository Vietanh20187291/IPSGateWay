package com.openwaygroup.ipsgateway.services;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class InetAddressIsReachable {
    /*
     * Overriding default InetAddress.isReachable() method to add 2 more arguments port and timeout value
     */
    public static boolean addressReachable(String address, int port, int timeout) throws IOException {
        Socket socket = new Socket();
        try {
           socket.connect(new InetSocketAddress(address, port), timeout);
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }

    }
}
