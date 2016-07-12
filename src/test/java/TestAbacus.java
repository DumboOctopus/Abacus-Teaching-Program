import com.dotneil.abacus.Abacus;
import com.dotneil.window.Operation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by neilprajapati on 7/11/16.
 * neilprajapati, dont forget to javaDoc this file.
 */
public class TestAbacus {
    public static void main(String[] args) {
        Abacus abacus = new Abacus();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.setVisible(true);
                frame.setLayout(new GridLayout(1, 2));
                frame.add(abacus, BorderLayout.CENTER);
                frame.setSize(600, 400);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                JButton button = new JButton("Add");
                button.addActionListener(e -> abacus.animateOperationAndNotify(Operation.ADDITION, 1342, null));
                frame.add(button, BorderLayout.NORTH);
                frame.addComponentListener(new ComponentListener() {
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


            }
        });
    }
}
