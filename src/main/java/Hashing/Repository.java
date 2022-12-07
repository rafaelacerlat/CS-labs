package Hashing;

import java.util.HashMap;
import java.util.Map;

public class Repository {
    private static Repository instance;
    private Map<Long, String> passwords = new HashMap<>();

    private Repository(){}

    public static Repository getInstance() {
        if (instance == null) {
            synchronized (Repository.class) {
                if (instance == null) {
                    instance = new Repository();
                }
            }
        }
        return instance;
    }

    public void save(Long userId, String password){
        passwords.put(userId,password);

    }

    public String getById(Long userId){
        String password = passwords.get(userId);
        if (password != null){
            return password;
        }else{
            throw new NullPointerException("There is no such user!");
        }
    }
}


