package fr.enst.infsi351.notedown.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Created by francois on 14/04/15.
 */
public class BiMap<X, Y> {

    private final Map<X, Y> xyMap;
    private final Map<Y, X> yxMap;

    public BiMap() {
        xyMap = new HashMap<>();
        yxMap = new HashMap<>();
    }

    public void clear() {
        xyMap.clear();
        yxMap.clear();
    }

    public boolean containsKey(X key) {
        return xyMap.containsKey(key);
    }


    public boolean containsValue(Y value) {
        return xyMap.containsValue(value);
    }

    public Set<Entry<X, Y>> entrySet() {
        return xyMap.entrySet();
    }

    public Y get(X key) {
        return xyMap.get(key);
    }

    public boolean isEmpty() {
        return xyMap.isEmpty();
    }

    public Set<X> keySet() {
        return xyMap.keySet();
    }

    public Y remove(X key) {
        Y ret = xyMap.remove(key);
        yxMap.remove(ret);
        return ret;
    }

    public int size() {
        return xyMap.size();
    }

    public Collection<Y> values() {
        return xyMap.values();
    }

    public Y put(X key, Y value) {
        yxMap.put(value, key);
        return xyMap.put(key, value);
    }

    public boolean reverseContainsKey(Y key) {
        return yxMap.containsKey(key);
    }


    public boolean reverseContainsValue(X value) {
        return yxMap.containsValue(value);
    }

    public Set<Entry<Y, X>> reverseEntrySet() {
        return yxMap.entrySet();
    }

    public X reverseGet(Y key) {
        return yxMap.get(key);
    }


    public Set<Y> reverseKeySet() {
        return yxMap.keySet();
    }

    public X reverseRemove(Y key) {
        X ret = yxMap.remove(key);
        xyMap.remove(ret);
        return ret;
    }

    public Collection<X> reverseValues() {
        return yxMap.values();
    }

    public X reversePut(Y key, X value) {
        xyMap.put(value, key);
        return yxMap.put(key, value);
    }


}
