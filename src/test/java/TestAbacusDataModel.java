import com.dotneil.abacus.AbacusDataModel;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by neilprajapati on 7/8/16.
 * neilprajapati, dont forget to javaDoc this file.
 */
public class TestAbacusDataModel {

    @Test
    public void testAdd0()
    {
        AbacusDataModel model = new AbacusDataModel();
        boolean[] out = model.add(0);
        assertEquals(1, out.length);
        assertArrayEquals(new boolean[]{false}, out);
    }

    @Test
    public void testAdd1()
    {
        AbacusDataModel model = new AbacusDataModel();
        boolean[] out = model.add(11);
        assertEquals(2, out.length);
        assertArrayEquals(new boolean[]{true, true}, out);
    }

    @Test
    public void testAdd2()
    {
        AbacusDataModel model = new AbacusDataModel();
        boolean[] out = model.add(10);
        assertEquals(2, out.length);
        assertArrayEquals(new boolean[]{false, true}, out);
    }

    @Test
    public void testAdd3()
    {
        AbacusDataModel model = new AbacusDataModel(23);
        boolean[] out = model.add(11);
        assertEquals(2, out.length);
        assertArrayEquals(new boolean[]{true, true}, out);
    }

    @Test
    public void testAdd4()
    {
        AbacusDataModel model = new AbacusDataModel(23);
        boolean[] out = model.add(123);
        assertEquals(3, out.length);
        assertArrayEquals(new boolean[]{true, true, true}, out);
    }

    @Test
    public void testAdd5()
    {
        AbacusDataModel model = new AbacusDataModel(23);
        boolean[] out = model.add(93);
        assertEquals(3, out.length);
        assertArrayEquals(new boolean[]{true, true, true}, out);
    }


    //=================SUBTRACTION=================================//
    @Test
    public void testSubtraction1()
    {
        AbacusDataModel model = new AbacusDataModel();
        boolean[] out = model.subtract(0);
        assertEquals(1, out.length);
        assertArrayEquals(new boolean[]{false}, out);
    }

    @Test
    public  void testSubtraction2()
    {
        AbacusDataModel model = new AbacusDataModel(90);
        boolean[] out = model.subtract(11);
        assertEquals(2, out.length);
        assertArrayEquals(new boolean[]{true, true}, out);

    }

    @Test
    public void testSubtraction3()
    {
        AbacusDataModel model = new AbacusDataModel(90);
        boolean[] out = model.subtract(10);
        assertEquals(2, out.length);
        assertArrayEquals(new boolean[]{false, true}, out);

    }

    @Test
    public void testSubtraction4()
    {
        AbacusDataModel model = new AbacusDataModel(100);
        boolean[] out = model.subtract(90);
        assertEquals(3, out.length);
        assertArrayEquals(new boolean[]{false, true, true}, out);

    }

    //====================GET COLUMN==========================//

    @Test
    public void testGetColumn()
    {
        AbacusDataModel model = new AbacusDataModel(100);
        assertEquals(1, model.getColumn(2));
        assertEquals(0, model.getColumn(1));
        assertEquals(0, model.getColumn(0));
    }

    @Test
    public void testGetColumnComplex()
    {
        AbacusDataModel model = new AbacusDataModel(159);
        assertEquals(1, model.getColumn(2));
        assertEquals(5, model.getColumn(1));
        assertEquals(9, model.getColumn(0));
    }

}
