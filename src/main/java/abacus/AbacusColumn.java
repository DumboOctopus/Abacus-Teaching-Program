package abacus;

import javax.swing.*;
import java.awt.*;

/**
 * Created by neilprajapati on 7/8/16.
 * neilprajapati, dont forget to javaDoc this file.
 *
 * TODO; refractor bead into a seperate JComponent.
 */
public class AbacusColumn extends JComponent{
    private AbacusDataModel model;
    private int columnNumber;
    private int displayedValue;

    private AbacusBead[] beads; //the 5 bead is a zero index, rest going down....
    private boolean isRequestingRepaint = false;

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
        g.setColor(Color.BLACK);
        g.fillRect(0, getHeight()/4, getWidth(), getHeight()/8);



        if(columnNumber %2 == 0) g.setColor(Color.ORANGE);
        else g.setColor(Color.darkGray);
        drawBead(beads[0].getCurrentState(), g);
        drawBead(beads[1].getCurrentState(), g);
        drawBead(beads[2].getCurrentState(), g);
        drawBead(beads[3].getCurrentState(), g);
        drawBead(beads[4].getCurrentState(), g);
    }
    private void drawBead(int height, Graphics g)
    {
        g.fillOval(getWidth()/6, height, 2*getWidth()/3, getHeight()/8);
    }

    //================================MAIN METHoDS==========================================//

    /**
     * Animates the beads positions to match the model.
     */
    public void refreshBeadsAndAnimate()
    {
        int newDisplayedValue = model.getColumn(columnNumber);
        if(newDisplayedValue == displayedValue) return; //short circut here

        //first we always move the 5
        if (newDisplayedValue >= 5) {
            beads[0].animateUp();
        } else {
            beads[0].animateDown();
        }

        //now we have to figure out how many beads to move...
        for(int i = 1; i <= displayedValue %5; i++)
        {
            final int index = i;
            Timer t = new Timer(
                    AbacusBead.pixelRate + 10, //5 millis so it has time to snap into position
                    e -> {
                        Timer timer = (Timer) e.getSource();
                        if(!beads[index].stepUp()) timer.stop();
                        if(isRequestingRepaint) {
                            isRequestingRepaint = false;
                            repaint();
                        }
                    }
            );
            t.start();

        }


        for(int i = displayedValue %5 + 1; i < beads.length; i++)
        {
            final int index = i;
            Timer t = new Timer(
                    AbacusBead.pixelRate + 10, //move faster than pixels so it has time to reach snap condition
                    e -> {
                        Timer timer = (Timer) e.getSource();
                        if(!beads[index].stepDown()) timer.stop();
                        if(isRequestingRepaint) {
                            isRequestingRepaint = false;
                            repaint();
                        }
                    }
            );
            t.start();

        }

        //make sure we update displayed value;
        displayedValue = newDisplayedValue;
    }

    @Deprecated
    public void animateAllUp()
    {
        for(AbacusBead bead:beads)
            bead.animateUp();
    }

    /**
     * Refreshes beads positions to match models without any animations
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


    public void requestRepaint()
    {
        isRequestingRepaint = true;
    }


}
