package testPack1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/*
    try {

        } catch (IOException e){
            System.err.println("");
            e.printStackTrace();
         }
            System.out.println("");
    */

public class clients {
    public Socket server;
    public ObjectOutputStream clientOut;
    public ObjectInputStream clientIn;

    public int port = 12345;
    public String ip = "10.80.47.196";

    public clients(){
        try {
            server = new Socket(ip, port);

        } catch (IOException e){
            System.err.println("Clients: Kunde inte koppla till servern!");
            e.printStackTrace();
        }
        System.out.println("Clients: kopplad till servern ...");
    }

    public void getStreams(){
        try {
            clientOut = new ObjectOutputStream(server.getOutputStream());
            clientIn = new ObjectInputStream(server.getInputStream());
        } catch (IOException e){
            System.err.println("Client: Did not get server streams!");
            e.printStackTrace();
        }
        System.out.println("Client: Have server streams ...");
    }
    public void runProtocol(){
        try {
            String messageOut = "Hej server";
            clientOut.writeObject(messageOut);
            clientOut.flush();
        } catch (IOException e){
            System.err.println("Clients: Kunde inte skicka msg!");
            e.printStackTrace();
        }
        System.out.println("Clients: Kunde skicka msg ...");
    }
    public void msg(){
        try {
            String msgIn = (String) clientIn.readObject();
            System.out.println(msgIn);
        } catch (IOException | ClassNotFoundException e){
            System.err.println("");
            e.printStackTrace();
        }
        System.out.println("");
    }

}