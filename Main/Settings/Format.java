/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Settings;

import java.util.List;

/**
 *
 * @author ylo019
 */
public class Format {
    
    private final double DOUBLE_PRECISION = Math.pow(10, -12);
    private final String DOUBLE_OUTPUT = "%3.6f";
    private final String INTEGER_OUTPUT = "%2d";
    
    private static Format singleton;
    
    public boolean setFormat(Format f) {
        if (f != null) {
            singleton = f;
            return true;
        }
        return false;
    }
    
    public static String DoubleToString(Double d) {
        if (singleton == null) {
            singleton = new Format();
        }
        return singleton.doubleToString(d);
    }
    public static String IntToString(Integer i) {
        if (singleton == null) {
            singleton = new Format();
        }
        return singleton.intToString(i);
    }
    
    public static String ListToString(List l) {
        if (singleton == null) {
            singleton = new Format();
        }
        return singleton.listToString(l);
    }
    
    public static String DoubleArrayToString(double[] o) {
        if (singleton == null) {
            singleton = new Format();
        }
        return singleton.doubleArrayToString(o);
    }
    
    public static String Format(Object o) {
        if (singleton == null) {
            singleton = new Format();
        }
        return singleton.format(o);
    }
        
    public static boolean DoubleEqual(double a, double b) {
        if (singleton == null) {
            singleton = new Format();
        }
        return singleton.doubleEqual(a,b);
    }
    
    private String doubleToString(Double d) {
        return String.format(DOUBLE_OUTPUT, d);
    }
    private String intToString(Integer i) {
        return String.format(INTEGER_OUTPUT, i);
    }
    
    private String listToString(List l) {
        String s = "";
        for (Object o : l) {
            if (!s.equals(""))
                s += ", ";
            s += format(o);
        }
        return s;
    }
    
    private String doubleArrayToString(double[] o) {
        String s = "{ ";
        for (double d : o) {
            s += format(d) + " ";
        }
        s += "}";
        return s;
    }
    
    public String format(Object o) {
        if (o instanceof Double) {
            return DoubleToString((Double)o);
        } else if (o instanceof Integer) {
            return IntToString((Integer)o);
        } else if (o instanceof List) {
            return ListToString((List)o);
        } else if (o instanceof double[]) {
            return DoubleArrayToString((double[]) o);
        }
        return o.toString();
    }
    
    public boolean doubleEqual(double a, double b) {
        return Math.abs(a - b) < DOUBLE_PRECISION;
    }
}
