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

    private int[] beadsPositions; //the 5 bead is a zero index, rest going down....

    public AbacusColumn(AbacusDataModel model, int columnNumber) {
        this.model = model;
        this.columnNumber = columnNumber;
        beadsPositions = new int[5];
        refreshBeads();

        setOpaque(true);
    }


    //============================FROM JCOMPONENT=============================//
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, getHeight()/4, getWidth(), getHeight()/8);
        if(columnNumber %2 == 0) g.setColor(Color.ORANGE);
        else g.setColor(Color.darkGray);
        drawBead(beadsPositions[0], g);
        drawBead(beadsPositions[1], g);
        drawBead(beadsPositions[2], g);
        drawBead(beadsPositions[3], g);
        drawBead(beadsPositions[4], g);
    }
    private void drawBead(int height, Graphics g)
    {

        g.fillOval(getWidth()/6, height, 2*getWidth()/3, getHeight()/8);
    }

    //================================MAIN METHoDS===========================//

    /**
     * Animates the beads positions to match the model.
     */
    public void refreshBeadsAndWait()
    {
        repaint();
    }


    /**
     * Refreshes beads positions to match models without any animations
     */
    public void refreshBeads()
    {
        displayedValue = model.getColumn(columnNumber);
        beadsPositions[0] = displayedValue < 5 ? 0 : getHeight() / 8;
        beadsPositions[1]= displayedValue % 5 >= 1 ? 3 * getHeight() / 8 : 4 * getHeight() / 8;
        beadsPositions[2]= displayedValue % 5 >= 2 ? 4 * getHeight() / 8 : 5 * getHeight() / 8;
        beadsPositions[3]= displayedValue % 5 >= 3 ? 5 * getHeight() / 8 : 6 * getHeight() / 8;
        beadsPositions[4]= displayedValue % 5 >= 4 ? 6 * getHeight() / 8 : 7 * getHeight() / 8;
        repaint();
    }

}
