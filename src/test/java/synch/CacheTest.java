package synch;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void testAdd() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 0);
        Base base2 = new Base(2, 0);
        cache.add(base1);
        cache.add(base1);
        cache.add(base2);
        cache.add(base2);
        cache.add(base2);
        assertThat(cache.size(), is(2));
    }

    @Test
    public void testUpdate() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 0);
        cache.add(base1);
        base1.setName("New name");
        assertThat(true, is(cache.update(base1)));
        assertThat(cache.get(base1.getId()).getName(), is("New name"));
    }

    @Test
    public void testDelete() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 0);
        Base base2 = new Base(2, 0);

        cache.add(base1);
        cache.add(base2);

        cache.delete(base1);
        cache.delete(base1);

        assertThat(cache.size(), is(1));
    }

    @Test(expected = OptimisticException.class)
    public void testOptimisticException() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 0);
        cache.add(base1);
        assertThat(true, is(cache.update(base1)));
        assertThat(true, is(cache.update(base1)));
    }
}