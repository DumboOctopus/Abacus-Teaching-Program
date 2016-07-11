package abacus;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by neilprajapati on 7/10/16.
 * neilprajapati, dont forget to javaDoc this file.
 */
public class AbacusBead {

    public static final int pixelRate = 10; //10 pixels per second
    

    private double upFraction;
    private double downFraction; // down = downFraction * getHeight();
    private final AbacusColumn column;


    private int currentState;

    public AbacusBead(AbacusColumn column, double downFraction, double upFraction) {
        this.column = column;
        this.downFraction = downFraction;
        this.upFraction = upFraction;
    }

    //--------------GETTERS-----------//

    public int getCurrentState() {
        return currentState;
    }

    //-----------SIMPLE MOVING UP AND DOWN-----------//
    public void goUp() {

        int upstate = (int) (upFraction * column.getHeight());
        if(currentState != upstate) {
            currentState = upstate;
            column.requestRepaint();
        }

    }

    public void goDown() {
        int downState = (int) (downFraction * column.getHeight());
        if(currentState != downState) {
            currentState = downState;
            column.requestRepaint();
        }
    }


    //-----------ANIMATING UP AND DOWN-----------//

    /**
     * moves the bead at a rate of <code>AbacusBead.pixelRate</code> upwards until
     * it reaches the up position
     */
    public void animateUp() {
        Timer t = new Timer(
            1000 / pixelRate + 10,
            e->{
                Timer source = (Timer) e.getSource();
                int upState = (int)(upFraction*column.getHeight());
                if(Math.abs(currentState - upState) < pixelRate)
                {
                    currentState = upState;
                    source.stop();
                    return;
                }
                currentState += pixelRate * (currentState > upState? -1: 1);
                column.repaint();
            }
        );
        t.start();

        //for it not to return until the process is down;
        //while(t.isRunning());
    }

    /**
     * Steps the abacus bead <code>AbacusBead.pixelRate</code> up.
     *
     * @return true if there was any movement, false otherwise
     */
    public boolean stepUp()
    {
        int upState = (int)(upFraction*column.getHeight());
        if (Math.abs(currentState - upState) >= pixelRate) {
            currentState += pixelRate * (currentState > upState? -1: 1);
            column.requestRepaint();
            return true;
        }
        currentState = upState;
        return false;
    }


    /**
     * moves the bead at a rate of <code>AbacusBead.pixelRate</code> downward until
     * it reaches the down position
     */
    public void animateDown() {
        Timer t = new Timer(
                1000 / pixelRate + 10,
                e->{
                    Timer source = (Timer) e.getSource();
                    int downState = (int)(downFraction*column.getHeight());
                    if(Math.abs(currentState - downState) < pixelRate)
                    {
                        currentState = downState;
                        source.stop();
                        return;
                    }
                    currentState += pixelRate * (currentState > downState? -1: 1);
                    column.repaint();
                }
        );
        t.start();

    }

    /**
     *
     * Steps the abacus bead <code>AbacusBead.pixelRate</code> down.
     *
     * @return true if the position of the bead changed, false otherwise
     */
    public boolean stepDown()
    {
        int downState = (int)(downFraction*column.getHeight());
        if (Math.abs(currentState - downState) >= pixelRate) {
            currentState += pixelRate * (currentState > downState? -1: 1);
            column.requestRepaint();
            return true;
        }
        currentState = downState;
        return false;
    }
}
