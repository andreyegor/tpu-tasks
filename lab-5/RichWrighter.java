import java.io.IOException;
import java.io.Writer;

// Base decorator class
abstract class RichWriter extends Writer {
    private final Writer writer;

    public RichWriter(Writer writer) {
        this.writer = writer;
    }

    abstract public void writePlot(Counter.Count data, boolean sortDescending, boolean sortByCount)
            throws IOException;

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        writer.write(cbuf, off, len);
    }

    @Override
    public void flush() throws IOException {
        writer.flush();
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}

class VerticalPlotWriter extends RichWriter {

    public VerticalPlotWriter(Writer writer) {
        super(writer);
    }

    @Override
    public void writePlot(Counter.Count data, boolean sortDescending, boolean sortByCount)
            throws IOException {
        var plot = Plot.generate(data, false, sortDescending, sortByCount);
        this.write(plot);
    }
}

class HorizontalPlotWriter extends RichWriter {

    public HorizontalPlotWriter(Writer writer) {
        super(writer);
    }

    @Override
    public void writePlot(Counter.Count data, boolean sortDescending, boolean sortByCount) throws IOException{
        var plot = Plot.generate(data, true, sortDescending, sortByCount);
        this.write(plot);
    }
}