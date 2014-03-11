package com.fisherevans.wipgame.log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

public class PrintStreamSplitter extends PrintStream {
  private PrintStream _original, _intercept;

  public PrintStreamSplitter(PrintStream original, PrintStream intercept) {
    super(new NullOutputStream());
    _original = original;
    _intercept = intercept;
  }

  public PrintStream getOriginal() {
    return _original;
  }

  public PrintStream getIntercept() {
    return _intercept;
  }

  @Override
  public void flush() {
    _original.flush();
    _intercept.flush();
  }

  @Override
  public void close() {
    _original.close();
    _intercept.close();
  }

  @Override
  public void write(int b) {
    _original.write(b);
    _intercept.write(b);
  }

  @Override
  public void write(byte[] buf, int off, int len) {
    _original.write(buf, off, len);
    _intercept.write(buf, off, len);
  }

  private static class NullOutputStream extends OutputStream {
    private NullOutputStream() {
    }
    @Override
    public void write(int b) throws IOException {
    }
  }
}
