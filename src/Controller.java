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
    private View view;// view
    private BricksBreaker bricksBreaker;// model

    private JFrame Frame;
    /* key controller */
    private final Set<Integer> pressedKeys = new HashSet<>();

    public Controller() {

        this.view = new View(BoundWidth - 200, BoundHeight);
        this.bricksBreaker = new BricksBreaker(BoundWidth - 200, BoundHeight);
        Frame = new JFrame();

        /* Frame settings */
        Frame.setBounds(0, 0, BoundWidth, BoundHeight);
        Frame.getContentPane().add(view);
        Frame.addKeyListener(this);
        Frame.setTitle("Bricks Breaker Java Game");
        Frame.setResizable(false);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setVisible(true);

        SetBricks();
        SetPaddle();
        SetBall();
        ShowAll();
    }

    @Override
    public synchronized void keyPressed(KeyEvent e) {// All synchronized blocks synchronized on the same object can only
        // have one thread executing inside them at a time.
        pressedKeys.add(e.getKeyCode());
        if (!pressedKeys.isEmpty()) {
            for (Iterator<Integer> it = pressedKeys.iterator(); it.hasNext();) {
                switch (it.next()) {

                    case KeyEvent.VK_RIGHT:// Right arrow key code

                        break;

                    case KeyEvent.VK_LEFT:// Left arrow key code

                        break;

                    case KeyEvent.VK_SPACE:

                        break;
                }
                SetBricks();
                SetPaddle();
                SetBall();
                ShowAll();
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
        this.view.SetPaddle(bricksBreaker.getPaddleX(), bricksBreaker.getPaddleY(), bricksBreaker.getPaddleW(),
                bricksBreaker.getPaddleH());
    }

    void SetBall() {
        this.view.SetBall(bricksBreaker.getBallX(), bricksBreaker.getBallY(), bricksBreaker.getBallR());
    }

    void ShowAll() {
        view.ShowAll();
    }
}