import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Controller implements KeyListener {
    private static final int BoundWidth = 1056, BoundHeight = 640;
    private static final int SCORE_AREA_RECT = 200;
    private View view;// view
    private BricksBreaker bricksBreaker;// model

    private JFrame Frame;
    /* key controller */
    private final Set<Integer> pressedKeys = new HashSet<>();
    private boolean isMultitaskRunning = false;
    Multithreading thread;

    public Controller(Color bricksColor, Color backGroundColor, Color paddleColor, Color ballColor) {

        this.view = new View(BoundWidth - SCORE_AREA_RECT, BoundHeight, bricksColor, backGroundColor, paddleColor,
                ballColor);
        this.bricksBreaker = new BricksBreaker(BoundWidth - SCORE_AREA_RECT, BoundHeight);
        Frame = new JFrame();

        /* Frame settings */
        Frame.setBounds(0, 0, BoundWidth, BoundHeight);
        Frame.getContentPane().add(view);
        Frame.addKeyListener(this);
        Frame.setTitle("Bricks Breaker Java Game");
        Frame.setResizable(false);
        Frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Exit(Frame);
            }
        });
        Frame.setVisible(true);

        RefreshView();
        CreateThread();
    }

    @Override
    public synchronized void keyPressed(KeyEvent e) {// All synchronized blocks synchronized on the same object can only
        // have one thread executing inside them at a time.
        pressedKeys.add(e.getKeyCode());
        if (!pressedKeys.isEmpty()) {
            for (Integer pressedKey : pressedKeys) {
                switch (pressedKey) {

                    case KeyEvent.VK_RIGHT:// Right arrow key code
                        bricksBreaker.getPaddle().Movement(1, BoundWidth - SCORE_AREA_RECT, BoundHeight);// right
                        break;

                    case KeyEvent.VK_LEFT:// Left arrow key code
                        bricksBreaker.getPaddle().Movement(2, BoundWidth - SCORE_AREA_RECT, BoundHeight);// left
                        break;

                    case KeyEvent.VK_SPACE:
                        isMultitaskRunning = false;
                        Menu();
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

    private void SetBricks() {
        this.view.SetBricks(bricksBreaker.getbricksX(), bricksBreaker.getbricksY(), bricksBreaker.getbricksHit(),
                bricksBreaker.getBW(), bricksBreaker.getBH());
    }

    private void SetPaddle() {
        this.view.SetPaddle(bricksBreaker.getPaddle().getX(), bricksBreaker.getPaddle().getY(),
                bricksBreaker.getPaddle().getWidth(), bricksBreaker.getPaddle().getHeight());
    }

    private void SetBall() {
        this.view.SetBall(bricksBreaker.getBall().getX(), bricksBreaker.getBall().getY(),
                bricksBreaker.getBall().getRadius());
    }

    private void SetScores() {
        view.SetScore(bricksBreaker.getScore(), bricksBreaker.getHighScore());
    }

    private void SetLevel() {
        view.SetLevel(bricksBreaker.getLevel());
    }

    private void ShowAll() {
        view.ShowAll();
    }

    private void CheckLevelChange() {
        bricksBreaker.LevelUpIfBricksEmpty();
    }

    private void RefreshView() {
        CheckLevelChange();
        SetLevel();
        SetBricks();
        SetPaddle();
        SetBall();
        SetScores();
        ShowAll();
    }

    void Menu() {
        Frame.setEnabled(false);
        JFrame Frame2;
        Frame2 = new JFrame();
        /* Frame settings */
        Frame2.setSize(300, 300);
        Frame2.setTitle("Settings");
        Frame2.setResizable(false);
        Frame2.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Frame.setEnabled(true);
                Resume(Frame2);
                Frame2.dispose();
            }
        });
        Frame2.setLayout(null);
        Frame2.setVisible(true);

        JButton resume = new JButton("Resume");
        resume.addActionListener(new ActionListener() {// add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {
                Resume(Frame2);
            }
        });
        resume.setBounds(100, 50, 100, 30);

        JButton exit = new JButton("Exit Game");
        exit.addActionListener(new ActionListener() {// add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {
                Exit(Frame2);
            }
        });
        exit.setBounds(100, 100, 100, 30);

        Frame2.add(resume);
        Frame2.add(exit);
    }

    public static int okcancel(String theMessage) {
        int result = JOptionPane.showConfirmDialog((Component) null, theMessage, "alert", JOptionPane.OK_CANCEL_OPTION);
        return result;
    }

    private void Resume(JFrame thisFrame) {
        isMultitaskRunning = true;
        CreateThread();
        Frame.setEnabled(true);
        thisFrame.dispose();
    }

    private void Exit(JFrame thisFrame) {
        if (!Frame.equals(thisFrame)) {
            if (okcancel("Are your sure to exit game?") == 0) {
                isMultitaskRunning = false;
                bricksBreaker = null;
                view = null;
                Frame.dispose();
                thisFrame.dispose();
            }
        } else {
            isMultitaskRunning = false;
            bricksBreaker = null;
            view = null;
            Frame.dispose();
            thisFrame.dispose();
        }
    }

    private void CreateThread() {
        thread = new Multithreading();
        thread.start();
    }

    class Multithreading extends Thread {
        public void run() {
            isMultitaskRunning = true;
            while (isMultitaskRunning) {
                int score = bricksBreaker.getBall().Movement(bricksBreaker.getBricks(), bricksBreaker.getPaddle(),
                        BoundWidth - SCORE_AREA_RECT, BoundHeight);
                if (score >= 0 && bricksBreaker.getLevel() <= 5) {
                    bricksBreaker.scoreInc(score);
                    RefreshView();
                } else if (bricksBreaker.getLevel() > 5) {
                    JOptionPane.showMessageDialog(Frame, "You won!!!");
                    Exit(Frame);
                } else {
                    JOptionPane.showMessageDialog(Frame, "Game Over!!!");
                    Exit(Frame);
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}