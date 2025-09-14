package org.example.model.entity;

import java.util.Objects;

public class Book {
    int id;
    String title;
    String author;
    int year;
    int totalCopies;
    int availableCopies;

    public Book(String title, String author, int year, int totalCopies, int availableCopies) {
        validateTitle(title);
        validateAuthor(author);
        validateYear(year);
        validateCopies(totalCopies, availableCopies);

        this.title = title;
        this.author = author;
        this.year = year;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        validateTitle(title);
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        validateAuthor(author);
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        validateYear(year);
        this.year = year;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        validateCopies(totalCopies, this.availableCopies);
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        validateCopies(this.totalCopies, availableCopies);
        this.availableCopies = availableCopies;
    }

    @Override
    public String toString() {
        return String.format(
                "[ID=%d] \"%s\", автор: %s, %d г., доступно: %d/%d",
                id, title, author, year, availableCopies, totalCopies
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return year == book.year &&
                title.equalsIgnoreCase(book.title) &&
                author.equalsIgnoreCase(book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title.toLowerCase(), author.toLowerCase(), year);
    }


    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Название книги не может быть пустым");
        }
    }

    private void validateAuthor(String author) {
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Автор не может быть пустым");
        }
    }

    private void validateYear(int year) {
        if (year < 1450 || year > java.time.Year.now().getValue() + 1) {
            throw new IllegalArgumentException("Некорректный год издания книги");
        }
    }

    private void validateCopies(int totalCopies, int availableCopies) {
        if (totalCopies < 0) {
            throw new IllegalArgumentException("Количество всех копий книги не может быть отрицательным");
        }
        if (availableCopies < 0) {
            throw new IllegalArgumentException("Количество доступных копий не может быть отрицательным");
        }
        if (availableCopies > totalCopies) {
            throw new IllegalArgumentException("Доступные копии (" + availableCopies +
                    ") не могут превышать общее количество (" + totalCopies + ")");
        }
    }

}
