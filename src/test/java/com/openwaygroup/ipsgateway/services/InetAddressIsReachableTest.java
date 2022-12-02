package com.openwaygroup.ipsgateway.services;

import org.junit.jupiter.api.Test;

import static com.openwaygroup.ipsgateway.services.InetAddressIsReachable.addressReachable;


class InetAddressIsReachableTest {
    /*
     * Address: www.google.com
     * port: 80 or 443
     * timeout: 2000 (in milliseconds)
     */
    static String host = "www.google.com";
    @Test
    void pingCheckIsReachable() {
        try {
            addressReachable(host, 80, 2000);
            System.out.println("\nOverloaded isReachable(host, port, timeout) Result ==> Ping successful for host: " + host);
        } catch (Exception e) {
            System.out.println("\nOverloaded isReachable(host, port, timeout) Result ==> Ping failed for host: " + host);
        }
    }
}