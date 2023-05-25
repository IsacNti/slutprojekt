package testPack;

import com.sun.corba.se.spi.activation.Server;

import java.io.*;
import java.net.*;

public class server {
    public String host = "localhost";
    public int port = 12345;
    public ServerSocket server;
    public Socket clients;
    public ObjectOutputStream serverOut;
    public ObjectInputStream serverIn;

    /*
    try {

        } catch (IOException e){
            System.err.println("");
            e.printStackTrace();
         }
            System.out.println("");
    */

    public server() {
        try {
            server = new ServerSocket(port, 50, InetAddress.getByName(host));
        } catch (IOException e){
            System.err.println("Server: Kunde inte starta!");
            e.printStackTrace();
        }
        System.out.println("Server: Servern är startad ...");
    }

    public void acceptClients(){
        try {
            clients = server.accept();
            System.out.println(InetAddress.getByName(host));
        } catch (IOException e){
            System.err.println("Server: Did not accept clients!");
            e.printStackTrace();
        }
        System.out.println("Server: Clients accepted ...");
    }

    public void getStreams(){
        try {
            serverOut = new ObjectOutputStream(clients.getOutputStream());
            serverIn = new ObjectInputStream(clients.getInputStream());
        } catch (IOException e){
            System.err.println("Server: Did not get clients streams!");
            e.printStackTrace();
        }
        System.out.println("Server: Have server clients ...");
    }

    public void runProtocol(){
        try {
            String messageOut = "Hej clients";
            serverOut.writeObject(messageOut);
            serverOut.flush();
        } catch (IOException e){
            System.err.println("Server: Kunde inte skicka msg!");
            e.printStackTrace();
        }
        System.out.println("Server: Kunde skicka msg ...");
    }
    public void msg(){
        try {
            String msgIn = (String) serverIn.readObject();
            System.out.println(msgIn);
        } catch (IOException | ClassNotFoundException e){
            System.err.println("");
            e.printStackTrace();
        }
        System.out.println("");
    }
}