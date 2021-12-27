import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Stack;

/**
 * @see https://www.acmicpc.net/problem/3954
 * @author rogal
 */

public class Main {
    static InputReader reader;
    static PrintWriter writer = new PrintWriter(System.out);

    static int arraySize;
    static int programSize;
    static int inputSize;

    static String program;
    static String input;

    static Array array;
    static int[] link;
    static int inputIndex;
    static int programPointer;
    static int programCount;
    static Stack<Integer> counterStack;

    static class Array {
        private byte[] array;
        private int arraySize;
        private int arrayPointer;

        Array() {
            array = new byte[(int) 1e5];
        }

        void reset(int arraySize) {
            this.arraySize = arraySize;
            Arrays.fill(array, 0, arraySize, (byte) 0);
            arrayPointer = 0;
        }

        void addValue() {
            array[arrayPointer]++;
        }

        void subtractValue() {
            array[arrayPointer]--;
        }

        byte getValue() {
            return array[arrayPointer];
        }

        void setValue(byte value) {
            array[arrayPointer] = value;
        }

        void left() {
            arrayPointer -= 1;
            if (arrayPointer < 0) {
                arrayPointer = arraySize - 1;
            }
        }

        void right() {
            arrayPointer += 1;
            if (arrayPointer >= arraySize) {
                arrayPointer = 0;
            }
        }
    }

    static void init() {
        link = new int[4096];
        array = new Array();
        counterStack = new Stack<>();
    }

    static void readInput() {
        arraySize = reader.nextInt();
        programSize = reader.nextInt();
        inputSize = reader.nextInt();
        program = reader.next();
        input = reader.next();
    }

    static void compile() {
        Stack<Integer> brackets = new Stack<>();
        for (int i = 0; i < programSize; i++) {
            if (program.charAt(i) == '[') {
                brackets.add(i);
            } else if (program.charAt(i) == ']') {
                int pair = brackets.pop();
                link[i] = pair;
                link[pair] = i;
            }
        }
    }

    static void preRun() {
        array.reset(arraySize);
        inputIndex = 0;
        programPointer = 0;
        programCount = 0;
        counterStack.clear();
    }

    static void runCommand() {
        byte command = (byte) program.charAt(programPointer);
        if (command == '-') {
            array.subtractValue();
        } else if (command == '+') {
            array.addValue();
        } else if (command == '<') {
            array.left();
        } else if (command == '>') {
            array.right();
        }else if (command == '[') {
            if (array.getValue() == 0) {
                programPointer = link[programPointer];
            } else {
                counterStack.push(programCount);
                programCount = -1;
            }
        } else if (command == ']') {
            if (array.getValue() != 0) {
                programPointer = link[programPointer];
            } else {
                programCount += counterStack.pop();
            }
        } else if (command == ',') {
            byte read = (byte) 255;
            if (inputIndex < inputSize) {
                read = (byte) input.charAt(inputIndex++);
            }
            array.setValue(read);
        }
    }

    static int findLoopTail(int programPointer) {
        while (program.charAt(programPointer) != ']') {
            if (program.charAt(programPointer) == '[') {
                programPointer = link[programPointer];
            }
            programPointer++;
        }
        return programPointer;
    }

    static String run() {
        while (programPointer < programSize && programCount <= 5e7) {
            runCommand();
            programCount++;
            programPointer++;
        }
        if (programPointer < programSize) {
            int loopTail = findLoopTail(programPointer);
            int loopHead = link[loopTail];
            return String.format("Loops %d %d", loopHead, loopTail);
        }
        return "Terminates";
    }

    public static void main(String[] args) {
        reader = InputReader.of(args);
        init();
        int testCaseSize = reader.nextInt();
        for (int testCase = 1; testCase <= testCaseSize; testCase++) {
            readInput();
            compile();
            preRun();
            String result = run();
            writer.println(result);
        }
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
