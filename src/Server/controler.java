package Server;




import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class controler extends JFrame {

    Server Server;
    grafik grafik;
    public String ip;
    public controler(Server m, grafik g) throws InterruptedException {
        this.Server = m;
        this.grafik = g;
        this.pack();
        Server s = new Server(4202);
        s.acceptClient();
        s.getStreams();
        ListenerThread l = new ListenerThread(s.in, System.out);
        Thread listener = new Thread(l);
        listener.start();
        Client.grafik exempel = new Client.grafik();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                exempel.setVisible(true);
            }
        });
        exempel.start();
        s.runProtocol();
        listener.join();
        s.shutdown();

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Server m = new Server();
        grafik g = new grafik();
        controler thisIsTheProgram = new controler(m,g);
        thisIsTheProgram.setVisible(true);

    }


}

