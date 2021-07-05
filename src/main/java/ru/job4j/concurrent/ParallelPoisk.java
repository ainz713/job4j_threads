package ru.job4j.concurrent;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelPoisk<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T index;
    private final int startIndex;
    private final int endIndex;

    public ParallelPoisk(T[] array, T index, int startIndex, int endIndex) {
        this.array = array;
        this.index = index;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    protected Integer compute() {
        int rsl = -1;
        if (endIndex - startIndex < 10) {
            for (int i = startIndex; i <= endIndex; i++) {
                if (array[i] == index) {
                    rsl = i;
                    break;
                }
            }
            return rsl;
        }
        int mid = startIndex + ((endIndex - startIndex) / 2);
        ParallelPoisk<T> leftSide = new ParallelPoisk(array, index, startIndex, mid);
        ParallelPoisk<T> rightSide = new ParallelPoisk(array, index, mid + 1, endIndex);
        leftSide.fork();
        rightSide.fork();
        int left = leftSide.join();
        int right = rightSide.join();
        return left != -1 ? left : right;
    }

    public static <T> int findIndex(T[] array, T index) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelPoisk<>(array, index, 0, array.length - 1));
    }
}
