import com.dotneil.abacus.AbacusColumn;
import com.dotneil.abacus.AbacusDataModel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by neilprajapati on 7/8/16.
 * neilprajapati, dont forget to javaDoc this file.
 */
public class TestAbacusColumn {
    public static void main(String[] args) {
        AbacusDataModel model = new AbacusDataModel(0);
        AbacusColumn column = new AbacusColumn(model, 0);
        AbacusColumn column1 = new AbacusColumn(model, 1);


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.setVisible(true);
                frame.setLayout(new GridLayout(1, 2));
                frame.add(column1);
                frame.add(column);
                frame.setSize(100, 400);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                Timer t = new Timer(50, e -> {

                    boolean[] updates = model.add(11);
                    if(updates[0])
                        column.animateBeadsAndNotify(null);
                    if(updates.length >= 2 && updates[1])
                        column1.animateBeadsAndNotify(null);
                });
                t.start();
            }
        });
    }
}
