package com.cs2340.WaterNet.Model;

/**
 * an interface designed to replace java.util.function.Consumer as java.util.function cannot be
 * imported in current versions of android studio
 * Created by Pulkit Gupta on 4/5/2017.
 */
public interface Consumer<T> {
    void accept(T t);
}
