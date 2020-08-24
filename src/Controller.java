import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
/**
 * in this class, the user inputs are catered
 * the class deal with the game(model/logic) and the view(user experience)
 * Controller communicate between game and view and pass data back forth between them.
 */
public class Controller implements KeyListener {
    private static final int BoundWidth = 1056, BoundHeight = 640;
    private static final int SCORE_AREA_RECT = 200;
    private View view;// view
    private BricksBreaker bricksBreaker;// model

    private JFrame Frame;/*Frame for displaying the screen for game that have bricks and stuff*/
    /* key controller */
    private final Set<Integer> pressedKeys = new HashSet<>();/*keys that are pressed will be saved here until all keys are served*/
    private boolean isMultitaskRunning = false;/*Ball move in other thread, this used for pause the thread, if paused the ball will be stopped*/
    Multithreading thread;/*It is the Multithreading class object, that handle the ball and its logic*/

    public Controller(Color bricksColor, Color backGroundColor, Color paddleColor, Color ballColor) {/*constructor*/
        /*Boundary for view is passed from here and the scoring area is reserved by subtracting it*/
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
        Frame.addWindowListener(new WindowAdapter() {/*on close of Frame, this function listen for the frame closure(means press of X on top right of screen*/
            public void windowClosing(WindowEvent e) {
                Exit(Frame);/*Function that run on closure, closes the Frame*/
            }
        });
        Frame.setVisible(true);/*Frame will only visible after setting it to true*/

        RefreshView();/*All the setting variable x,y color high score, score is done here*/
        CreateThread();/*Thread for ball movement*/
    }

    @Override
    public synchronized void keyPressed(KeyEvent e) {// All synchronized blocks synchronized on the same object can only
        // have one thread executing inside them at a time.
        pressedKeys.add(e.getKeyCode());/*All the keys are added in it user may press alot of keys at once so each key should be served*/

        try {
            if (!pressedKeys.isEmpty()) {
                for (Integer pressedKey : pressedKeys) {
                    switch (pressedKey) {

                        case KeyEvent.VK_RIGHT:// Right arrow key code, moving paddle
                            bricksBreaker.getPaddle().Movement(1, BoundWidth - SCORE_AREA_RECT, BoundHeight);// right
                            break;

                        case KeyEvent.VK_LEFT:// Left arrow key code, moving paddle
                            bricksBreaker.getPaddle().Movement(2, BoundWidth - SCORE_AREA_RECT, BoundHeight);// left
                            break;

                        case KeyEvent.VK_SPACE:/*pause the game*/
                            keyclear();/*when the space bar is entered clear all the keys remaining*/
                            isMultitaskRunning = false;/*means the thread is paused*/
                            Menu();/*Screen to display on pause*/
                            break;
                    }
                    RefreshView();/*after each key press game data is refereshed for view*/
                }
            }
        }
        catch (Exception ex){
            //ignore
        }
    }

    public void keyclear() {/*if the key is release it get removed*/
        pressedKeys.clear();
    }
    @Override
    public void keyReleased(KeyEvent e) {/*if the key is release it get removed*/
        pressedKeys.remove(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
    }

    private void SetBricks() {/*method to send brick data to view*/
        view.SetBricks(bricksBreaker.getbricksX(), bricksBreaker.getbricksY(), bricksBreaker.getbricksHit(),
                bricksBreaker.getBW(), bricksBreaker.getBH());
    }

    private void SetPaddle() {/*method to send paddle data to view*/
        view.SetPaddle(bricksBreaker.getPaddle().getX(), bricksBreaker.getPaddle().getY(),
                bricksBreaker.getPaddle().getWidth(), bricksBreaker.getPaddle().getHeight());
    }

    private void SetBall() {/*method to send ball data to view*/
        view.SetBall(bricksBreaker.getBall().getX(), bricksBreaker.getBall().getY(),
                bricksBreaker.getBall().getRadius());
    }

    private void SetScores() {/*method to send score, high score data to view*/
        view.SetScore(bricksBreaker.getScore(), bricksBreaker.getHighScore());
    }

    private void SetLevel() {/*method to send current level e.g. level is 4 to view*/
        view.SetLevel(bricksBreaker.getLevel());
    }

    private void ShowAll() {/*Ask view to show all stuff*/
        view.ShowAll();
    }

    private void CheckLevelChange() {/*Ask for game to levelUp if bricks are empty*/
        bricksBreaker.LevelUpIfBricksEmpty();
    }

    private void RefreshView() {/*refresh the view, after sending all the data to view ask for view in the end to Show the stuff to screen*/
        CheckLevelChange();
        SetLevel();
        SetBricks();
        SetPaddle();
        SetBall();
        SetScores();
        ShowAll();
    }

    void Menu() {/*Menu used to display stuff on pause, Another Frame is used for this*/
        Frame.setEnabled(false);/*the previous frame cnnot be touched (Disabled for a while)*/
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

        JButton resume = new JButton("Resume");/*resume buuton*/
        resume.addActionListener(new ActionListener() {// add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {
                Resume(Frame2);
            }
        });
        resume.setBounds(100, 50, 100, 30);

        JButton exit = new JButton("Exit Game");/*exit buuton*/
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

    public static int okcancel(String theMessage) {/*Ok cancel alert dialog box, If want to close the application after pausing*/
        int result = JOptionPane.showConfirmDialog((Component) null, theMessage, "alert", JOptionPane.OK_CANCEL_OPTION);
        return result;
    }

    private void Resume(JFrame thisFrame) {/*Resume Screen of Game*/
        isMultitaskRunning = true;
        CreateThread();
        Frame.setEnabled(true);
        thisFrame.dispose();
    }

    /**
     * If Frame(game main screen) is paused as parameter thats mean the game is over. and this function is asked to close main screen
     * If the frame2 is paused as parameter then its mean user is cosing the window so ask for the comfirmation
     */
    private void Exit(JFrame thisFrame) {/*Exit Screen of game two ways ma be game is over or game is paused/closed by user*/
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

    /**
     * Create thread for the ball movement
     */
    private void CreateThread() {
        thread = new Multithreading();
        thread.start();
    }

    class Multithreading extends Thread {
        public void run() {
            isMultitaskRunning = true;/*when the thread is created, its make this variable true*/
            while (isMultitaskRunning) {/*Run until isMultitaskRunning is true*/

                int score = bricksBreaker.getBall().Movement(bricksBreaker.getBricks(), bricksBreaker.getPaddle(),
                        BoundWidth - SCORE_AREA_RECT, BoundHeight);
                /*score return from game logic*/
                if (score >= 0 && bricksBreaker.getLevel() <= 5) {/*If score is greater equal 0 and level are equal less 5 its means the game is fine*/
                    bricksBreaker.scoreInc(score);
                    RefreshView();
                } else if (bricksBreaker.getLevel() > 5) {/*If level goes higher than 5 => You won displays*/
                    JOptionPane.showMessageDialog(Frame, "You won!!!");
                    Exit(Frame);
                } else {/*this means score are negative => by default negative scores mean ball goes below paddle*/
                    JOptionPane.showMessageDialog(Frame, "Game Over!!!");
                    Exit(Frame);
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(15);/*thread time interval(mili seconds), its a game speed of ball*/
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}