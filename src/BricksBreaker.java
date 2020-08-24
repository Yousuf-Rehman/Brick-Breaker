/*Model*/

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Level;

public class BricksBreaker {
    private int level;
    private ArrayList<Bricks> bricks;
    private Ball ball;
    private Paddle paddle;
    private int BoundWidth, BoundHeight;
    int score;
    private static int HighScore = 0;

    public BricksBreaker(int RW, int RH) {
        BoundWidth = RW;
        BoundHeight = RH;
        level = 1;
        score = 0;
        SetLevel(level);
    }

    private void SetLevel(int levels) {
        switch (levels) {
            case 1:
                bricks = new ArrayList<>();
                paddle = new Paddle(BoundWidth / 2, BoundHeight - 80, LevelData.PW1, LevelData.PH1);
                ball = new Ball(BoundWidth / 2, BoundHeight - 100, LevelData.BR);
                for (int i = LevelData.BrickStartOffsetX; i < (LevelData.COLS1 * LevelData.BW
                        + LevelData.BrickStartOffsetX); i = i + LevelData.BW)
                    for (int j = LevelData.BrickStartOffsetY; j < (LevelData.ROWS1 * LevelData.BH
                            + LevelData.BrickStartOffsetY); j = j + LevelData.BH)
                        bricks.add(new Bricks(i, j, LevelData.BW, LevelData.BH, LevelData.BrickHit1));
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

            case 4:
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

    public int[] getbricksX() {// converting Array List to array
        int[] X = new int[bricks.size()];
        for (int i = 0; i < X.length; i++) {
            X[i] = bricks.get(i).getX();
        }
        return X;
    }

    public int[] getbricksY() {// coverting Array List to array
        int[] Y = new int[bricks.size()];
        for (int i = 0; i < Y.length; i++) {
            Y[i] = bricks.get(i).getY();
        }
        return Y;
    }

    public int[] getbricksHit() {// coverting Array List to array
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

    public void LevelUpIfBricksEmpty() {
        if (bricks.size() <= 0) {
            level++;
            SetLevel(level);
        }
    }
}
