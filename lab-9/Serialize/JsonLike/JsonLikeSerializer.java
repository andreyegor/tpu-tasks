package Serialize.JsonLike;

import Serialize.Serializer;

public class JsonLikeSerializer implements Serializer{
    JsonLikeDeserializeCore jsonLikeDeserializeCore;
    JsonLikeSerializeCore jsonLikeSerializeCore; 

    public JsonLikeSerializer(Class<?>... deserializables) {
        jsonLikeSerializeCore = new JsonLikeSerializeCore();
        jsonLikeDeserializeCore = new JsonLikeDeserializeCore(deserializables);
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