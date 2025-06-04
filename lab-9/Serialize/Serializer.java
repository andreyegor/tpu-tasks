package Serialize;

public interface Serializer {
    String serialize(Object obj);

    Object deserialize(String s);
}