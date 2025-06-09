package Serialize.JsonLike;

import Serialize.Serializer;

public class JsonLikeSerializer implements Serializer{
    JsonLikeDeserializeCore jsonLikeDeserializeCore;
    JsonLikeSerializeCore jsonLikeSerializeCore; 

    public JsonLikeSerializer() {
        jsonLikeSerializeCore = new JsonLikeSerializeCore();
        jsonLikeDeserializeCore = new JsonLikeDeserializeCore();
    }

    @Override
    public String serialize(Object obj){
        return jsonLikeSerializeCore.serialize(obj);
    };

    @Override
    public Object deserialize(String s){
        s = s.replaceAll("\\s+", "");
        return jsonLikeDeserializeCore.deserialize(s);
    };
}