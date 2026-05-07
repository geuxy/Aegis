package gg.aegis.util.collection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.LinkedList;

@Getter @RequiredArgsConstructor
public class EvictedList<T> extends LinkedList<T> {

    /*
     * Maximum size of
     */
    private final int maxSize;

    public boolean isFull() {
        return size() == maxSize;
    }

    @Override
    public boolean add(T t) {
        if(size() > maxSize) {
            removeFirst();
        }
        return super.add(t);
    }

    @Override @Deprecated
    public void add(int index, T element) {
    }

    @Override @Deprecated
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override @Deprecated
    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    @Override @Deprecated
    public void addFirst(T t) {
    }

    @Override @Deprecated
    public void addLast(T t) {
    }

}