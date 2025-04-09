import java.io.Reader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Queue;
import java.util.LinkedList;

public class RichReader extends Reader {
    private final Queue<Reader> readers;

    public RichReader(Reader... readers) {
        this.readers = new LinkedList<>();
        this.readers.addAll(Arrays.asList(readers));
    }

    public boolean add(Reader reader) {
        return this.readers.add(reader);
    }

    public int size() {
        return this.readers.size();
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        if (readers.isEmpty())
            return -1;
        int out = readers.element().read(cbuf, off, len);
        if (out != -1)
            return out;
        readers.element().close();
        readers.poll();
        return read(cbuf, off, len);
    }

    @Override
    public void close() throws IOException {
        for (Reader reader : readers) // ?
            reader.close();
    }
}
