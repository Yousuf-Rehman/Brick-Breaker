import javafx.scene.shape.Circle;

import java.awt.*;
import java.util.ArrayList;
/**
 * Assets class has all the element used for the game
 * This class is inherited by all the assets of game( bricks, ball, paddle) used in game
 */
public abstract class Assests {
    protected int X, Y;/*X,Y is the co-ordinates of the assets each asset must has X,Y*/

    void setX(int x) {
        X = x;
    }

    void setY(int y) {
        Y = y;
    }

    int getX() {
        return X;
    }

    int getY() {
        return Y;
    }
}

class Bricks extends Assests {

    private int bw, bh;/*bricks width, bricks height*/
    private int brickHits;

    Bricks(int x, int y, int bw, int bh, int brickHits) {
        X = x;
        Y = y;
        this.bw = bw;
        this.bh = bh;
        this.brickHits = brickHits;/*Number of brick hits required, this number will decrement when gets hit until zero*/
    }

    public int getWidth() {// brick width
        return LevelData.BW;
    }

    public int getHeight() {// brick height
        return LevelData.BH;
    }

    public void BrickHitsDec() {/*brick hit keeps on decrement when hit until zero*/
        brickHits--;
    }

    public int getBrickHits() {/*get bricks hit for other classes*/
        return brickHits;
    }
}

class Ball extends Assests {
    private int br;
    private int BallX_Step = LevelData.BALLX_STEP;
    private int BallY_Step = LevelData.BALLY_STEP;

    Ball(int x, int y, int br) {
        X = x;
        Y = y;
        this.br = br;
    }

    /* Ball getter Setter */
    public int getRadius() {
        return LevelData.BR;
    }

    public int Movement(ArrayList<Bricks> bricks, Paddle paddle, int BoundWidth, int BoundHeight) {//check ball collision with
        // other bricks and padlle

        int scores = 0;/*score get increment if bricks are hitted*/

        X += BallX_Step;
        Y += BallY_Step;

        for (int i = 0; i < bricks.size(); i++)/*check for all bricks if get hit by ball*/
            if (new Rectangle(bricks.get(i).X, bricks.get(i).Y, bricks.get(i).getWidth(), bricks.get(i).getHeight())
                    .intersects(new Rectangle(X, Y, br, br))) {

                if (bricks.get(i).getBrickHits() <= 1) {/*when the brick hits already remain one and ball is hitted it now*/
                    bricks.get(i).BrickHitsDec();
                    bricks.remove(i);/*remove that brick from array list..object will be removed*/
                }
                else
                    bricks.get(i).BrickHitsDec();/*otherwise just decrement the hits, no need to remove that brick*/
                // BallX_Step = -BallX_Step;

                BallY_Step = -BallY_Step;/*if collide with any brick, change direction*/
                scores += 5;/*set the score on hit to bricks only*/
            }

        if (new Rectangle(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight())/*check collison with paddle*/
                .intersects(new Rectangle(X, Y, br, br)))
            BallY_Step = -BallY_Step;/*if collide with paddle, change direction*/

        if (X <= 0 || X >= BoundWidth)/*if on boundary, ball change directon*/
            BallX_Step = -BallX_Step;

        if (Y <= 0)/*if on boundary, ball change directon*/
            BallY_Step = -BallY_Step;

        if (Y >= BoundHeight)/*this means below paddle, send -1 to the controller to end game*/
            return -1;// game is exit

        return scores;
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

    /* Paddle getter Setter */
    public int getWidth() {
        return pw;
    }

    public int getHeight() {
        return ph;
    }

    /* Paddle Movement, trigger by controller on user left/right key pressed */
    public int Movement(int direction, int BoundWidth, int BoundHeight) {

        switch (direction) {/*left or right direction*/
            case 1:// right
                if ((X + pw + LevelData.PADDLE_STEP) <= BoundWidth)
                    X = X + LevelData.PADDLE_STEP;
                break;
            case 2:// left
                if (X - LevelData.PADDLE_STEP >= 0)
                    X = X - LevelData.PADDLE_STEP;
                break;
        }
        return 0;
    }
}
