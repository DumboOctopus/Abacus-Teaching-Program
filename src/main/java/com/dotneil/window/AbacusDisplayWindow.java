package com.dotneil.window;

import com.dotneil.abacus.Abacus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.InputMismatchException;

/**
 *
 */
public class AbacusDisplayWindow extends JFrame {

    private Abacus abacus;
    private JTextField expressionField;

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

        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {

            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {
                abacus.reset();
            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });


        setSize(600, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }



    public void setUpMainMenu() {
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);


        JMenu submenu = new JMenu("Chapter");
        JMenuItem item;

        try {
            ClassLoader resourceLoader = Thread.currentThread().getContextClassLoader();
            BufferedReader reader;
            reader = new BufferedReader(
                    new InputStreamReader(
                            resourceLoader.getResourceAsStream("chapterData.txt"),
                            "UTF-8"
                    )
            );

            ButtonGroup buttonGroup = new ButtonGroup();
            String line;

            JMenu currSubMenu = submenu;
            while ((line = reader.readLine()) != null) {
                if (line.contains("//")) {
                    continue;
                }
                if (line.contains("{")) {
                    currSubMenu = new JMenu(line.replace("{", ""));
                    submenu.add(currSubMenu);
                    continue;
                } else if (line.contains("}")) {
                    currSubMenu = submenu;
                    continue;
                }
                item = generateFromLine(line);
                buttonGroup.add(item);
                currSubMenu.add(item);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        menuBar.add(submenu);



        item = new JMenuItem("Report a Bug");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Desktop desktop = Desktop.getDesktop();
                String message = "mailto:neilp0101@gmail.com?subject=Bug%20Dectected%20For%20Abacusteachin&body=Please%20Place%20Description%20Here";
                URI uri = URI.create(message);
                try {
                    desktop.mail(uri);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });
        menuBar.add(item);


    }

    public JRadioButtonMenuItem generateFromLine(String line) {
        String[] tokens = line.split(", ");

        String chapterName = tokens[0];
        Integer initial = Integer.parseInt(tokens[1]);
        Operation op = Operation.parseOperation(tokens[2]);
        Integer toOperate = Integer.parseInt(tokens[3]);


        JRadioButtonMenuItem item = new JRadioButtonMenuItem(chapterName);
        item.addActionListener(new ActionListener() {
            private Operation operation = op;
            private int num = toOperate;
            private int initalValue = initial;

            @Override
            public void actionPerformed(ActionEvent e) {
                abacus.reset(initalValue);
                abacus.animateOperationAndNotify(operation,num,null);
            }
        });

        return item;
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
