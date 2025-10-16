package org.example.model.entity;

import jakarta.mail.internet.InternetAddress;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class User {
    private int id;
    private String name;
    private String email;
    private Set<Loan> currentLoans;
    private Set<Loan> loansHistory;

    public User(String name, String email) {
        validateName(name);
        validateEmail(email);
        this.name = name;
        this.email = email;
        this.currentLoans = new HashSet<>();
        this.loansHistory = new HashSet<>();
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

    public Set<Loan> getCurrentLoans() {
        return currentLoans;
    }

    public Set<Loan> getLoansHistory() {
        return loansHistory;
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

    public void addLoan(Loan loan) {
            currentLoans.add(loan);
            loansHistory.add(loan);
    }

    public void removeLoan(Loan loan) {
        loan.setReturnDate(LocalDate.now());
        currentLoans = currentLoans.stream()
                .filter(l -> !l.equals(loan))
                .collect(Collectors.toSet());
    }
}
