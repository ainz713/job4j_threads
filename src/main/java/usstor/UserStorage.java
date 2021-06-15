package usstor;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> users = new HashMap<>();

    public synchronized boolean add(User user) {
        return users.putIfAbsent(user.getId(), user) != null;
    }

    public synchronized boolean update(User user) {
        return users.replace(user.getId(), user) != null;
    }

    public synchronized boolean delete(User user) {
        return users.remove(user.getId(), user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        if (!users.containsKey(fromId) || !users.containsKey(toId)) {
            return false;
        }
        User user1 = this.users.get(fromId);
        User user2 = this.users.get(toId);
        if (user1.getAmount() >= amount) {
            user1.setAmount(user1.getAmount() - amount);
            user2.setAmount(user2.getAmount() + amount);
            return true;
        }
        return false;
    }
}
