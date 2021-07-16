package bsp3d;

import engine.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Area;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Controller extends Canvas implements KeyListener, MouseListener, MouseMotionListener {

    private boolean[] keys = new boolean[256];
    private BufferedImage offscreenImage;
    private int[] offscreenImageBuffer;
    private BufferStrategy bs;


    private Node bspNode;
    private Color backgroundColor = new Color(0, 0, 0);
    private int renderMode = 1;

    private Observer observer = new Observer();

    public Controller() {
        offscreenImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        offscreenImageBuffer = ((DataBufferInt) offscreenImage.getRaster().getDataBuffer()).getData();
    }

    public void start() {

        /*TODO: IMPORT GAME WORLD MODEL HERE*/

        createBufferStrategy(2);
        bs = getBufferStrategy();
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean running = true;
                while (running) {
                    update(); /*TODO: IMPLEMENT UPDATE LOOP*/

                    Graphics2D g = (Graphics2D) bs.getDrawGraphics();
                    draw((Graphics2D) offscreenImage.getGraphics());
                    g.clearRect(0, 0, getWidth(), getHeight());
                    g.drawImage(offscreenImage, 0, 0, getWidth(), getHeight(), null);
                    g.dispose();

                    bs.show();

                    try {
                        Thread.sleep(1);
                    } catch(InterruptedException ex) {

                    }
                }
            }
        }).start();

        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

    }


    private Vec3 speed = new Vec3();

    private void update() {
        speed.set(0, 0, 0);

        if (mousePressed) {
            double ry = mouseDrag.x - mouseClicked.x;
            double rx = mouseDrag.y - mouseClicked.y;

            observer.angleY += 0.5 * ((mousePressedPlayerAngleY + 0.01 * ry) - observer.angleY);
            observer.angleX += 0.5 * ((mousePressedPlayerAngleX + 0.01 * rx) - observer.angleX);
        }

        if (keys[KeyEvent.VK_Z]) {
            observer.angleY += 0.1;
        }
        else if (keys[KeyEvent.VK_X]) {
            observer.angleY -= 0.1;
        }
        if (keys[KeyEvent.VK_D]) {
            observer.angleX += 0.1;
        }
        else if (keys[KeyEvent.VK_C]) {
            observer.angleX -= 0.1;
        }
        observer.updateDirection();
        if (keys[KeyEvent.VK_LEFT]) {
            speed.set(observer.direction);
            speed.rotateY(Math.toRadians(-90));
            speed.scale(0.01);
        }
        else if (keys[KeyEvent.VK_RIGHT]) {
            speed.set(observer.direction);
            speed.rotateY(Math.toRadians(+90));
            speed.scale(0.01);
        }

        if (keys[KeyEvent.VK_UP]) {
            speed.set(observer.direction);
            speed.scale(0.01);
        }
        else if (keys[KeyEvent.VK_DOWN]) {
            speed.set(observer.direction);
            speed.scale(-0.01);
        }
        observer.position.add(speed);

        /*TODO: REMOVE FOR FINAL RELEASE, DEBUG MOVES ONLY*/

        if (keys[KeyEvent.VK_Q]) {
            observer.position.y -= 0.01;
        }
        else if (keys[KeyEvent.VK_A]) {
            observer.position.y += 0.01;
        }

        if (keys[KeyEvent.VK_ADD]) {
            Node.maxCount += 10;
        }
        else if (keys[KeyEvent.VK_SUBTRACT]) {
            Node.maxCount -= 10;
        }

        if (keys[KeyEvent.VK_1]) {
            renderMode = 1;
        }
        else if (keys[KeyEvent.VK_2]) {
            renderMode = 2;
        }


    }

    private void draw(Graphics2D g) {
        g.setBackground(backgroundColor);
        g.clearRect(0, 0, 800, 600);

        Area area = new Area();

        g.translate(400, 300);
        g.scale(1, -1);

        if (renderMode == 1) {
            bspNode.transverse(observer, g);
        }
        /*else {
            for (Triangle triangle: WaveFrontParser.obj.faces) {
                triangle.draw3D(g, observer);
            }
        }*/
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Controller controller = new Controller();

                JFrame frame = new JFrame();

                frame.setTitle("Space Partition! (Created by Niko Ayers and Jackson Meade)");
                frame.setSize(800, 600);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.getContentPane().add(controller);
                frame.setResizable(false);
                frame.setVisible(true);

                controller.requestFocus();

                controller.start();
            }
        });
    }



    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    private Vec2 mouseClicked = new Vec2();
    private Vec2 mouseDrag = new Vec2();
    private boolean mousePressed;
    private double mousePressedPlayerAngleX;
    private double mousePressedPlayerAngleY;

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseDrag.set(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseClicked.set(e.getX(), e.getY());
        mouseDrag.set(e.getX(), e.getY());
        mousePressedPlayerAngleX = observer.angleX;
        mousePressedPlayerAngleY = observer.angleY;
        mousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
