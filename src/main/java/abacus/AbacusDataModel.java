package abacus;

/**
 * Represents the bead configuration of an unknown size abacus.
 * It can only represent a mValue up to Integer.MAX_VALUE
 */
public class AbacusDataModel {

    //mValue on the abacus. mValue >= 0
    private int mValue;



    //===============================CONSTRUCTORS==============================//
    public AbacusDataModel() {}
    public AbacusDataModel(int value){
        mValue = value;
    }


    /**
     * Adds <code>toAdd</code> to the abacus sum.
     *
     * @param toAdd the number to add on the abacus
     * @return the columns which have changed. (0 indexed). if out[i] == true then column i needs updating
     *
     */
    public boolean[] add(int toAdd)
    {

        char[] after = ( toAdd + mValue  + "").toCharArray(); //[3, 4, 3, 1]
        boolean[] updates = new boolean[after.length]; //[f,f,f,f]
        char[] before = (mValue + "").toCharArray(); //[1, 2, 3]

        if(after.length != before.length)
        {
            //we either must have added columns
            //not before.length - 1 because we don't want to include last index of before
            for (int i = before.length; i < after.length; i++) {
                updates[i] = true;
            }
        }

        for(int distFromEnd = 0; distFromEnd < after.length; distFromEnd++)
        {
            if(distFromEnd < before.length && after[after.length - 1 - distFromEnd] != before[before.length -1 - distFromEnd])
            {
                updates[distFromEnd] = true;
            }
        }

        mValue += toAdd;
        return updates;
    }

    /**
     * Subtracts <code>toSubtract</code> from abacus.
     *
     * @param toSubtract the number to subtract from the abacus
     * @return the columns which have changed. (0 indexed). if out[i] == true then column i needs updating
     * @throws ArithmeticException if the subtraction operation would cause
     *                              abacus to have a negative sum.
     */
    public boolean[] subtract(int toSubtract)
    {
        if(mValue + toSubtract < 0)
            throw new ArithmeticException("Cannot subtract " + toSubtract + " from Abacus of value " + mValue);

        char[] after = ( mValue - toSubtract  + "").toCharArray(); //[3, 4, 3, 1]
        char[] before = (mValue + "").toCharArray(); //[1, 2, 3]
        boolean[] updates = new boolean[before.length]; //[f,f,f,f]

        if(after.length != before.length)
        {
            //we either must have removed columns
            //not after.length - 1 because we don't want to include last index of before
            for (int i = after.length; i < before.length; i++) {
                updates[i] = true;
            }
        }

        for(int distFromEnd = 0; distFromEnd < after.length; distFromEnd++)
        {
            if(after[after.length - 1 - distFromEnd] != before[before.length -1 - distFromEnd])
            {
                updates[distFromEnd] = true;
            }
        }
        mValue -= toSubtract;
        return updates;
    }

    /**
     * identifies the digit stored in a certain column
     * @param columnNumber the column index (indexed from 0)
     * @return the digit in that place.
     */
    public int getColumn(int columnNumber)
    {
        char[] ca = (mValue+"").toCharArray();
        if(columnNumber < ca.length)
            return ca[ca.length - 1 - columnNumber] - 48;
        return 0;
    }


}
