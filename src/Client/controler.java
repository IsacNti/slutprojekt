package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class controler extends JFrame {

    Client Client;
    grafik grafik;
    public String ip;
    public controler(Client m, grafik g) throws InterruptedException {
        this.Client = m;
        this.grafik = g;
        this.pack();
        Client me = new Client("10.80.47.196",4202);
        me.getStreams();
        ListenerThread l = new ListenerThread(me.in, System.out);
        Thread listener = new Thread(l);
        listener.start();
        grafik exempel = new grafik();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                exempel.setVisible(true);
            }
        });
        exempel.start();
        me.runProtocol();
        listener.join();
        me.shutDown();

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Client m = new Client();
        grafik g = new grafik();
        controler thisIsTheProgram = new controler(m,g);
        thisIsTheProgram.setVisible(true);

    }


}
