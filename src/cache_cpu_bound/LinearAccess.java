package cache_cpu_bound;

import it.unimi.dsi.fastutil.ints.IntArrayList;

import java.util.ArrayList;

public class LinearAccess{
    public static void main(String[] args) {
        final int N = 1_000_000;

        // 1. int[]
        int[] arr = new int[N];
        long start = System.nanoTime();
        for (int i = 0; i < N; i++) {
            arr[i] = i;
        }
        long end = System.nanoTime();
        System.out.println("int[] add: " + (end - start) / 1e6 + " ms");

        start = System.nanoTime();
        long sum = 0;
        for (int i = 0; i < N; i++) {
            sum += arr[i];
        }
        end = System.nanoTime();
        System.out.println("int[] read sum: " + sum + ", time: " + (end - start) / 1e6 + " ms");


        // 2. ArrayList<Integer>
        ArrayList<Integer> list = new ArrayList<>(N);
        start = System.nanoTime();
        for (int i = 0; i < N; i++) {
            list.add(i);
        }
        end = System.nanoTime();
        System.out.println("ArrayList<Integer> add: " + (end - start) / 1e6 + " ms");

        start = System.nanoTime();
        sum = 0;
        for (int i = 0; i < N; i++) {
            sum += list.get(i);
        }
        end = System.nanoTime();
        System.out.println("ArrayList<Integer> read sum: " + sum + ", time: " + (end - start) / 1e6 + " ms");


        // 3. FastUtil IntArrayList
        IntArrayList fastList = new IntArrayList(N);
        start = System.nanoTime();
        for (int i = 0; i < N; i++) {
            fastList.add(i);
        }
        end = System.nanoTime();
        System.out.println("IntArrayList add: " + (end - start) / 1e6 + " ms");

        start = System.nanoTime();
        sum = 0;
        for (int i = 0; i < N; i++) {
            sum += fastList.getInt(i);
        }
        end = System.nanoTime();
        System.out.println("IntArrayList read sum: " + sum + ", time: " + (end - start) / 1e6 + " ms");
    }
}

