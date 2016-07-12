package com.dotneil.abacus;

/**
 * Created by neilprajapati on 7/10/16.
 * neilprajapati, dont forget to javaDoc this file.
 */
public class AbacusBead {

    public static final double fractionRate = 0.015; //1.5% per 50 millis
    

    private double upFraction;
    private double downFraction;
    private final AbacusColumn column;


    private double currentState;

    public AbacusBead(AbacusColumn column, double downFraction, double upFraction) {
        this.column = column;
        this.downFraction = downFraction;
        this.upFraction = upFraction;
        this.currentState = downFraction;
    }

    //--------------GETTERS-----------//


    public double getCurrentState() {
        return currentState;
    }

    //-----------SIMPLE MOVING UP AND DOWN-----------//
    public void goUp() {

        if(currentState != upFraction) {
            currentState = upFraction;
            column.requestRepaint();
        }

    }

    public void goDown() {
        if(currentState != downFraction) {
            currentState = downFraction;
            column.requestRepaint();
        }
    }


    //-----------ANIMATING UP AND DOWN-----------//

    /**
     * Steps the com.dotneil.abacus bead <code>AbacusBead.fractionRate</code> up.
     *
     * @return true if there was any movement, false otherwise
     */
    public boolean stepUp()
    {
        if (Math.abs(currentState - upFraction) >= fractionRate) {
            currentState += fractionRate * (currentState > upFraction? -1: 1);
            column.requestRepaint();
            return true;
        }
        currentState = upFraction;
        column.requestRepaint();
        return false;
    }

    /**
     *
     * Steps the com.dotneil.abacus bead <code>AbacusBead.fractionRate</code> down.
     *
     * @return true if the position of the bead changed, false otherwise
     */
    public boolean stepDown()
    {
        if (Math.abs(currentState - downFraction) >= fractionRate) {
            currentState += fractionRate * (currentState > downFraction? -1: 1);
            column.requestRepaint();
            return true;
        }
        currentState = downFraction;
        column.requestRepaint();
        return false;
    }
}
