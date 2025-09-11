package Serialize.JsonLike;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Function;

import Serialize.Serializable;
import Serialize.CustomSerializable;
import Serialize.core.SerializeCore;

public class JsonLikeSerializeCore implements SerializeCore {
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

    public static final String CLASS_KEY = "__classtype__";

    private final Map<Class<?>, Function<Object, String>> serializers = Map.of(
            Integer.class, obj -> serializeInteger((Integer) obj),
            Double.class, obj -> serializeDouble((Double) obj),
            String.class, obj -> serializeSimpleString((String) obj));

    @Override
    public String serialize(Object obj) {
        // мне НЕ нравится эта часть кода, но я хз как лучше
        if (obj == null) {
            return "null";
        }
        if (obj instanceof CustomSerializable) {
            return ((CustomSerializable) obj).serialize(this);
        }
        Function<Object, String> serializer = serializers.get(obj.getClass());
        if (serializer != null) {
            return serializer.apply(obj);
        }
        if (obj instanceof Map) {
            @SuppressWarnings("unchecked")
            var r = serializeMap((Map<String, Object>) obj);
            return r;
        }
        if (obj instanceof Iterable) {
            @SuppressWarnings("unchecked")
            var r = serializeIterable((List<Object>) obj);
            return r;
        }
        if (obj != null && obj.getClass().isArray()) {
            return serializeArray(obj);
        }
        if (!(obj instanceof Serializable)) {
            throw new IllegalArgumentException("Can't serialize \"%s\".".formatted(obj.getClass().getName()));
        }
        Class<?> classs = obj.getClass();
        List<java.lang.reflect.Field> fieldsList = new ArrayList<>();

        Class<?> current = classs;
        while (current != null && current != Object.class) {
            var declaredFields = current.getDeclaredFields();
            for (var field : declaredFields) {
                fieldsList.add(field);
            }
            current = current.getSuperclass();
        }

        var outputFields = new HashMap<String, Object>();
        outputFields.put(serialize(CLASS_KEY), classs.getName());
        for (java.lang.reflect.Field field : fieldsList) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers()) &&
                    java.lang.reflect.Modifier.isFinal(field.getModifiers())) {
                continue;
            }
            var key = field.getName();
            Object val = null;
            field.setAccessible(true);
            try {
                val = field.get(obj);
            } catch (IllegalAccessException e) {
            }
            outputFields.put(key, val);
        }
        return serializeMap((Map<String, Object>) outputFields);
    }

    private String serializeMap(Map<String, Object> map) {
        var v = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object obj = entry.getValue();
            v.add(serialize(key) + SPKV + serialize(obj));
        }
        return OPNM + String.join(""+SPEL, v) + CLSM;
    }

    private String serializeIterable(Iterable<Object> iter) {
        var v = new ArrayList<String>();
        for (Object obj : iter) {
            v.add(serialize(obj));
        }
        return OPNI + String.join(""+SPEL, v) + CLSI;
    }

    private String serializeArray(Object array) {
        int length = java.lang.reflect.Array.getLength(array);
        var v = new ArrayList<String>(length);
        for (int i = 0; i < length; i++) {
            Object element = java.lang.reflect.Array.get(array, i);
            v.add(serialize(element));
        }
        return OPNA + String.join(""+SPEL, v) + CLSA;
    }

    private String serializeInteger(Integer in) {
        return Integer.toString(in) + INTEGER_LST;
    }

    private String serializeDouble(Double in) {
        return Double.toString(in) + DOUBLE_LST;
    }

    private String serializeSimpleString(String s) {
        if (s.matches(FRB_CHR_RE)) {
            return s;
        } else {
            throw new IllegalArgumentException("String \"%s\" contains forbidden characters: \"%s\"".formatted(s, FRB_CHR));
        }
    }
}
