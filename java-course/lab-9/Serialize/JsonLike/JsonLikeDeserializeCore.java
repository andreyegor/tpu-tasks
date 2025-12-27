package Serialize.JsonLike;

import java.util.Map;

import Serialize.CustomSerializable;
import Serialize.Serializable;
import Serialize.core.DeserializeCore;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import sun.misc.Unsafe;
import java.lang.reflect.Field;

public class JsonLikeDeserializeCore implements DeserializeCore {
    private static final char OPNM = '{';
    private static final char OPNI = '(';
    private static final char OPNA = '[';
    private static final char CLSM = '}';
    private static final char CLSI = ')';
    private static final char CLSA = ']';
    private static final char SPKV = ':';
    private static final char SPEL = ',';

    private static final String FRB_CHR = "{}()[]\",:";
    private static final String FRB_CHR_RE = "^[^\\{\\}\\(\\)\\[\\]\",:]*$";

    private static final char INTEGER_LST = 'I';
    private static final char DOUBLE_LST = 'D';
    private static final String CLASS_KEY = "__classtype__";

    @Override
    public Object deserialize(String s) {
        s = s.trim();
        char first = s.charAt(0);
        char last = s.charAt(s.length() - 1);
        switch ("" + first + last) {
            case "" + OPNM + CLSM:
                return maybeObject(deserializeMap(s));
            case "" + OPNI + CLSI:
                return deserializeList(s);
            case "" + OPNA + CLSA:
                return deserializeArray(s);
        }
        switch (last) {
            case INTEGER_LST:
                return deserializeInt(s);
            case DOUBLE_LST:
                return deserializeDouble(s);
        }
        return deserializeSimpleString(s);
    }

    private Map<String, Object> deserializeMap(String s) {
        Map<String, Object> map = new HashMap<>();
        s = s.substring(1, s.length() - 1).trim();
        if (s.isEmpty())
            return map;

        for (var line : splitSeries(s)) {
            var keyval = line.split("" + SPKV, 2);
            if (keyval.length != 2) {
                throw new IllegalArgumentException("Can't deserialize \"%s\" as MapEntity".formatted(s));
            }
            map.put(keyval[0], deserialize(keyval[1]));
        }
        return map;
    }

    private List<Object> deserializeList(String s) {
        List<Object> list = new ArrayList<>();
        s = s.substring(1, s.length() - 1).trim();
        if (s.isEmpty())
            return list;
        int depth = 0, last = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == OPNM || c == OPNI || c == OPNA)
                depth++;// тут будет баг со скобками в строке
            if (c == CLSM || c == CLSI || c == CLSA)
                depth--;
            if (c == SPEL && depth == 0) {
                String value = s.substring(last, i).trim();
                list.add(deserialize(value));
                last = i + 1;
            }
        }
        if (last < s.length()) {
            String value = s.substring(last).trim();
            list.add(deserialize(value));
        }
        return list;
    }

    private Object deserializeArray(String s) {
        List<Object> list = new ArrayList<>();
        s = s.substring(1, s.length() - 1).trim();

        if (s.isEmpty())
            return list;
        int depth = 0, last = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == OPNM || c == OPNI || c == OPNA)
                depth++;// тут будет баг со скобками в строке
            if (c == CLSM || c == CLSI || c == CLSA)
                depth--;
            if (c == SPEL && depth == 0) {
                String value = s.substring(last, i).trim();
                list.add(deserialize(value));
                last = i + 1;
            }
        }
        if (last < s.length()) {
            String value = s.substring(last).trim();
            list.add(deserialize(value));
        }
        return list.toArray();
    }

    private Integer deserializeInt(String s) {
        try {
            return Integer.parseInt(s.substring(0, s.length() - 1));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Can't deserialize \"%s\" as Integer".formatted(s));
        }
    }

    private Double deserializeDouble(String s) {
        try {
            return Double.parseDouble(s.substring(0, s.length() - 1));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Can't deserialize \"%s\" as Double".formatted(s));
        }
    }

    private String deserializeSimpleString(String s) {
        if (s.matches(FRB_CHR_RE)) {
            return s;
        } else {
            throw new IllegalArgumentException(
                    "String \"%s\" contains forbidden characters: \"%s\"".formatted(s, FRB_CHR));
        }
    }

    Object maybeObject(Map<String, Object> map) {
        if (!(map.containsKey(CLASS_KEY)))
            return map;
        var className = (String) map.get(CLASS_KEY);

        Class<?> classs;
        try {
            classs = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to auto-deserialize %s - class not found".formatted(className), e);
        }
        if (CustomSerializable.class.isAssignableFrom(classs)) {
            map.remove(CLASS_KEY);
            try {
                var method = classs.getMethod("deserialize", Map.class);// 😭
                return method.invoke(null, map);
            } catch (Exception e) {
                throw new RuntimeException("Failed to run deserialize method for " + className, e);
            }
        }
        if (!Serializable.class.isAssignableFrom(classs)) {
            return map;
        }
        map.remove(CLASS_KEY);
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe unsafe = (Unsafe) theUnsafe.get(null);

            Object instance = unsafe.allocateInstance(classs);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Field field = findField(classs, entry.getKey());
                if (field == null)
                    continue;

                Object rawValue = entry.getValue();
                Class<?> fieldType = field.getType();
                field.setAccessible(true);
                if (fieldType.isArray() && rawValue instanceof Object[]) {
                    @SuppressWarnings("unchecked")
                    Object typedArray = Arrays.copyOf((Object[]) rawValue,
                            ((Object[]) rawValue).length,
                            (Class<? extends Object[]>) fieldType);
                    field.set(instance, typedArray);
                } else {
                    field.set(instance, rawValue);
                }

            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Failed to auto-deserialize " + className, e);
        }
    }

    private Field findField(Class<?> cls, String name) {
        while (cls != null) {
            try {
                Field f = cls.getDeclaredField(name);
                f.setAccessible(true);
                return f;
            } catch (NoSuchFieldException ex) {
                cls = cls.getSuperclass();
            }
        }
        return null;
    }

    private ArrayList<String> splitSeries(String s) {
        var out = new ArrayList<String>();
        int depth = 0, last = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == OPNM || c == OPNI || c == OPNA)
                depth++;
            if (c == CLSM || c == CLSI || c == CLSA)
                depth--;
            if (c == SPEL && depth == 0) {
                out.add(s.substring(last, i).trim());
                last = i + 1;
            }
        }
        out.add(s.substring(last));
        return out;
    }

}
