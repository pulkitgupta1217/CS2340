package com.cs2340.WaterNet.Factory;

/**
 * an interface designed to replace java.util.function.Consumer as java.util.function cannot be
 * imported in current versions of android studio
 * Created by Pulkit Gupta on 4/5/2017.
 */
public interface Consumer<T> {
    /**
     * accept
     * @param t the returned item necessary for callback
     */
    void accept(T t);
}
