package ru.popov.bodya.customcounter;

/**
 * @author popovbodya
 */

public interface BodyaCounter {

    void reset();

    void increment();

    int getCount();

    void setCount(int count);

}
