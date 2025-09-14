package org.example.model.repository;

import org.example.exception.UserNotFoundException;
import org.example.model.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository implements Repository<User> {
    Map<Integer, User> users = new HashMap<>();
    private int nextId = 1;

    public UserRepository() {
        User user1 = new User("Demo", "a@a.ru");
        user1.setId(nextId);
        users.put(nextId, user1);
        nextId++;
    }

    @Override
    public void add(String s) {

        if (s == null || s.isBlank()) {
            throw new IllegalArgumentException("Пустая строка с параметрами недопустима");
        }
        String[] str = s.split(",");
        if (str.length != 2) {
            throw new IllegalArgumentException("Вы ввели некорректные параметры пользователя");
        }
        String userName = str[0].trim();
        String userEmail = str[1].trim();

        if (isEmailTaken(userEmail)) {
            throw new IllegalArgumentException("Пользователь с данным email уже существует в базе");
        }
        User user = new User(userName, userEmail);
        user.setId(nextId);
        users.put(nextId, user);
        nextId++;
        System.out.println("Пользователь успешно добавлен в базу");
    }

    @Override
    public Map<Integer, User> getAll() {
        return users;
    }

    @Override
    public User getById(int id) {
        if (users.get(id) == null) {
            throw new UserNotFoundException("Пользователь с ID " + id + " не найден");
        }
        return users.get(id);
    }

    public List<User> getByName(String name) {
        List<User> result = users.values().stream().filter(user -> user.getName().toLowerCase().contains(name.toLowerCase())).toList();
        if (result.isEmpty()) {
            throw new UserNotFoundException("Пользователей с именем " + name + " не найдено");
        }
        return result;
    }

    public List<User> getByEmail(String email) {
        List<User> result = users.values().stream().filter(user -> user.getEmail().toLowerCase().contains(email.toLowerCase())).toList();
        if (result.isEmpty()) {
            throw new UserNotFoundException("Пользователей с почтой " + email + " не найдено");
        }
        return result;
    }

    public boolean isEmailTaken(String email) {
        return users.values().stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }
}
