import java.io.*;
import java.util.*;

/**
 * @see
 * @author rogal
 */

public class Main {
    static FastIO io;

    public static void main(String[] args) throws IOException {
        io = new FastIO(args);

        // do something

        io.flush();
    }

    static class FastIO {
        private BufferedInputStream in;
        private PrintStream out;
        private final int bufferSize;

        FastIO(String[] args) {
            bufferSize = 1 << 16;
            InputStream inputStream = null;
            if (Arrays.asList(args).contains("--local")) {
                try {
                    inputStream = new FileInputStream("input.txt");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                inputStream = System.in;
            }
            in = new BufferedInputStream(inputStream, bufferSize);
            out = new PrintStream(new BufferedOutputStream(System.out, bufferSize));
        }

        String next() throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            int b;
            while (isDelimiter(b = in.read()));
            while (!isDelimiter(b)) {
                stringBuilder.append((char) b);
                b = in.read();
            }
            return stringBuilder.toString();
        }

        int nextInt() throws IOException {
            int integer = 0;
            int sign = 1;
            int b;
            while (isDelimiter(b = in.read()));
            if (b == '-') {
                sign = -1;
            }
            while (!isDelimiter(b)) {
                integer = integer * 10 + b - '0';
                b = in.read();
            }
            return sign * integer;
        }

        boolean isDelimiter(int c) {
            return c == ' ' || c == '\t' || c == '\n' || c == '\r' || c == '\f' || c == -1;
        }

        PrintStream printf(String format, Object ...args) {
            return out.printf(format, args);
        }

        void flush() throws IOException {
            out.flush();
        }
    }
}

