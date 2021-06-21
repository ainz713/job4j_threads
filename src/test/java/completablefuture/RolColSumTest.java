package completablefuture;

import org.junit.Test;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

public class RolColSumTest {
    private final StringBuilder expected = new StringBuilder()
            .append("Сумма колонки - 6, строки - 6\n")
            .append("Сумма колонки - 7, строки - 15\n")
            .append("Сумма колонки - 9, строки - 1\n");
    private final  int[][] a1 = {
            {1,2,3},
            {4,5,6},
            {1,0,0}
    };

    @Test
    public void testAsyncSum() throws ExecutionException, InterruptedException {
        RolColSum.Sums[] n = RolColSum.asyncSum(a1);
        StringBuilder s = new StringBuilder();
        for (RolColSum.Sums a
                :n) {
            s.append(String.format("Сумма колонки - %s, строки - %s\n", a.getColSum(), a.getRowSum()));
        }
        assertEquals(expected.toString(), s.toString());
    }

    @Test
    public void whenSum() {
        RolColSum.Sums[] n = RolColSum.sum(a1);
        StringBuilder s = new StringBuilder();
        for (RolColSum.Sums a
                :n) {
            s.append(String.format("Сумма колонки - %s, строки - %s\n", a.getColSum(), a.getRowSum()));
        }
        assertEquals(expected.toString(), s.toString());
    }
}