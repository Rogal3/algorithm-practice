import java.io.*;
import java.util.*;

/**
 * @see https://www.acmicpc.net/problem/11660
 * @author rogal
 */

public class Main {
    static FastIO io;

    static int n, m;
    static int[][] table;
    static int[][] queries;

    static int[][] cumulativeSum;

    static void readInput() {
        n = io.nextInt();
        m = io.nextInt();
        table = new int[n + 1][n + 1];
        for (int r = 1; r <= n; r++) {
            for (int c = 1; c <= n; c++) {
                table[r][c] = io.nextInt();
            }
        }
        queries = new int[m][4];
        for (int q = 0; q < m; q++) {
            for (int i = 0; i < 4; i++) {
                queries[q][i] = io.nextInt();
            }
        }
    }

    static void initCumulativeSum() {
        cumulativeSum = new int[n + 1][n + 1];
        for (int y = 1; y <= n; y++) {
            for (int x = 1; x <= n; x++) {
                cumulativeSum[y][x] = cumulativeSum[y - 1][x] + cumulativeSum[y][x - 1] - cumulativeSum[y - 1][x - 1] + table[y][x];
            }
        }
    }

    static int answer(int xBegin, int yBegin, int xEnd, int yEnd) {
        return cumulativeSum[yEnd][xEnd] - cumulativeSum[yBegin - 1][xEnd] - cumulativeSum[yEnd][xBegin - 1] + cumulativeSum[yBegin - 1][xBegin - 1];
    }

    static int answer(int[] query) {
        return answer(query[1], query[0], query[3], query[2]);
    }

    public static void main(String[] args) {
        io = FastIO.of(args);

        readInput();
        initCumulativeSum();
        for (int[] query : queries) {
            io.println(answer(query));
        }

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

