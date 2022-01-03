import java.io.*;
import java.util.*;

/**
 * @see https://www.acmicpc.net/problem/15684
 * @author rogal
 */

public class Main {
    static FastIO io;

    static int n, m, h;

    /**
     *  if map[y][x] == 1 then go right
     *  if map[y][x] == 2 then go left
     */
    static int[][] map;

    static void readInput() {
        n = io.nextInt();
        m = io.nextInt();
        h = io.nextInt();
        map = new int[h + 1][n + 1];
        for (int bridge = 0; bridge < m; bridge++) {
            int height = io.nextInt();
            int vertical = io.nextInt();
            map[height][vertical] = 1;
            map[height][vertical + 1] = 2;
        }
    }

    static int findIncompleteCount() {
        int incompleteCount = 0;
        for (int x = 1; x <= n; x++) {
            int num = 0;
            for (int y = 1; y <= n; y++)
                if (map[y][x] == 1) num++;
            if (num % 2 == 1) incompleteCount++;
        }
        return incompleteCount;
    }

    static boolean validate() {
        for (int x = 1; x <= n; x++) {
            int currentX = x;
            for (int y = 1; y <= h; y++) {
                if (map[y][currentX] == 1) {
                    currentX++;
                } else if (map[y][currentX] == 2) {
                    currentX--;
                }
            }
            if (x != currentX) {
                return false;
            }
        }
        return true;
    }

    static void printMap() {
        for (int y = 1; y <= h; y++) {
            for (int x = 1; x <= n; x++) {
                io.print(map[y][x]);
                io.print(' ');
            }
            io.println();
        }
        io.println("================");
    }

    /**
     * @param lastBridgeIndex A bridge (y, x) is a bridgeIndex ((x - 1) * h + y).
     *                        A bridgeIndex (i) is a bridge ((i - 1) % h + 1, (i - 1) / h + 1).
     * @param bridgeCount count of created bridges.
     * @param maxBridgeCount max count of bridgeCount
     * @return can be validated.
     */
    static boolean setBridgeAndValidate(int lastBridgeIndex, int bridgeCount, int maxBridgeCount) {
        boolean isValidated;
        if (bridgeCount == maxBridgeCount) {
            isValidated = validate();
            // io.printf("bridgeCount = %d\n", bridgeCount);
            // printMap();
            return isValidated;
        } else {
            for (int nextBridgeIndex = lastBridgeIndex + 1; nextBridgeIndex <= h * (n - 1); nextBridgeIndex++) {
                int nextBridgeY = (nextBridgeIndex - 1) % h + 1;
                int nextBridgeX = (nextBridgeIndex - 1) / h + 1;
                if (map[nextBridgeY][nextBridgeX] == 0 && map[nextBridgeY][nextBridgeX + 1] == 0) {
                    map[nextBridgeY][nextBridgeX] = 1;
                    map[nextBridgeY][nextBridgeX + 1] = 2;

                    isValidated = setBridgeAndValidate(nextBridgeIndex, bridgeCount + 1, maxBridgeCount);
                    if (isValidated) {
                        return true;
                    }

                    map[nextBridgeY][nextBridgeX] = 0;
                    map[nextBridgeY][nextBridgeX + 1] = 0;
                }
            }
        }
        return false;
    }

    static int findMinBridgeCountWithValidation() {
        // if (findIncompleteCount() > 3) {
        //     return -1;
        // }
        for (int bridgeCount = 0; bridgeCount <= 3; bridgeCount++) {
            boolean isValidate = setBridgeAndValidate(0, 0, bridgeCount);
            if (isValidate) {
                return bridgeCount;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        io = FastIO.of(args);

        readInput();
        int answer = findMinBridgeCountWithValidation();
        io.print(answer);

        io.flush();
    }

    static class FastIO extends PrintWriter {
        private static final int bufferSize = 1 << 16;
        private InputStream in;

        private FastIO(InputStream in, boolean autoFlush) {
            super(new BufferedWriter(new OutputStreamWriter(System.out), bufferSize), autoFlush);
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
