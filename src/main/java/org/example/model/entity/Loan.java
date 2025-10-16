package org.example.model.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Loan {
    private int id;
    private int bookId;
    private int userId;
    private LocalDate loanDate;
    private LocalDate returnDate;

    public Loan(int bookId, int userId, LocalDate loanDate, LocalDate returnDate) {
        this.bookId = bookId;
        this.userId = userId;
        this.loanDate = loanDate;
        this.returnDate = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate date) {
        this.loanDate = date;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "{" +
                "ID книги =" + bookId +
                ", ID пользователя =" + userId +
                ", дата выдачи =" + loanDate +
                ", дата возврата =" + returnDate +
                '}';
    }

    public boolean isReturned() {
        return returnDate != null;
    }

    public void returnBook() {
        setReturnDate(LocalDate.now());
    }

    public boolean isOverdue() {
        return !isReturned() && loanDate.plusDays(30).isBefore(LocalDate.now());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return bookId == loan.bookId && userId == loan.userId && Objects.equals(loanDate, loan.loanDate) && Objects.equals(returnDate, loan.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, userId, loanDate, returnDate);
    }
}
