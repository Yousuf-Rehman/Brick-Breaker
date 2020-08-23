import javafx.scene.shape.Circle;

import java.awt.*;
import java.util.ArrayList;

public abstract class Assests {
    protected int X, Y;

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

    private int bw, bh;
    private int brickHits;

    Bricks(int x, int y, int bw, int bh, int brickHits) {
        X = x;
        Y = y;
        this.bw = bw;
        this.bh = bh;
        this.brickHits = brickHits;
    }

    public int getWidth() {// brick width
        return LevelData.BW;
    }

    public int getHeight() {// brick height
        return LevelData.BH;
    }

    public void BrickHitsDec(){
        brickHits--;
    }

    public int getBrickHits(){
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

    /*Ball getter Setter*/
    public int getRadius() {
        return LevelData.BR;
    }


    public int Movement(ArrayList<Bricks> bricks, Paddle paddle, int BoundWidth, int BoundHeight){//collision with other

        int scores = 0;

        X += BallX_Step;
        Y += BallY_Step;

        for(int i=0; i<bricks.size(); i++)
            if(new Rectangle(bricks.get(i).X, bricks.get(i).Y,
                    bricks.get(i).getWidth(), bricks.get(i).getHeight()).
                    intersects(new Rectangle(X, Y, br, br))) {
                if(bricks.get(i).getBrickHits()<=1)
                    bricks.remove(i);
                else
                    bricks.get(i).BrickHitsDec();
                //BallX_Step = -BallX_Step;
                BallY_Step = -BallY_Step;
                scores += 5;
            }

        if(new Rectangle(paddle.getX(), paddle.getY(),
                paddle.getWidth(), paddle.getHeight()).
                intersects(new Rectangle(X, Y, br, br)))
            BallY_Step = -BallY_Step;

        if(X <= 0 || X >= BoundWidth)
            BallX_Step = -BallX_Step;

        if(Y <= 0 || Y >= BoundHeight)
            BallY_Step = -BallY_Step;

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

    /*Paddle getter Setter*/
    public int getWidth() {
        return pw;
    }

    public int getHeight() {
        return ph;
    }

    /*Paddle Movement*/
    public int Movement(int direction, int BoundWidth, int BoundHeight){

        switch (direction){
            case 1://right
                if( (X + pw + LevelData.PADDLE_STEP) <= BoundWidth)
                    X = X + LevelData.PADDLE_STEP;
                break;
            case 2://left
                if(X - LevelData.PADDLE_STEP >= 0)
                    X = X - LevelData.PADDLE_STEP;
                break;
        }
        return 0;
    }
}
