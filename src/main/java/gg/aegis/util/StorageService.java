package gg.aegis.util;

@FunctionalInterface
public interface StorageService<T> {

    void save(T content);

}
