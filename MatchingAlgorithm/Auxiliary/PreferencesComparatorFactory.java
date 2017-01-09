/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.Auxiliary;

import Main.Settings.Format;
import Main.Settings.Settings;
import java.util.Comparator;

/**
 *
 * @author ylo019
 */
public abstract class PreferencesComparatorFactory {
    
    public static PartialComparator<double[]> buildDoubleArrayComparator(iIterator preferences) {
        preferences.resetPointer();
        return new PartialComparator<double[]>() {

            @Override
            public PartialComparatorResult compare(double[] self, double[] other) {
                if (preferences == null || preferences.size() != self.length || preferences.size() != other.length) {
                    throw new RuntimeException("buildDoubleArrayComparator: preferences == null || one of the array has wrong size");
                }
                double selfSum = 0;
                double otherSum = 0;
                boolean selfHigher = false;
                boolean otherHigher = false;
                while (preferences.hasNext()) {
                    int obj = preferences.getNext() - 1;
                    selfSum += self[obj];
                    otherSum += other[obj];
                    if (!Format.DoubleEqual(selfSum, otherSum)) {
                        if (selfSum > otherSum) {
                            selfHigher = true;
                        } else if (otherSum > selfSum) {
                            otherHigher = true;
                        }
                    }
                }
                if (selfHigher && otherHigher) {
                    return PartialComparatorResult.INCOMPARABLE;
                } else if (selfHigher && !otherHigher) {
                    return PartialComparatorResult.GREATER;
                } else if (!selfHigher && otherHigher) {
                    return PartialComparatorResult.LESS;
                } else if (!selfHigher && !otherHigher) {
                    return PartialComparatorResult.EQUAL;
                }
                throw new RuntimeException("compare: selfHigher/otherHigher changed mid-check. Threading issue indicated.");
            }

        };
    }
    
}
