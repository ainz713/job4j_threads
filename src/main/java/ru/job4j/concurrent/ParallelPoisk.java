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
        if (array.length <= 10) {
           return Arrays.asList(array).indexOf(index);
        }
        int mid = startIndex + ((endIndex - startIndex) / 2);
        ParallelPoisk<T> leftSide = new ParallelPoisk(array, index, startIndex, mid);
        ParallelPoisk<T> rightSide = new ParallelPoisk(array, index, mid, endIndex);
        leftSide.fork();
        rightSide.fork();
        int left = leftSide.join();
        int right = rightSide.join();
        return left != -1 ? left : right;
    }

    public static <T> int findIndex(T[] array, T index) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelPoisk<T>(array, index, 0, array.length - 1));
    }
}
