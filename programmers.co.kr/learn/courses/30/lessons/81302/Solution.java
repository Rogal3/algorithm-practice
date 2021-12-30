import java.util.LinkedList;
import java.util.Queue;

/**
 * @see https://programmers.co.kr/learn/courses/30/lessons/81302
 * @author rogal
 */

class Solution {
    final int SIZE = 5;

    public int[] solution(String[][] places) {
        int[] answer = new int[SIZE];
        boolean[][][] visited = new boolean[SIZE][SIZE][SIZE];
        for (int place = 0; place < SIZE; place++) {
            int isSafe = 1;
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    if (places[place][r].charAt(c) == 'P') {
                        boolean foundCloseP = bfs(r, c, place, visited, places);
                        if (foundCloseP) {
                            isSafe = 0;
                            break;
                        }
                    }
                }
                if (isSafe == 0) {
                    break;
                }
            }
            answer[place] = isSafe;
        }
        return answer;
    }

    // return foundCloseP
    boolean bfs(int r, int c, int place, boolean[][][] visited, String[][] places) {
        final int[] dr = {-1, 0, 1, 0};
        final int[] dc = {0, 1, 0, -1};
        // element[0] is row, element[1] is column, element[2] is distance from the source
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {r, c, 0});
        visited[place][r][c] = true;
        while (!queue.isEmpty()) {
            int[] element = queue.poll();
            if (!(r == element[0] && c == element[1]) && places[place][element[0]].charAt(element[1]) == 'P') {
                return true;
            }
            if (element[2] < 2) {
                int nDist = element[2] + 1;
                for (int d = 0; d < dr.length; d++) {
                    int nr = element[0] + dr[d];
                    int nc = element[1] + dc[d];
                    if (inRange(nr, nc) && !visited[place][nr][nc] && places[place][nr].charAt(nc) != 'X') {
                        queue.add(new int[] {nr, nc, nDist});
                        visited[place][nr][nc] = true;
                    }
                }
            }
        }
        return false;
    }

    boolean inRange(int r, int c) {
        return 0 <= r && r < SIZE && 0 <= c && c < SIZE;
    }
}
