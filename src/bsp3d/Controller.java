package bsp3d;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import engine.*;
import importer.WavefrontParser;

/**
 * @author Leonardo Ono (ono.leo80@gmail.com)
 */
public class Controller extends Canvas implements KeyListener, MouseListener, MouseMotionListener {
    private boolean[] keys = new boolean[256];
    private BufferedImage offscreenImage;
    private int[] offscreenImageBuffer;
    private BufferStrategy bs;

    private Node bspNode;
    private Color backgroundColor = new Color(0, 0, 0, 0);
    private int renderMode = 1;

    private Observer observer = new Observer();

    public Controller() {
        offscreenImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        offscreenImageBuffer = ((DataBufferInt) offscreenImage.getRaster().getDataBuffer()).getData();
    }

    public void start() {
        if (1 == 1) {
            try {
                //WavefrontParser.load("/res/Doom_E1M1.obj", 1.0);
                WavefrontParser.load("/res/test3.obj", 1.0);
                //WavefrontParser.load("/res/test4.obj", 1.0);
                //WavefrontParser.load("/res/test5.obj", 1.0);
                //WavefrontParser.load("/res/quake.obj", 1.0);

                System.out.println(WavefrontParser.obj.faces.size());
                bspNode = new Node();
                bspNode.preProcess(0, WavefrontParser.obj.faces);
            } catch (Exception ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
//            try {
//                Node.save("d:/quake.preproc", bspNode);
//            } catch (Exception ex) {
//                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
//                System.exit(-1);
//            }
        }
        else {
            try {
                bspNode = Node.load(getClass().getResourceAsStream("/res/quake.preproc"));
            } catch (Exception ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(-1);
            }
        }

        createBufferStrategy(2);
        bs = getBufferStrategy();
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean running = true;
                while (running) {
                    update();
                    Graphics2D g = (Graphics2D) bs.getDrawGraphics();
                    draw((Graphics2D) offscreenImage.getGraphics());
                    g.clearRect(0, 0, getWidth(), getHeight());
                    g.drawImage(offscreenImage, 0, 0, getWidth(), getHeight(), null);
                    g.dispose();
                    bs.show();

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
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
        //double speed = 0.05;
        speed.set(0, 0, 0);

        if (mousePressed) {
            double ry = mouseDrag.x - mouseClicked.x;
            double rx = mouseDrag.y - mouseClicked.y;
            observer.angleY += 0.5 * ((mousePressedObserverAngleY + 0.01 * ry) - observer.angleY);
            observer.angleX += 0.5 * ((mousePressedObserverAngleX + 0.01 * rx) - observer.angleX);
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
        // g.drawLine(0, 0, 800, 600);

        Area area = new Area();

        g.translate(400, 300);
        g.scale(1, -1);

        if (renderMode == 1) {
            //Composite oc = g.getComposite();
            //g.setComposite(AlphaComposite.DstOver);            
            bspNode.transverse(observer, g);
            //g.setComposite(oc);
        }
        else {
            //Collections.sort(WavefrontParser.obj.faces);
            for (Triangle triangle : WavefrontParser.obj.faces) {
                triangle.draw3D(g, observer);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Controller view = new Controller();
                JFrame frame = new JFrame();
                frame.setTitle("Java 3D BSP + Painter's Algorithm ");
                frame.setSize(800, 600);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.getContentPane().add(view);
                frame.setResizable(false);
                frame.setVisible(true);
                view.requestFocus();
                view.start();
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

    // --- mouse

    private Vec2 mouseClicked = new Vec2();
    private Vec2 mouseDrag = new Vec2();
    private boolean mousePressed;
    private double mousePressedObserverAngleX;
    private double mousePressedObserverAngleY;

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseClicked.set(e.getX(), e.getY());
        mouseDrag.set(e.getX(), e.getY());
        mousePressedObserverAngleX = observer.angleX;
        mousePressedObserverAngleY = observer.angleY;
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

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseDrag.set(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

}