/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordinalpreferencegenerator;

import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author ylo019
 */
public class ImpartialCultureFactory {
    
    public static iOrdinalIterator createImpartialCulture(Class<? extends iOrdinalIterator> oiClass, int param) {
        System.out.println(oiClass + " " + param);
        try {
            return oiClass.getDeclaredConstructor(Integer.TYPE).newInstance(new Integer(param));
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            System.out.println(ex);
            throw new RuntimeException("ImpartialCultureFactory: createImpartialCulture(Class, int): cannot create instance");
        }
    }
    
    public static iOrdinalIterator createImpartialCulture(Class<? extends iOrdinalIterator> oiClass, int agent, int object) {
        System.out.println(oiClass + " " + agent + " " + object);
        try {
            return oiClass.getDeclaredConstructor(Integer.TYPE, Integer.TYPE).newInstance(new Integer(agent), new Integer(object));
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            System.out.println(ex);
            ex.printStackTrace();
            throw new RuntimeException("ImpartialCultureFactory: createImpartialCulture(Class, int, int): cannot create instance");
        }
    }
    
}
