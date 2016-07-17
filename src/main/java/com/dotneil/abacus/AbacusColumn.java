package com.dotneil.abacus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Abacus Column which makes up an Abacus.
 */
public class AbacusColumn extends JComponent{
    private AbacusDataModel model;
    private int columnNumber;
    private int displayedValue;

    private AbacusBead[] beads; //the 5 bead is a zero index, rest going down....
    private boolean isRequestingRepaint = false;

    /**
     * Sole Constructor
     * @param model the <code>AbacusModel</code> the column will read and display
     * @param columnNumber the exact column (0 indexed right to left) this <code>AbacusColumn</code> represents and displays
     */
    public AbacusColumn(AbacusDataModel model, int columnNumber) {
        this.model = model;
        this.columnNumber = columnNumber;
        beads = new AbacusBead[5];
        
        beads[0]= new AbacusBead(this, 0, 1.0 / 8);
        beads[1]= new AbacusBead(this, 4.0 / 8, 3.0 / 8);
        beads[2]= new AbacusBead(this, 5.0 / 8,  4.0 / 8);
        beads[3]= new AbacusBead(this, 6.0 / 8,  5.0 / 8);
        beads[4]= new AbacusBead(this, 7.0 / 8, 6.0 / 8);

        refreshBeads();

        setOpaque(true);
    }


    //============================FROM JCOMPONENT============================================//
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(152, 118, 40));
        g.fillRect(getWidth()/2 - getWidth()/12, 0, getWidth()/6, getHeight());
        g.setColor(Color.BLACK);
        g.fillRect(0, getHeight()/4, getWidth(), getHeight()/8);


        if(columnNumber %2 == 0) g.setColor(Color.ORANGE);
        else g.setColor(Color.darkGray);
        drawBead((int)(beads[0].getCurrentState()*getHeight()), g);
        drawBead((int)(beads[1].getCurrentState()*getHeight()), g);
        drawBead((int)(beads[2].getCurrentState()*getHeight()), g);
        drawBead((int)(beads[3].getCurrentState()*getHeight()), g);
        drawBead((int)(beads[4].getCurrentState()*getHeight()), g);
    }
    private void drawBead(int height, Graphics g)
    {
        g.fillOval(getWidth()/6, height, 2*getWidth()/3, getHeight()/8);
    }

    //================================MAIN METHoDS==========================================//
    /**
     * Animates the beads positions to match the model. Does a small move then returns.
     * Will move 5 bead first then the ones beads. Usually called in a Timer
     *
     * @return true if recalling is still necessary false otherwise.
     * @see Timer
     */
    public boolean stepBeads()
    {
        int newDisplayedValue = model.getColumn(columnNumber);
        boolean moved = false;

        //first we always move the 5
        if (newDisplayedValue >= 5) {
            moved = beads[0].stepUp();
        } else {
            moved = beads[0].stepDown();
        }

        //this is so that if the 5 bead moved, we don't start moving the other beads.
        if(moved){
            doRepaintIfRequested();
            return true;
        }


        for (int i = 1; i <= newDisplayedValue % 5; i++) {
            if (beads[i].stepUp()) moved = true;
        }


        for (int i = newDisplayedValue % 5 + 1; i < beads.length; i++) {
            if (beads[i].stepDown()) moved = true;
        }

        doRepaintIfRequested();
        //make sure we update displayed value;
        //if we were at the right space to begin with...
        if(!moved) displayedValue = newDisplayedValue;
        return moved;
    }



    /**
     * Refreshes beads positions to match models without any animations. Does it instantenously
     */
    public void refreshBeads()
    {
        displayedValue = model.getColumn(columnNumber);
        if (displayedValue < 5)
            beads[0].goDown();
        else
            beads[0].goUp();

        for(int i = 1; i < beads.length; i++)
        {
            if(displayedValue %5 >= i)
                beads[i].goUp();
            else
                beads[i].goDown();
        }

        if(isRequestingRepaint) {
            repaint();
            isRequestingRepaint = false;
        }
    }



    //===========================MAKE REPAINTS MORE EFFIECNET================================================//

    /**
     * notifies the com.dotneil.abacus column to refresh when possible. Usually used
     * when multiple changes are made at the same time and calling <code>repaint()</code>
     * for each would be redundant
     */
    public void requestRepaint()
    {
        isRequestingRepaint = true;
    }


    private void doRepaintIfRequested() {
        if(isRequestingRepaint) {
            isRequestingRepaint = false;
            repaint();
        }
    }


}
