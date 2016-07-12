package com.dotneil;

import com.dotneil.abacus.AbacusDataModel;

import javax.swing.*;

/**
 * Created by neilprajapati on 7/8/16.
 * neilprajapati, dont forget to javaDoc this file.
 */
public class AbacusMainWindow extends JFrame{
    private AbacusDataModel dataModel;


    //===================CONSTRUCTOR + HELPERS==============================================//
    public AbacusMainWindow()
    {
        super("Abacus Main Window");

        setUpJMenu();

        this.setVisible(true);
        this.setSize(100,300);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void setUpJMenu()
    {

    }

}
