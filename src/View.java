import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class View extends JPanel {

    protected static int RECT_WIDTH;
    protected static int RECT_HEIGHT;
    protected static final Font font = new Font("TimesRoman", Font.BOLD, 20);
    protected static final Font scorefont = new Font("TimesRoman", Font.BOLD, 24);
    private static Color BricksColor, BackGroundColor, PaddleColor, BallColor;

    protected int scores, Highscores, level;
    protected static String str;
    protected int[] Xpos;
    protected int[] Ypos;
    protected int[] bricksHit;
    protected int rw, rh;

    protected int PXpos, PYpos, PW, PH;

    protected int BallXpos, BallYpos, BallR;
    View(int RW, int RH, Color bricksColor, Color backGroundColor, Color paddleColor, Color ballColor) {
        RECT_WIDTH = RW;
        RECT_HEIGHT = RH;
        scores = 0;
        BricksColor = bricksColor;
        BackGroundColor = backGroundColor;
        PaddleColor = paddleColor;
        BallColor = ballColor;
    }

    // Xpos and Ypos are the positions of brick and rw. rh are width and height
    // respectively
    public void SetBricks(int[] Xpos, int[] Ypos, int[] bricksHit, int rw, int rh) {
        this.Xpos = Xpos;
        this.Ypos = Ypos;
        this.bricksHit = bricksHit;
        this.rw = rw;
        this.rh = rh;
    }

    public void SetPaddle(int PXpos, int PYpos, int PW, int PH) {
        this.PXpos = PXpos;
        this.PYpos = PYpos;
        this.PW = PW;
        this.PH = PH;
    }

    public void SetBall(int BallXpos, int BallYpos, int BallR) {
        this.BallXpos = BallXpos;
        this.BallYpos = BallYpos;
        this.BallR = BallR;
    }

    void SetScore(int s, int hs){
        scores = s;
        Highscores = hs;
    }

    void SetLevel(int l){
        level = l;
    }

    void ShowAll() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {/*main display function: display all data*/
        super.paintComponent(g);
        // g.drawImage(background_Image, 0, 0, null);
        g.setColor(BackGroundColor);
        g.fillRect(0, 0, RECT_WIDTH, RECT_HEIGHT);

        if (Xpos != null)
            for (int i = 0; i < Xpos.length; i++) {

                if(bricksHit[i] == 2)
                    g.setColor(Color.RED);
                else
                    g.setColor(BricksColor);
                Rectangle r = new Rectangle(Xpos[i], Ypos[i], rw, rh);
                g.fillRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());

                if(BricksColor == Color.WHITE)
                    g.setColor(Color.BLACK);
                else
                    g.setColor(Color.WHITE);
                g.drawRect(Xpos[i], Ypos[i], rw, rh);
            }

        g.setColor(PaddleColor);
        Rectangle r = new Rectangle(PXpos, PYpos, PW, PH);
        g.fillRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());

        g.setColor(Color.WHITE);/*Boundary of Paddle*/
        g.drawRect(PXpos, PYpos, PW, PH);

        g.setColor(BallColor);
        int diameter = BallR * 2;
        //shift x and y by the radius of the circle in order to correctly center it
        g.fillOval(BallXpos - BallR, BallYpos - BallR, diameter, diameter);
        g.setColor(Color.WHITE);
        g.drawOval(BallXpos - BallR, BallYpos - BallR, diameter, diameter);

        UpdatingPlayersData(g);
    }

    /*score and other stuff is displayed here*/
    private void UpdatingPlayersData(Graphics g){
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString("Scores: ", RECT_WIDTH + 20, 30);
        g.setFont(scorefont);
        g.drawString(Integer.toString(scores), RECT_WIDTH + 100, 30);

        g.setColor(Color.RED);
        g.setFont(font);
        g.drawString("Level: ", RECT_WIDTH + 20, 60);
        g.setFont(scorefont);
        g.drawString(Integer.toString(level), RECT_WIDTH + 100, 60);

        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString(" High Scores: ", RECT_WIDTH + 10, RECT_HEIGHT - 100);
        g.setFont(scorefont);
        g.drawString(Integer.toString(Highscores), RECT_WIDTH + 80, RECT_HEIGHT - 70);
    }
}