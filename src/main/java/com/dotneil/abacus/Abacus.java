package com.dotneil.abacus;

import com.dotneil.window.Operation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;

/**
 * Created by neilprajapati on 7/8/16.
 * neilprajapati, dont forget to javaDoc this file.
 */
public class Abacus extends JComponent {
    public static final int NUM_COLUMNS = 8;

    private AbacusColumn[] columns;
    private AbacusDataModel model;


    //===================CONSTRUCTOR + HELPERS==============================================//
    public Abacus(){
        this.model = new AbacusDataModel();
        setLayout(new GridLayout(1, NUM_COLUMNS));
        initColumns();
    }

    private void initColumns()
    {
        columns = new AbacusColumn[NUM_COLUMNS];
        for (int i = 0; i < columns.length; i++)
        {
            columns[i] = new AbacusColumn(model, NUM_COLUMNS -1- i);
            add(columns[i]);
        }
    }


    /**
     * Given an operation, it display the changes needed on the abacus to
     * preform it.
     *
     * @param operation
     * @param num2
     *
     * @throws ArrayIndexOutOfBoundsException if the subtraction operation is not possible
     */
    public void animateOperationAndNotify(Operation operation, int num2, final AbacusAnimationListener listener)
    {
        switch (operation)
        {
            case ADDITION:
                model.add(num2);
                break;
            case SUBTRACTION:
                model.subtract(num2);
                break;
            case ASSIGNMENT:
                reset(num2);
                return;
            default:
                throw new InputMismatchException(operation+ " is not an operation lool");
        }

        Timer t = new Timer(
                50,
                new ActionListener() {
                    private int currIndex = 0;
                    private AbacusAnimationListener innerListener = listener;

                    public void actionPerformed(ActionEvent e) {
                        Timer thisTimer = (Timer)e.getSource();

                        if(!columns[currIndex].stepBeads())
                        {
                            currIndex ++;
                            if(currIndex >= NUM_COLUMNS)
                            {
                                if(innerListener != null){
                                    innerListener.onDone();
                                    innerListener = null;// to prevent it from going twice
                                }
                                Timer timer = (Timer) e.getSource();
                                timer.stop();

                            }
                        }
                    }
                }
        );
        t.start();
    }

    public void reset()
    {
        model.reset();
        for(AbacusColumn column: columns)
            column.refreshBeads();
        repaint();
    }

    public void reset(int num)
    {
        model.reset();
        model.add(num);
        for(AbacusColumn column: columns)
            column.refreshBeads();
        repaint();
    }


}
