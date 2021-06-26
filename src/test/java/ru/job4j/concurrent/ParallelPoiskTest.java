package ru.job4j.concurrent;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ParallelPoiskTest {

    @Test
    public void whenFindAndNot() {
        Integer[] array1 = {5, 8, 10, 13, 55, 3, 4};
        Integer[] array2 = {2, 1, 16, 5, 8, 10, 13, 55, 3, 4, 467, 45, 77};
        int index1 = ParallelPoisk.findIndex(array1, 13);
        int index2 = ParallelPoisk.findIndex(array2, 45);
        int index3 = ParallelPoisk.findIndex(array2, 11);
        assertThat(index1, is(3));
        assertThat(index2, is(11));
        assertThat(index3, is(-1));
    }
}