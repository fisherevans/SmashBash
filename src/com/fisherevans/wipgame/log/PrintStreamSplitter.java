import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

public class PrintStreamSplitter extends PrintStream {
  private Set<PrintStream> _writers;

  public PrintStreamSplitter(PrintStream... newWriters) {
    super(new NullOutputStream());
    _writers = new HashSet<PrintStream>();
    for (PrintStream writer : newWriters)
      addWriter(writer);
  }

  public void addWriter(PrintStream writer) {
    _writers.add(writer);
  }

  public void removeWriter(PrintStream writer) {
    _writers.remove(writer);
  }

  @Override
  public void flush() {
    for (PrintStream writer : _writers)
      writer.flush();
  }

  @Override
  public void close() {
    for (PrintStream writer : _writers)
      writer.close();
    _writers.clear();
  }

  @Override
  public void write(int b) {
    for (PrintStream writer : _writers)
      writer.write(b);
  }

  @Override
  public void write(byte[] buf, int off, int len) {
    for (PrintStream writer : _writers)
      writer.write(buf, off, len);
  }

  private static class NullOutputStream extends OutputStream {
    private NullOutputStream() {
    }
    @Override
    public void write(int b) throws IOException {
    }
  }
}
