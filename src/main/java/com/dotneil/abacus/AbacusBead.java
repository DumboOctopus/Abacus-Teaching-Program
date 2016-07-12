package com.dotneil.abacus;

import javax.swing.*;

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
     * Steps the com.dotneil.abacus bead <code>AbacusBead.pixelRate</code> up.
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
        column.requestRepaint();
        return false;
    }

    /**
     *
     * Steps the com.dotneil.abacus bead <code>AbacusBead.pixelRate</code> down.
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
        column.requestRepaint();
        return false;
    }
}
