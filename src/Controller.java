import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Controller implements KeyListener {
    private static final int BoundWidth = 1280, BoundHeight = 640;
    private static final int SCORE_AREA_RECT = 200;
    private View view;// view
    private BricksBreaker bricksBreaker;// model

    private JFrame Frame;
    /* key controller */
    private final Set<Integer> pressedKeys = new HashSet<>();

    public Controller() {

        this.view = new View(BoundWidth - SCORE_AREA_RECT, BoundHeight);
        this.bricksBreaker = new BricksBreaker(BoundWidth - SCORE_AREA_RECT, BoundHeight);
        Frame = new JFrame();

        /* Frame settings */
        Frame.setBounds(0, 0, BoundWidth, BoundHeight);
        Frame.getContentPane().add(view);
        Frame.addKeyListener(this);
        Frame.setTitle("Bricks Breaker Java Game");
        Frame.setResizable(false);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setVisible(true);

        RefreshView();
        Multithreading m = new Multithreading();
        m.start();
    }

    @Override
    public synchronized void keyPressed(KeyEvent e) {// All synchronized blocks synchronized on the same object can only
        // have one thread executing inside them at a time.
        pressedKeys.add(e.getKeyCode());
        if (!pressedKeys.isEmpty()) {
            for (Iterator<Integer> it = pressedKeys.iterator(); it.hasNext();) {
                switch (it.next()) {

                    case KeyEvent.VK_RIGHT:// Right arrow key code
                        bricksBreaker.getPaddle().Movement(1, BoundWidth - SCORE_AREA_RECT, BoundHeight);//right
                        break;

                    case KeyEvent.VK_LEFT:// Left arrow key code
                        bricksBreaker.getPaddle().Movement(2, BoundWidth- SCORE_AREA_RECT, BoundHeight);//left
                        break;

                    case KeyEvent.VK_SPACE:

                        break;
                }
                RefreshView();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
    }

    void SetBricks() {
        this.view.SetBricks(bricksBreaker.getbricksX(), bricksBreaker.getbricksY(), bricksBreaker.getBW(),
                bricksBreaker.getBH());
    }

    void SetPaddle() {
        this.view.SetPaddle(bricksBreaker.getPaddle().getX(), bricksBreaker.getPaddle().getY(), bricksBreaker.getPaddle().getWidth(),
                bricksBreaker.getPaddle().getHeight());
    }

    void SetBall() {
        this.view.SetBall(bricksBreaker.getBall().getX(), bricksBreaker.getBall().getY(), bricksBreaker.getBall().getRadius());
    }

    void SetScore(){
        view.SetScore(bricksBreaker.getScore());
    }
    void ShowAll() {
        view.ShowAll();
    }

    void RefreshView(){
        SetBricks();
        SetPaddle();
        SetBall();
        SetScore();
        ShowAll();
    }

    class Multithreading extends Thread
    {
        public void run()
        {
            while(true) {
                bricksBreaker.scoreInc(bricksBreaker.getBall().Movement(bricksBreaker.getBricks(), bricksBreaker.getPaddle(),
                        BoundWidth - SCORE_AREA_RECT, BoundHeight));
                RefreshView();
                try {
                    TimeUnit.MILLISECONDS.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}