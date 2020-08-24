/*Model*/

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * This class has all the logic of the game
 * getter and setter functions are also used that will communicate with controller class
 */
public class BricksBreaker {
    private int level;/*current level detail*/
    private ArrayList<Bricks> bricks;/*brick objects are all in it*/
    private Ball ball;/*ball object*/
    private Paddle paddle;/*paddle object*/
    private int BoundWidth, BoundHeight;/*Boundaries for screen for game to check boundary conditions, send by controller the area of game*/
    int score;/*score save here*/
    private static int HighScore = 0;/* high scre static to keep this variable same for all the objects of same class*/

    public BricksBreaker(int RW, int RH) {/*constructor for the game*/
        BoundWidth = RW;
        BoundHeight = RH;
        level = 1;
        score = 0;
        SetLevel(level);
    }

    private void SetLevel(int levels) {/* the level will change here => Data is taken from LevelData class*/
        switch (levels) {
            case 1:/*for each level separate case*/
                bricks = new ArrayList<>();/*initializing arraylist*/
                paddle = new Paddle(BoundWidth / 2, BoundHeight - 80, LevelData.PW1, LevelData.PH1);
                ball = new Ball(BoundWidth / 2, BoundHeight - 100, LevelData.BR);
                for (int i = LevelData.BrickStartOffsetX; i < (LevelData.COLS1 * LevelData.BW
                        + LevelData.BrickStartOffsetX); i = i + LevelData.BW)
                    for (int j = LevelData.BrickStartOffsetY; j < (LevelData.ROWS1 * LevelData.BH
                            + LevelData.BrickStartOffsetY); j = j + LevelData.BH)
                        bricks.add(new Bricks(i, j, LevelData.BW, LevelData.BH, LevelData.BrickHit1));/*storing brick data*/
                break;

            case 2:
                bricks = new ArrayList<>();
                paddle = new Paddle(BoundWidth / 2, BoundHeight - 80, LevelData.PW2, LevelData.PH2);
                ball = new Ball(BoundWidth / 2, BoundHeight - 100, LevelData.BR);
                for (int i = LevelData.BrickStartOffsetX; i < (LevelData.COLS2 * LevelData.BW
                        + LevelData.BrickStartOffsetX); i = i + LevelData.BW)
                    for (int j = LevelData.BrickStartOffsetY; j < (LevelData.ROWS2 * LevelData.BH
                            + LevelData.BrickStartOffsetY); j = j + LevelData.BH)
                        bricks.add(new Bricks(i, j, LevelData.BW, LevelData.BH, LevelData.BrickHit2));
                break;

            case 3:
                bricks = new ArrayList<>();
                paddle = new Paddle(BoundWidth / 2, BoundHeight - 80, LevelData.PW3, LevelData.PH3);
                ball = new Ball(BoundWidth / 2, BoundHeight - 100, LevelData.BR);
                for (int i = LevelData.BrickStartOffsetX; i < (LevelData.COLS3 * LevelData.BW
                        + LevelData.BrickStartOffsetX); i = i + LevelData.BW)
                    for (int j = LevelData.BrickStartOffsetY; j < (LevelData.ROWS3 * LevelData.BH
                            + LevelData.BrickStartOffsetY); j = j + LevelData.BH)
                        bricks.add(new Bricks(i, j, LevelData.BW, LevelData.BH, LevelData.BrickHit3));
                break;

            case 4:/*paramid bricks*/
                int count = 1;
                bricks = new ArrayList<>();
                paddle = new Paddle(BoundWidth / 2, BoundHeight - 80, LevelData.PW4, LevelData.PH4);
                ball = new Ball(BoundWidth / 2, BoundHeight - 100, LevelData.BR);
                for (int j = LevelData.BrickStartOffsetY; j < (LevelData.ROWS4 * LevelData.BH
                        + LevelData.BrickStartOffsetY); j = j + LevelData.BH) {
                    for (int i = BoundWidth / 2; i < (count * LevelData.BW + BoundWidth / 2); i = i + LevelData.BW) {

                        bricks.add(new Bricks(i, j, LevelData.BW, LevelData.BH, LevelData.BrickHit4));

                    }
                    count++;
                }

                count = 1;
                for (int j = LevelData.BrickStartOffsetY; j < (LevelData.ROWS4 * LevelData.BH
                        + LevelData.BrickStartOffsetY); j = j + LevelData.BH) {
                    for (int i = BoundWidth / 2 - LevelData.BW, k = 0; k < count; i = i - LevelData.BW, k++) {
                        bricks.add(new Bricks(i, j, LevelData.BW, LevelData.BH, LevelData.BrickHit4));
                    }
                    count++;
                }
                break;

            case 5:
                bricks = new ArrayList<>();
                paddle = new Paddle(BoundWidth / 2, BoundHeight - 80, LevelData.PW5, LevelData.PH5);
                ball = new Ball(BoundWidth / 2, BoundHeight - 100, LevelData.BR);
                for (int i = LevelData.BrickStartOffsetX; i < (LevelData.COLS5 * LevelData.BW
                        + LevelData.BrickStartOffsetX); i = i + LevelData.BW)
                    for (int j = LevelData.BrickStartOffsetY; j < (LevelData.ROWS5 * LevelData.BH
                            + LevelData.BrickStartOffsetY); j = j + LevelData.BH)
                        bricks.add(new Bricks(i, j, LevelData.BW, LevelData.BH, LevelData.BrickHit5));
                break;

            default:
                break;
        }

    }

    public int getLevel() {
        return level;
    }

    /* bricks ArrayList getter */
    public ArrayList<Bricks> getBricks() {
        return bricks;
    }

    public int getBW() {// brick width
        return LevelData.BW;
    }

    public int getBH() {// brick height
        return LevelData.BH;
    }

    public int[] getbricksX() {// converting Array List to array, X coordiantes of brick
        int[] X = new int[bricks.size()];
        for (int i = 0; i < X.length; i++) {
            X[i] = bricks.get(i).getX();
        }
        return X;
    }

    public int[] getbricksY() {// coverting Array List to array to send data to contoller, Y coordiantes of brick
        int[] Y = new int[bricks.size()];
        for (int i = 0; i < Y.length; i++) {
            Y[i] = bricks.get(i).getY();
        }
        return Y;
    }

    public int[] getbricksHit() {// coverting Array List to array to send data to contoller for Hits of each brick remaining
        int[] Hits = new int[bricks.size()];
        for (int i = 0; i < Hits.length; i++) {
            Hits[i] = bricks.get(i).getBrickHits();
        }
        return Hits;
    }

    /* paddle Getter */
    public Paddle getPaddle() {
        return paddle;
    }

    /* ball Getter */
    public Ball getBall() {
        return ball;
    }

    public void scoreInc(int s) {
        score += s;
        if (HighScore <= score)
            HighScore = score;
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return HighScore;
    }

    public void LevelUpIfBricksEmpty() {/*increment the level if bricks are all empty*/
        if (bricks.size() <= 0) {
            level++;
            SetLevel(level);
        }
    }
}
