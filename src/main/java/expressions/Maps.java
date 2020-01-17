/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author martinstraus
 */
public class Maps {

    public static <T> Map<String, T> of(String key, T value) {
        Map<String, T> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    public static Map<String, Object> of(String key1, Object value1, String key2, Object value2) {
        Map<String, Object> map = new HashMap<>();
        map.put(key1, value1);
        map.put(key2, value2);
        return map;
    }

    public static Map<String, Object> of(Object... values) {
        if (values.length % 2 != 0) {
            throw new IllegalArgumentException("Only pair number of arguments supported.");
        }
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < values.length; i += 2) {
            map.put((String) values[i], values[i + 1]);
        }
        return map;
    }
}
