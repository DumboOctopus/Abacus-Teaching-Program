package com.dotneil.window;

import com.dotneil.abacus.Abacus;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.InputMismatchException;

/**
 *
 */
public class AbacusDisplayWindow extends JFrame {

    private Abacus abacus;
    private JTextField expressionField;


    //==========================CONSTRUTOR + HELPERS========================//
    public AbacusDisplayWindow() {

        setUpMainMenu();
        abacus = new Abacus();
        add(abacus, BorderLayout.CENTER);

        expressionField = new JTextField("+23");
        expressionField.addActionListener(e ->{
            doExpression(expressionField.getText());
            expressionField.requestFocus();
        });
        add(expressionField, BorderLayout.SOUTH);


        setSize(600, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }



    public void setUpMainMenu() {
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenuItem item = new JMenuItem("Report a Bug");

        item.addActionListener(e -> {
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    Desktop desktop = Desktop.getDesktop();
                    String message = "mailto:neilp0101@gmail.com?subject=Bug%20Dectected%20For%20Abacusteachin&body=Please%20Place%20Description%20Here";
                    URI uri = URI.create(message);
                    try {
                        desktop.mail(uri);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    return null;
                }
            };
            worker.execute();
        });
        menuBar.add(item);


    }

    //=================OTHER METHODS========================//

    public void doExpression(String expression)
    {
        expression = expression.replace(" ", "");
        try{
            Operation op = Operation.parseOperation("" + expression.charAt(0));
            int toAdd = Integer.parseInt(expression.substring(1, expression.length()));
            abacus.animateOperationAndNotify(op, toAdd, null);
        } catch (NumberFormatException e)
        {
            expressionField.setText(expression.substring(1, expression.length())+ " is not a valid number bruh. ");
        } catch (ArrayIndexOutOfBoundsException e)
        {
            expressionField.setText(expressionField.getText()+ " will make the number on the abacus negative. ");
        } catch (InputMismatchException e){
            expressionField.setText(expression.charAt(0) + " is not a command");
        }
        expressionField.requestFocus();

    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(AbacusDisplayWindow::new);
    }
    
    
    
}
