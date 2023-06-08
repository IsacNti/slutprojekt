package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    Socket socket;
    PrintWriter out;
    BufferedReader in;
    int mxxx = 10;
    int myyy = 10;

    public int max = 2;
    public int may = 2;
    public int max1 = 2;
    public int may1 = 2;
    public int max2 = 2;
    public int may2 = 2;
    public int matime = 0;
    public int matime1 = 0;
    public int matime2 = 0;

    public Client(String ip, int port) {
        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
            System.err.println("Failed to connect to server");
            e.printStackTrace();
        }
        System.out.println("Connection ready...");
    }

    public Client() {

    }


    public void getStreams() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Streams ready...");
    }

    public void runProtocol(grafik g) {
        Scanner tgb = new Scanner(System.in);
        System.out.println("chatting...");
        while (true) {
            if (max == g.ax) {

            }
            else{
                max = g.ax;
                out.println("CLIENT: " + max);
            }
            if (max1 == g.ax1) {
            }
            else{
                max1 = g.ax1;
                out.println("CLIENT: " + max1);
            }
            if (max2 == g.ax2) {
            }
            else{
                max2 = g.ax2;
                out.println("CLIENT: " + max2);
            }
            if (may == g.ay) {
            }
            else{
                may = g.ay;
                out.println("CLIENT: " + may);
            }
            if (may1 == g.ay1) {
            }
            else{
                may1 = g.ay1;
                out.println("CLIENT: " + may1);
            }
            if (may2 == g.ay2) {
            }
            else{
                may2 = g.ay2;
                out.println("CLIENT: " + may2);
            }
            if (matime == g.atime){
            }
            else{
                matime = g.atime;
                out.println("CLIENT: " + matime);
            }
            if (matime1 == g.atime1){
            }
            else{
                matime1 = g.atime1;
                out.println("CLIENT: " + matime1);
            }
            if (matime2 == g.atime2){
            }
            else{
                matime2 = g.atime2;
                out.println("CLIENT: " + matime2);
            }
            if (g.getXxx() != mxxx){

            }
            else{
                mxxx = g.getXxx();
                out.println("CLIENT: " + g.getXxx());

            }
            if (myyy == g.yyy){
            }
            else {
                myyy = g.yyy;
                out.println("CLIENT: " + myyy);
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Client me = new Client("172.20.10.2", 4202);
        me.getStreams();
        ListenerThread l = new ListenerThread(me.in, System.out);
        Thread listener = new Thread(l);
        listener.start();

        listener.join();
        me.shutDown();
    }

    public void shutDown() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

