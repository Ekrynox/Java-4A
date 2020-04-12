package com.esiea.tp4ADemoServer;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class ServerTest {

    @Test
    void main() throws InterruptedException {
        Runnable main = () -> {
            try {
                Server.main(new String[] {});
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        Thread mainT = new Thread(main);
        mainT.start();

        Thread.sleep(10000);
        mainT.stop();
    }
}
