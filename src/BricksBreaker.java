/*Model*/

import java.lang.reflect.Array;
import java.util.ArrayList;

class Bricks extends Assests {

    private int bw, bh;

    Bricks(int x, int y, int bw, int bh) {
        X = x;
        Y = y;
        this.bw = bw;
        this.bh = bh;
    }

}

class Ball extends Assests {
    private int br;

    Ball(int x, int y, int br) {
        X = x;
        Y = y;
        this.br = br;
    }
}

class Paddle extends Assests {
    private int pw, ph;

    Paddle(int x, int y, int pw, int ph) {
        X = x;
        Y = y;
        this.pw = pw;
        this.ph = ph;
    }

}

public class BricksBreaker {
    private int level;
    private ArrayList<Bricks> bricks;
    private Ball ball;
    private Paddle paddle;

    public BricksBreaker(int RW, int RH) {
        bricks = new ArrayList<>();
        ball = new Ball(RW / 2, RH - 100, LevelData.BR);
        paddle = new Paddle(RW / 2, RH - 80, LevelData.PW, LevelData.PH);
        level = 1;
        InitLevel();
    }

    private void InitLevel() {
        if (level == 1) {
            for (int i = LevelData.BrickStartOffsetX; i < (LevelData.COLS1 * LevelData.BW
                    + LevelData.BrickStartOffsetX); i = i + LevelData.BW)
                for (int j = LevelData.BrickStartOffsetY; j < (LevelData.ROWS1 * LevelData.BH
                        + LevelData.BrickStartOffsetY); j = j + LevelData.BH)
                    bricks.add(new Bricks(i, j, LevelData.BW, LevelData.BH));
        }
    }

    public int getLevel() {
        return level;
    }

    public ArrayList<Bricks> getbricks() {
        return bricks;
    }

    public int[] getbricksX() {// coverting Array List to array
        int[] X = new int[bricks.size()];
        for (int i = 0; i < X.length; i++) {
            X[i] = bricks.get(i).X;
        }
        return X;
    }

    public int[] getbricksY() {// coverting Array List to array
        int[] Y = new int[bricks.size()];
        for (int i = 0; i < Y.length; i++) {
            Y[i] = bricks.get(i).Y;
        }
        return Y;
    }

    public int getBW() {// brick width
        return LevelData.BW;
    }

    public int getBH() {// brick height
        return LevelData.BH;
    }

    public int getBallR() {
        return LevelData.BR;
    }

    public int getPaddleH() {
        return LevelData.PH;
    }

    public int getPaddleW() {
        return LevelData.PW;
    }

    public int getPaddleX() {
        return paddle.X;
    }

    public int getPaddleY() {
        return paddle.Y;
    }

    public int getBallX() {
        return ball.X;
    }

    public int getBallY() {
        return ball.Y;
    }
}
