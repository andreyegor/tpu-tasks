package Serialize.Stream;

import java.io.IOException;
import Serialize.Serializer;

public class OutputStreamSerializer {
    private final java.io.OutputStream out;
    private final Serializer serializer;

    public OutputStreamSerializer(java.io.OutputStream out, Serializer serializer) {
        this.out = out;
        this.serializer = serializer;
    }

    public void write(Object obj) throws IOException {
        String data = serializer.serialize(obj);
        out.write(data.getBytes());
    }

    public void close() throws IOException {
        out.close();
    }
}
