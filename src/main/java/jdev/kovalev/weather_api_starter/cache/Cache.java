package jdev.kovalev.weather_api_starter.cache;

public interface Cache<K, V> {
    V get(K key);
    void set(K key, V value);
    void remove(K key);
}
