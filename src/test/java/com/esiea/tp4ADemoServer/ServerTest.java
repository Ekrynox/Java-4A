package com.esiea.tp4ADemoServer;

import org.junit.jupiter.api.Test;

class ServerTest {

    @Test
    void main() throws InterruptedException {
        Runnable main = () -> {
            Server server = new Server();
            server.main(new String[] {});
        };

        Thread mainT = new Thread(main);
        mainT.start();

        mainT.join();
    }
}
