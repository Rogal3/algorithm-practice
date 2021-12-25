import java.io.*;
import java.util.*;

/**
 * @see
 * @author rogal
 */

public class Main {
    static InputReader reader;
    static PrintWriter writer = new PrintWriter(System.out);

    public static void main(String[] args) {
        reader = InputReader.of(args);

        // do something

        writer.close();
    }
}

class InputReader {
    private InputStream in;
    private final byte[] buffer;
    private final int bufferSize;
    private int readCount;
    private int bufferIndex;

    private InputReader(InputStream inputStream) {
        bufferSize = 1 << 16;
        buffer = new byte[bufferSize];
        in = inputStream;
    }

    static InputReader of(String[] args) {
        if (Arrays.asList(args).contains("--local")) {
            try {
                return new InputReader(new FileInputStream("input.txt"));
            } catch (Exception e) {
                throw new RuntimeException();
            }
        } else {
            return new InputReader(System.in);
        }
    }

    int fill() {
        try {
            readCount = in.read(buffer, bufferIndex = 0, bufferSize);
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return readCount;
    }

    int read() {
        if (bufferIndex >= readCount) {
            if (fill() < 1) {
                return -1;
            }
        }
        return buffer[bufferIndex++];
    }

    String next() {
        StringBuilder stringBuilder = new StringBuilder();
        int c;
        while (isDelimiter(c = read()));
        while (!isDelimiter(c) && c != -1) {
            stringBuilder.append((char) c);
            c = read();
        };
        return stringBuilder.toString();
    }

    int nextInt() {
        return Integer.parseInt(next());
    }

    boolean isDelimiter(int c) {
        return c == ' ' || c == '\t' || c == '\n' || c == '\r' || c == '\f';
    }
}