import org.example.exception.BookNotFoundException;
import org.example.exception.UserNotFoundException;
import org.example.model.entity.Book;
import org.example.model.entity.User;
import org.example.model.repository.BookRepository;
import org.example.model.repository.UserRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Tests {

    @Test
    void createBookTest() {
        Book book = new Book("Мы", "Замятин", 1924, 5, 4);
        assertEquals("Мы", book.getTitle());
        assertEquals("Замятин", book.getAuthor());
        assertEquals(1924, book.getYear());
        assertEquals(5, book.getTotalCopies());
        assertEquals(4, book.getAvailableCopies());

        IllegalArgumentException e1 = assertThrows(IllegalArgumentException.class, () -> book.setTitle(""));
        assertEquals("Название книги не может быть пустым", e1.getMessage());
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> book.setTitle(" "));
        assertEquals("Название книги не может быть пустым", e2.getMessage());
        assertThrows(IllegalArgumentException.class, () -> book.setAuthor(" "));
        assertThrows(IllegalArgumentException.class, () -> book.setAuthor(null));
        assertThrows(IllegalArgumentException.class, () -> book.setYear(0));
        assertThrows(IllegalArgumentException.class, () -> book.setYear(-1));
        assertThrows(IllegalArgumentException.class, () -> book.setYear(2028));
        assertThrows(IllegalArgumentException.class, () -> book.setTotalCopies(-1));
        IllegalArgumentException e3 = assertThrows(IllegalArgumentException.class, () -> book.setAvailableCopies(-1));
        assertEquals("Количество доступных копий не может быть отрицательным", e3.getMessage());
        assertThrows(IllegalArgumentException.class, () -> book.setAvailableCopies(6));
    }

    @Test
    void createUserTest() {
        User user1 = new User("Max", "max@ya.ru");
        assertEquals("Max", user1.getName());
        assertEquals("max@ya.ru", user1.getEmail());

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> user1.setName(""));
        assertEquals("Имя пользователя не может быть пустым", e.getMessage());

        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> user1.setName(null));
        assertEquals("Имя пользователя не может быть пустым", e2.getMessage());

        IllegalArgumentException e3 = assertThrows(IllegalArgumentException.class, () -> user1.setEmail(""));
        assertEquals("Email пользователя не может быть пустым", e3.getMessage());

        IllegalArgumentException e4 = assertThrows(IllegalArgumentException.class, () -> user1.setEmail(null));
        assertEquals("Email пользователя не может быть пустым", e4.getMessage());

        IllegalArgumentException e5 = assertThrows(IllegalArgumentException.class, () -> user1.setEmail("a.ru"));
        assertEquals("Формат email не соответствует требуемому", e5.getMessage());
    }

    @Test
    void userRepositoryAddTest() {
        UserRepository userRepository = new UserRepository();
        userRepository.add("Max, max@ma.ru");
        assertEquals(2, userRepository.getAll().size());
        assertTrue(userRepository.isEmailTaken("max@ma.ru"));

        String s = "";
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> userRepository.add(s));
        assertEquals("Пустая строка с параметрами недопустима", e.getMessage());
        assertEquals(2, userRepository.getAll().size());

        String s2 = null ;
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> userRepository.add(s2));
        assertEquals("Пустая строка с параметрами недопустима", e2.getMessage());
        assertEquals(2, userRepository.getAll().size());

        String s3 = "a" ;
        IllegalArgumentException e3 = assertThrows(IllegalArgumentException.class, () -> userRepository.add(s3));
        assertEquals("Вы ввели некорректные параметры пользователя", e3.getMessage());
        assertEquals(2, userRepository.getAll().size());

        String s4 = "a,a,a" ;
        IllegalArgumentException e4 = assertThrows(IllegalArgumentException.class, () -> userRepository.add(s4));
        assertEquals("Вы ввели некорректные параметры пользователя", e4.getMessage());
        assertEquals(2, userRepository.getAll().size());

        String s5 = "Max, max@ma.ru";
        IllegalArgumentException e5 = assertThrows(IllegalArgumentException.class, () -> userRepository.add(s5));
        assertEquals("Пользователь с данным email уже существует в базе", e5.getMessage());
        assertEquals(2, userRepository.getAll().size());
    }

    @Test
    void getByIdTest() {
        UserRepository userRepository = new UserRepository();
        userRepository.add("Max, max@ma.ru");
        assertEquals(2, userRepository.getAll().size());
        assertTrue(userRepository.isEmailTaken("max@ma.ru"));
        UserNotFoundException e = assertThrows(UserNotFoundException.class, () -> userRepository.getById(3));
        assertEquals("Пользователь с ID 3 не найден", e.getMessage());
        assertEquals("Пользователь {id = 1, имя = 'Demo', email = 'a@a.ru'}", userRepository.getById(1).toString());
    }

    @Test
    void getByNameTest() {
        UserRepository userRepository = new UserRepository();
        userRepository.add("Max, max@ma.ru");
        assertEquals(2, userRepository.getAll().size());
        assertTrue(userRepository.isEmailTaken("max@ma.ru"));
        UserNotFoundException e = assertThrows(UserNotFoundException.class, () -> userRepository.getByName("ы"));
        assertEquals("Пользователей с именем ы не найдено", e.getMessage());
        assertEquals("[Пользователь {id = 1, имя = 'Demo', email = 'a@a.ru'}]", userRepository.getByName("D").toString());
        assertEquals("[Пользователь {id = 1, имя = 'Demo', email = 'a@a.ru'}, Пользователь {id = 2, имя = 'Max', email = 'max@ma.ru'}]", userRepository.getByName("m").toString());
    }

    @Test
    void getByEmailTest() {
        UserRepository userRepository = new UserRepository();
        userRepository.add("Max, max@ma.ru");
        assertEquals(2, userRepository.getAll().size());
        assertTrue(userRepository.isEmailTaken("max@ma.ru"));
        UserNotFoundException e = assertThrows(UserNotFoundException.class, () -> userRepository.getByEmail("ы"));
        assertEquals("Пользователей с почтой ы не найдено", e.getMessage());
        assertEquals("[Пользователь {id = 1, имя = 'Demo', email = 'a@a.ru'}, Пользователь {id = 2, имя = 'Max', email = 'max@ma.ru'}]", userRepository.getByEmail("a").toString());
    }


    @Test
    void bookRepositoryAddTest() {
        BookRepository bookRepository = new BookRepository();
        assertEquals(2, bookRepository.getAll().size());
        bookRepository.add("Гарри Поттер и Тайная комната, Д. Роулинг, 1999, 8, 4");
        assertEquals(3, bookRepository.getAll().size());
        assertEquals("[ID=3] \"Гарри Поттер и Тайная комната\", автор:  Д. Роулинг, 1999 г., доступно: 4/8", bookRepository.getById(3).toString());

        String s = "";
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> bookRepository.add(s));
        assertEquals("Пустая строка с параметрами недопустима", e.getMessage());
        assertEquals(3, bookRepository.getAll().size());

        String s2 = null ;
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> bookRepository.add(s2));
        assertEquals("Пустая строка с параметрами недопустима", e2.getMessage());
        assertEquals(3, bookRepository.getAll().size());

        String s3 = "a" ;
        IllegalArgumentException e3 = assertThrows(IllegalArgumentException.class, () -> bookRepository.add(s3));
        assertEquals("Вы ввели некорректные параметры книги", e3.getMessage());
        assertEquals(3, bookRepository.getAll().size());

        String s4 = "a,a,a" ;
        IllegalArgumentException e4 = assertThrows(IllegalArgumentException.class, () -> bookRepository.add(s4));
        assertEquals("Вы ввели некорректные параметры книги", e4.getMessage());
        assertEquals(3, bookRepository.getAll().size());

        String s5 = "Гарри Поттер и Тайная комната, Д. Роулинг, 1999, 8, 4";
        IllegalArgumentException e5 = assertThrows(IllegalArgumentException.class, () -> bookRepository.add(s5));
        assertEquals("Данная книга уже существует в базе", e5.getMessage());
        assertEquals(3, bookRepository.getAll().size());
    }

    @Test
    void getBookByIDTest() {
        BookRepository bookRepository = new BookRepository();
        assertEquals("[ID=2] \"Гарри Поттер и Философский камень\", автор: Д. Роулинг, 1997 г., доступно: 4/8", bookRepository.getById(2).toString());
        BookNotFoundException e = assertThrows(BookNotFoundException.class, () -> bookRepository.getById(3));
        assertEquals("Книга с ID = 3 не найдена", e.getMessage());
    }

    @Test
    void getBookByTitleTest() {
        BookRepository bookRepository = new BookRepository();
        assertEquals("[[ID=2] \"Гарри Поттер и Философский камень\", автор: Д. Роулинг, 1997 г., доступно: 4/8]", bookRepository.getByTitle("Гарри").toString());
        BookNotFoundException e = assertThrows(BookNotFoundException.class, () -> bookRepository.getByTitle("Магаз"));
        assertEquals("Книги с названием 'Магаз' не найдено", e.getMessage());
    }

    @Test
    void getBookByAuthorTest() {
        BookRepository bookRepository = new BookRepository();
        assertEquals("[[ID=2] \"Гарри Поттер и Философский камень\", автор: Д. Роулинг, 1997 г., доступно: 4/8]", bookRepository.getByAuthor("Роулинг").toString());
        BookNotFoundException e = assertThrows(BookNotFoundException.class, () -> bookRepository.getByAuthor("Я"));
        assertEquals("Книги автора 'Я' не найдены", e.getMessage());
    }

    @Test
    void getBookByYearTest() {
        BookRepository bookRepository = new BookRepository();
        assertEquals("[[ID=2] \"Гарри Поттер и Философский камень\", автор: Д. Роулинг, 1997 г., доступно: 4/8]", bookRepository.getByYear(1997).toString());
        BookNotFoundException e = assertThrows(BookNotFoundException.class, () -> bookRepository.getByYear(1998));
        assertEquals("Книги за 1998 год не найдены", e.getMessage());
    }

}
