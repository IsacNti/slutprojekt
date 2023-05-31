package Server;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Enkel grafik. Skapa en Canvas men skriv en egen metod för att anropa ritandet. För att kunna styra fps och ups
 * lägger vi den i egen tråd
 *
 * Created 2022-04-26
 *
 * @author Magnus Silverdal
 */

/**
 * Vi utökar klassen Canvas med vår bild och implementerar runnable så att den kan köras som en egen tråd
 */
public class grafik extends Canvas implements Runnable {
    // Variabler för tråden
    private Thread thread;
    int fps = 60;
    private boolean isRunning;
    // Skapa en buffrad grafik så att vi kan rita bilder i förväg, bättre än dbg från tidigare
    private BufferStrategy bs;
    // Storleken på bilden
    private final int height = 600;
    private final int width = 800;
    // Variabler gör det lättare att placera saker
    // värdens mitt
    int x = 400;
    int y = 300;

    // hastigheter
    int vvx = 0;
    int vvy = 0;

    // spelare posetion
    // spelar 1
    int xx = 20;
    int yy = 20;

    // spelare 2
    int xxx = 10;
    int yyy = 10;

    // inte tillagad än
    int dead = 0;
    int hälsa = 5;

    // skot
    int shot = 0;

    //spelar 2
    int ax = 2;
    int ay = 2;
    int ax1 = 2;
    int ay1 = 2;
    int ax2 = 2;
    int ay2 = 2;
    int atime = 0;
    int atime1 = 0;
    int atime2 = 0;

    //spelar 1
    int bx = 2;
    int by = 2;
    int bx1 = 2;
    int by1 = 2;
    int bx2 = 2;
    int by2 = 2;
    int bxx = 12;
    int byy = -10;
    int bxv = 0;
    int byv = -7;
    int time = 0;
    int time1 = 0;
    int time2 = 0;


    private BufferedImage tank1;
    private BufferedImage tank2;



