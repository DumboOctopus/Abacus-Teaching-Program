package com.dotneil.window;

/**
 * Created by neilprajapati on 7/11/16.
 * neilprajapati, dont forget to javaDoc this file.
 */
/**
 * Created on 2/21/16.
 */
public enum Operation {
    ADDITION("+" ),
    SUBTRACTION("-" ),
    MULTIPLICATION("*" ),
    DIVISION("/" ),
    ASSIGNMENT("=");
    


    //======================ENUM STUFF======================//
    private final String asString;

    private Operation(String asString)
    {
        this.asString = asString;
    }

    @Override
    public String toString() {
        return asString;
    }

    public static Operation parseOperation(String s)
    {
        for(Operation op: values())
        {
            if(op.asString.equals(s))return op;
        }
        return ADDITION;
    }


}
