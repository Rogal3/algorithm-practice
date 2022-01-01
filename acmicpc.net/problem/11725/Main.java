import java.io.*;
import java.util.*;

/**
 * @see https://www.acmicpc.net/problem/11725
 * @author rogal
 */

public class Main {
    static FastIO io;

    static int[] generateTree(List<Integer>[] links) {
        int[] tree = new int[links.length];
        tree[1] = -1;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int child : links[current]) {
                if (tree[child] == 0) {
                    tree[child] = current;
                    queue.offer(child);
                }
            }
        }
        return tree;
    }

    public static void main(String[] args) {
        io = FastIO.of(args);

        int n = io.nextInt();
        List<Integer>[] links = (List<Integer>[]) new List[n + 1];
        Arrays.setAll(links, i -> new ArrayList());
        for (int i = 0; i < n - 1; i++) {
            int a = io.nextInt();
            int b = io.nextInt();
            links[a].add(b);
            links[b].add(a);
        }
        int[] tree = generateTree(links);
        for (int i = 2; i <= n; i++) {
            io.println(tree[i]);
        }

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