    /**
     * Skapa ett fönster och lägg in grafiken i det.
     */
    public grafik() {
        JFrame frame = new JFrame("Titel");
        this.setSize(width, height);
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Börja med animationen avslagen
        isRunning = false;

        // Lägg till en keylistener
        this.addKeyListener(new KL());
        this.addMouseListener(new ML());
        this.addMouseMotionListener(new MML());
        this.requestFocus();
        // Läs in en bild
        try {



            tank1 = ImageIO.read(getClass().getResource("tank.png"));

            tank2 = ImageIO.read(getClass().getResource("tank2.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        grafik that = (grafik) o;
        return dead == that.dead && hälsa == that.hälsa;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dead, hälsa);
    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        double deltaT = 1000.0 / fps;
        long lastTime = System.currentTimeMillis();

        while (isRunning) {
            long now = System.currentTimeMillis();
            if (now - lastTime > deltaT) {
                update();
                time = time +1;
                time1 = time1 +1;
                time2 = time2 +1;
                lastTime = now;
            }
            paint();
        }
        stop();
    }

    /**
     * Nu gör vi en egen paint. Skapa en bufferStrategy så att vi får flera skärmar att jobba på, Java sköter det åt oss
     */
    public void paint() {
        bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        // Om vi inte suddar allt ritar vi över det som redan fanns. Ibland kan det vara bättre att bara sudda en bit
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        draw(g);
        // Det här byter skärm
        g.dispose();
        bs.show();
    }

    /**
     * Ändra värdet på de variabler som styr animationerna
     */

    private void update() {

        if (xx <= 0 || xx >= width - 3 * tank1.getWidth())
            vvx = 0;
        if (yy <= 0 || yy >= height - 3 * tank1.getHeight())
            vvy = 0;

        if (xx <= 0){
            xx = 1;
        }
        if (767 <= xx){
            xx = 766;
        }
        if (yy <= 0){
            yy = 1;
        }
        if (549 <= yy){
            yy = 548;
        }

        xx += vvx;
        yy += vvy;

        bx += bxv;
        by += byv;

        bx1 += bxv;
        by1 += byv;

        bx2 += bxv;
        by2 += byv;


    }


    /**
     * Rita gräs i nederkant av bilden
     * @param g grafiken
     */
    private void drawGrass(Graphics g) {
        g.setColor(new Color(0xFF1F1F1F));
        g.fillRect(0,250,width,height);
    }

    private void drawHeaven(Graphics g) {
        g.setColor(new Color(0xFF1F1F1F, true));
        g.fillRect(0,0,width,height);
    }
    private void Boom(Graphics g, int dx, int dy) {
        g.setColor(Color.yellow);
        g.fillRect(dx,dy,9,9);
    }
    private void Boom1(Graphics g, int dx1, int dy1) {
        g.setColor(Color.yellow);
        g.fillRect(dx1,dy1,9,9);
    }
    private void Boom2(Graphics g, int dx2, int dy2) {
        g.setColor(Color.yellow);
        g.fillRect(dx2,dy2,9,9);
    }
    /**
     * Rita ut alla saker. Ordningen är viktig eftersom vi kan rita saker på andra saker.
     *
     * @param g grafiken
     */
    private void draw(Graphics g) {
        drawHeaven(g);
        drawGrass(g);


        g.drawImage(tank1, xx, yy, 3*tank1.getWidth(), 3* tank1.getHeight(),null);
        g.drawImage(tank2, xxx,yyy, 3*tank2.getWidth(),3* tank2.getHeight(),null);
        if (time >= 0){
            if (time <= 80){
                Boom(g,bx,by);
            }
        }
        if (time1 >= 0){
            if (time1 <= 80){
                Boom1(g,bx1,by1);
            }
        }
        if (time2 >= 0){
            if (time2 <= 80){
                Boom2(g,bx2,by2);
            }
        }
        if (atime >= 0){
            if (atime <= 80){
                Boom(g,ax,ay);
            }
        }
        if (atime1 >= 0){
            if (atime1 <= 80){
                Boom1(g,ax1,ay1);
            }
        }
        if (atime2 >= 0){
            if (atime2 <= 80){
                Boom2(g,ax2,ay2);
            }
        }

    }

    /**
     * Nu kan vi starta vårt program
     * Skapa först en JFrame och en canvas, starta sedan tråden som sköter animationen.
     */
    public static void main(String[] args) {
        grafik exempel = new grafik();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                exempel.setVisible(true);
            }
        });
        exempel.start();
    }

    private class KL implements KeyListener {
        @Override
        public void keyTyped(KeyEvent keyEvent) {
            if (keyEvent.getKeyChar() == 'a') {
                System.out.print("a");
            }
        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            if (keyEvent.getKeyChar()=='a'){
                System.out.println("a");
                vvx = -5;
            }
            if (keyEvent.getKeyChar()=='d'){
                System.out.println("d");
                vvx = 5;
            }
            if (keyEvent.getKeyChar()=='w'){
                System.out.println("w");
                vvy = -5;
            }
            if (keyEvent.getKeyChar()=='s'){
                System.out.println("s");
                vvy = 5;
            }
            if (keyEvent.getKeyChar()=='k'){
                System.out.println("k");
                shot = shot + 1;
                if (shot == 1){
                    bx = bxx + xx;
                    by = byy + yy;
                    time = 1;
                }
                if (shot == 2){
                    bx1 = bxx + xx;
                    by1 = byy + yy;
                    time1 = 1;
                }
                if (shot == 20){
                    bx2 = bxx + xx;
                    by2 = byy + yy;
                    time2 = 1;
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
            if (keyEvent.getKeyChar()=='a'){
                System.out.println("a");
                vvx = 0;
            }
            if (keyEvent.getKeyChar()=='d'){
                System.out.println("d");
                vvx = 0;
            }
            if (keyEvent.getKeyChar()=='w'){
                System.out.println("w");
                vvy = 0;
            }
            if (keyEvent.getKeyChar()=='s'){
                System.out.println("s");
                vvy = 0;
            }
            if (keyEvent.getKeyChar()=='k'){
                System.out.println("k");
                shot = 0;
            }
        }
    }

    private class ML implements MouseListener {
        @Override

        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }

    private class MML implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
        }
    }
}
