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

    static class FastIO extends PrintStream {
        private static final int bufferSize = 1 << 16;
        private InputStream in;

        FastIO(String[] args) throws IOException {
            super(new BufferedOutputStream(System.out, bufferSize));
            InputStream inputStream;
            if (Arrays.asList(args).contains("--local")) {
                inputStream = new FileInputStream("input.txt");
            } else {
                inputStream = System.in;
            }
            in = new BufferedInputStream(inputStream, bufferSize);
        }

        String next() throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            int b;
            while (isDelimiter(b = in.read()));
            do {
                stringBuilder.append((char) b);
            } while (!isDelimiter(b = in.read()));
            return stringBuilder.toString();
        }

        int nextInt() throws IOException {
            int mag = 0;
            int sign = 1;
            int b;
            while (isDelimiter(b = in.read()));
            if (b == '-') {
                sign = -1;
                b = in.read();
            }
            do {
                mag = mag * 10 + b - '0';
            } while (!isDelimiter(b = in.read()));
            return sign * mag;
        }

        boolean isDelimiter(int c) {
            return c == ' ' || c == '\t' || c == '\n' || c == '\r' || c == '\f' || c == -1;
        }
    }
}

