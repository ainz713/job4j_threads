package usstor;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> users;

    public UserStorage() {
        this.users = new HashMap<>();
    }

    public synchronized boolean add(User user) {
        if (users.containsKey(user.getId())) {
            return false;
        }
        users.put(user.getId(), user);
        return true;
    }

    public synchronized boolean update(User user) {
        if (users.containsKey(user.getId())) {
            return false;
        }
        users.put(user.getId(), user);
        return true;
    }

    public synchronized boolean delete(User user) {
        if (users.containsKey(user.getId())) {
            return false;
        }
        users.remove(user.getId());
        return true;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User user1 = this.users.get(fromId);
        User user2 = this.users.get(toId);
        if (user1.getAmount() > amount) {
            user1.setAmount(user1.getAmount() - amount);
            user2.setAmount(user2.getAmount() + amount);
            return true;
        }
        return false;
    }
}
