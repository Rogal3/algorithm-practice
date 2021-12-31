import java.io.*;
import java.util.*;

/**
 * @see 
 * @author rogal
 */

public class Main {
    static FastIO io;

    public static void main(String[] args) {
        io = FastIO.of(args);

        // do something

        io.flush();
    }

    static class FastIO extends PrintWriter {
        private static final int bufferSize = 1 << 16;
        private InputStream in;

        private FastIO(InputStream in, boolean autoFlush) {
            super(new BufferedOutputStream(System.out, bufferSize), autoFlush);
            this.in = new BufferedInputStream(in, bufferSize);
        }

        static FastIO of(String[] args) {
            if (Arrays.asList(args).contains("--local")) {
                try {
                    return new FastIO(new FileInputStream("input.txt"), true);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            return new FastIO(System.in, false);
        }

        String next() {
            StringBuilder stringBuilder = new StringBuilder();
            int b;
            try {
                while (isDelimiter(b = in.read()));
                do {
                    stringBuilder.append((char) b);
                } while (!isDelimiter(b = in.read()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return stringBuilder.toString();
        }

        int nextInt() {
            int mag = 0;
            int sign = 1;
            int b;
            try {
                while (isDelimiter(b = in.read()));
                if (b == '-') {
                    sign = -1;
                    b = in.read();
                }
                do {
                    mag = mag * 10 + b - '0';
                } while (!isDelimiter(b = in.read()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return sign * mag;
        }

        boolean isDelimiter(int c) {
            return c == ' ' || c == '\t' || c == '\n' || c == '\r' || c == '\f' || c == -1;
        }
    }
}

