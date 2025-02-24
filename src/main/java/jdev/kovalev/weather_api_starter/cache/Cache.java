package jdev.kovalev.weather_api_starter.cache;

import java.util.Set;

public interface Cache<K, V> {
    V get(K key);
    void set(K key, V value);
    void remove(K key);
    Set<K> getAllKeys();
}
