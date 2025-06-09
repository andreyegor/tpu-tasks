package Serialize;

import java.util.Map;

public interface CustomSerializable extends Serializable {
    String serialize(Object core);

    Object deserialize(Map<String, Object> data);
}
