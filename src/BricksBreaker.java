/*Model*/

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BricksBreaker {
    private int level;
    private ArrayList<Bricks> bricks;
    private Ball ball;
    private Paddle paddle;
    private int BoundWidth, BoundHeight;
    int score;
    private static int HighScore = 0;

    public BricksBreaker(int RW, int RH) {
        bricks = new ArrayList<>();
        BoundWidth = RW;
        BoundHeight = RH;
        ball = new Ball(BoundWidth / 2, BoundHeight - 100, LevelData.BR);
        paddle = new Paddle(BoundWidth / 2, BoundHeight - 80, LevelData.PW, LevelData.PH);
        level = 1;
        score = 0;
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

    /*bricks ArrayList getter*/
    public ArrayList<Bricks> getBricks(){
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

    /*paddle Getter*/
    public Paddle getPaddle(){
        return paddle;
    }

    /*ball Getter*/
    public Ball getBall(){
        return ball;
    }

    public void scoreInc(int s){
        score += s;
        if(HighScore <= score)
            HighScore = score;
        System.out.print(score);System.out.print(" ");
        System.out.println(HighScore);
    }

    public int getScore(){
        return score;
    }

    public int getHighScore(){ return HighScore;}
}
