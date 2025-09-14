package org.example.model.entity;

import jakarta.mail.internet.InternetAddress;

public class User {
    int id;
    String name;
    String email;

    public User(String name, String email) {
        validateName(name);
        validateEmail(email);
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateName(name);
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        validateEmail(email);
        this.email = email;
    }

    @Override
    public String toString() {
        return "Пользователь {" +
                "id = " + id +
                ", имя = '" + name + '\'' +
                ", email = '" + email + '\'' +
                '}';
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя пользователя не может быть пустым");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email пользователя не может быть пустым");
        }
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
        } catch (Exception e) {
            throw new IllegalArgumentException("Формат email не соответствует требуемому");
        }
    }
}
