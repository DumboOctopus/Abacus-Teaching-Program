package abacus;

import javax.swing.*;

/**
 * Created by neilprajapati on 7/8/16.
 * neilprajapati, dont forget to javaDoc this file.
 */
public class Abacus extends JComponent {
    public static final int NUM_COLUMNS = 10;

    private AbacusColumn[] columns;
    private AbacusDataModel model;


    //===================CONSTRUCTOR + HELPERS==============================================//
    public Abacus(){
        this.model = new AbacusDataModel();
        initColumns();
    }

    public Abacus(AbacusDataModel model) {
        this.model = model;
        initColumns();
    }

    private void initColumns()
    {
        columns = new AbacusColumn[NUM_COLUMNS];
        for (int i = 0; i < columns.length; i++)
        {
            columns[i] = new AbacusColumn(model, i);
        }
    }


    /**
     * Given an operation, it display the changes needed on the abacus to
     * preform it.
     * @param num1
     * @param operation
     * @param num2
     */
    public void doOperationAndWait(int num1, String operation, int num2)
    {

    }

    public void reset()
    {
    }





}
