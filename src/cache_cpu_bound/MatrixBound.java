package cache_cpu_bound;

public class MatrixBound {
    // Tạo mảng 2 chiều
    static final int SIZE = 10_000_000;
    static int[][] matrix = new int[SIZE][2];

    public static void main(String[] args) {
        long start = System.nanoTime();
        // 1. Truy cập theo hàng - cột
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < 2; j++) {
                matrix[i][j]++;
            }
        }
        long end = System.nanoTime();
        System.out.println("Row-access time: " + (end - start) / 1e6 + " ms");


        start = System.nanoTime();
        // 2. Truy cập theo cột - hàng
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < SIZE; j++) {
                matrix[j][i]++;
            }
        }
        end = System.nanoTime();
        System.out.println("Col-access time: " + (end - start) / 1e6 + " ms");

        System.out.println("\nNên sử dụng mảng 2 chiều theo thứ tự hàng - cột thay vì cột - hàng, vì các ô nhớ được xếp theo hàng - cột.\nVí dụ: matrix[0][0] nằm cạnh matrix[0][0] trong Cache CPU, arr[0][0] cách xa arr[1][0]");


    }
}
