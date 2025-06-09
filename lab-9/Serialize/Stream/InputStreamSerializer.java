package Serialize.Stream;

import java.io.IOException;
import Serialize.Serializer;

public class InputStreamSerializer {
    private final java.io.InputStream in;
    private final Serializer serializer;

    public InputStreamSerializer(java.io.InputStream in, Serializer serializer) {
        this.in = in;
        this.serializer = serializer;
    }

    public Object read() throws IOException {
        byte[] buffer = in.readAllBytes();
        String data = new String(buffer);
        return serializer.deserialize(data);
    }

    public void close() throws IOException {
        in.close();
    }
}
