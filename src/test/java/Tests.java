import org.example.exception.BookNotFoundException;
import org.example.exception.UserNotFoundException;
import org.example.model.entity.Book;
import org.example.model.entity.Loan;
import org.example.model.entity.User;
import org.example.repository.BookRepository;
import org.example.repository.LoanRepository;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Tests {

    private BookRepository bookRepository;
    private UserRepository userRepository;
    private LoanRepository loanRepository;
    @BeforeEach
    void setUp() {
        bookRepository = new BookRepository();
        userRepository = new UserRepository();
        loanRepository = new LoanRepository(bookRepository, userRepository);
    }

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
    void createLoanTest() {
        Loan loan = new Loan(2, 1, LocalDate.now(), null);
        assertEquals(2, loan.getBookId());
        assertEquals(1, loan.getUserId());
        assertNull(loan.getReturnDate());
        assertFalse(loan.isReturned());
        assertFalse(loan.isOverdue());
        loan.setLoanDate(loan.getLoanDate().minusDays(31));
        assertTrue(loan.isOverdue());
        loan.returnBook();
        assertTrue(loan.isReturned());
    }

    @Test
    void userRepositoryAddTest() {
        userRepository.add("Max, max@ma.ru");
        assertEquals(4, userRepository.getAll().size());
        assertTrue(userRepository.isEmailTaken("max@ma.ru"));

        String s = "";
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> userRepository.add(s));
        assertEquals("Пустая строка с параметрами недопустима", e.getMessage());
        assertEquals(4, userRepository.getAll().size());

        String s2 = null ;
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> userRepository.add(s2));
        assertEquals("Пустая строка с параметрами недопустима", e2.getMessage());
        assertEquals(4, userRepository.getAll().size());

        String s3 = "a" ;
        IllegalArgumentException e3 = assertThrows(IllegalArgumentException.class, () -> userRepository.add(s3));
        assertEquals("Вы ввели некорректные параметры пользователя", e3.getMessage());
        assertEquals(4, userRepository.getAll().size());

        String s4 = "a,a,a" ;
        IllegalArgumentException e4 = assertThrows(IllegalArgumentException.class, () -> userRepository.add(s4));
        assertEquals("Вы ввели некорректные параметры пользователя", e4.getMessage());
        assertEquals(4, userRepository.getAll().size());

        String s5 = "Max, max@ma.ru";
        IllegalArgumentException e5 = assertThrows(IllegalArgumentException.class, () -> userRepository.add(s5));
        assertEquals("Пользователь с данным email уже существует в базе", e5.getMessage());
        assertEquals(4, userRepository.getAll().size());
    }

    @Test
    void getByIdTest() {
        UserRepository userRepository = new UserRepository();
        userRepository.add("Max, max@ma.ru");
        assertEquals(4, userRepository.getAll().size());
        assertTrue(userRepository.isEmailTaken("max@ma.ru"));
        UserNotFoundException e = assertThrows(UserNotFoundException.class, () -> userRepository.getById(10));
        assertEquals("Пользователь с ID 10 не найден", e.getMessage());
        assertEquals("Пользователь {id = 1, имя = 'Demo', email = 'a@a.ru'}", userRepository.getById(1).toString());
    }

    @Test
    void getByNameTest() {
        UserRepository userRepository = new UserRepository();
        userRepository.add("Max, max@ma.ru");
        assertEquals(4, userRepository.getAll().size());
        assertTrue(userRepository.isEmailTaken("max@ma.ru"));
        UserNotFoundException e = assertThrows(UserNotFoundException.class, () -> userRepository.getByName("ы"));
        assertEquals("Пользователей с именем ы не найдено", e.getMessage());
        assertEquals("[Пользователь {id = 1, имя = 'Demo', email = 'a@a.ru'}]", userRepository.getByName("De").toString());
        assertEquals("[Пользователь {id = 1, имя = 'Demo', email = 'a@a.ru'}, Пользователь {id = 4, имя = 'Max', email = 'max@ma.ru'}]", userRepository.getByName("m").toString());
    }

    @Test
    void getByEmailTest() {
        UserRepository userRepository = new UserRepository();
        userRepository.add("Max, max@ma.ru");
        assertEquals(4, userRepository.getAll().size());
        assertTrue(userRepository.isEmailTaken("max@ma.ru"));
        UserNotFoundException e = assertThrows(UserNotFoundException.class, () -> userRepository.getByEmail("ы"));
        assertEquals("Пользователей с почтой ы не найдено", e.getMessage());
        assertEquals("[Пользователь {id = 1, имя = 'Demo', email = 'a@a.ru'}, Пользователь {id = 4, имя = 'Max', email = 'max@ma.ru'}]", userRepository.getByEmail("a").toString());
    }


    @Test
    void bookRepositoryAddTest() {
        BookRepository bookRepository = new BookRepository();
        assertEquals(4, bookRepository.getAll().size());
        bookRepository.add("Гарри Поттер и Тайная комната, Д. Роулинг, 1999, 8, 4");
        assertEquals(5, bookRepository.getAll().size());
        assertEquals("[ID=5] \"Гарри Поттер и Тайная комната\", автор:  Д. Роулинг, 1999 г., доступно: 4/8", bookRepository.getById(5).toString());

        String s = "";
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> bookRepository.add(s));
        assertEquals("Пустая строка с параметрами недопустима", e.getMessage());
        assertEquals(5, bookRepository.getAll().size());

        String s2 = null ;
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> bookRepository.add(s2));
        assertEquals("Пустая строка с параметрами недопустима", e2.getMessage());
        assertEquals(5, bookRepository.getAll().size());

        String s3 = "a" ;
        IllegalArgumentException e3 = assertThrows(IllegalArgumentException.class, () -> bookRepository.add(s3));
        assertEquals("Вы ввели некорректные параметры книги", e3.getMessage());
        assertEquals(5, bookRepository.getAll().size());

        String s4 = "a,a,a" ;
        IllegalArgumentException e4 = assertThrows(IllegalArgumentException.class, () -> bookRepository.add(s4));
        assertEquals("Вы ввели некорректные параметры книги", e4.getMessage());
        assertEquals(5, bookRepository.getAll().size());

        String s5 = "Гарри Поттер и Тайная комната, Д. Роулинг, 1999, 8, 4";
        IllegalArgumentException e5 = assertThrows(IllegalArgumentException.class, () -> bookRepository.add(s5));
        assertEquals("Данная книга уже существует в базе", e5.getMessage());
        assertEquals(5, bookRepository.getAll().size());
    }

    @Test
    void getBookByIDTest() {
        BookRepository bookRepository = new BookRepository();
        assertEquals("[ID=2] \"Гарри Поттер и Философский камень\", автор: Д. Роулинг, 1997 г., доступно: 4/8", bookRepository.getById(2).toString());
        BookNotFoundException e = assertThrows(BookNotFoundException.class, () -> bookRepository.getById(10));
        assertEquals("Книга с ID = 10 не найдена", e.getMessage());
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
        BookNotFoundException e = assertThrows(BookNotFoundException.class, () -> bookRepository.getByAuthor("p"));
        assertEquals("Книги автора 'p' не найдены", e.getMessage());
    }

    @Test
    void getBookByYearTest() {
        BookRepository bookRepository = new BookRepository();
        assertEquals("[[ID=2] \"Гарри Поттер и Философский камень\", автор: Д. Роулинг, 1997 г., доступно: 4/8]", bookRepository.getByYear(1997).toString());
        BookNotFoundException e = assertThrows(BookNotFoundException.class, () -> bookRepository.getByYear(1998));
        assertEquals("Книги за 1998 год не найдены", e.getMessage());
    }

    @Test
    void loanRepositoryAddLoan() {
        assertEquals(1, loanRepository.getAll().size());
        assertEquals(5, bookRepository.getById(4).getAvailableCopies());
        loanRepository.add("4,1");
        assertEquals(2, loanRepository.getAll().size());
        assertEquals("{ID книги =4, ID пользователя =1, дата выдачи =2025-10-08, дата возврата =null}", loanRepository.getById(2).toString());
        assertEquals(4, bookRepository.getById(4).getAvailableCopies());

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, ()-> loanRepository.add("1,2,3"));
        assertEquals("Введите 2 параметра - id книги и id пользователя", e.getMessage());

        BookNotFoundException e2 = assertThrows(BookNotFoundException.class, () -> loanRepository.add("18, 1"));
        assertEquals("Книга с ID = 18 не найдена", e2.getMessage());

        bookRepository.getById(3).setAvailableCopies(0);
        IllegalArgumentException e3 = assertThrows(IllegalArgumentException.class, () -> loanRepository.add("3, 1"));
        assertEquals("Доступных книг для выдачи не найдено", e3.getMessage());

        assertEquals(2, userRepository.getById(1).getCurrentLoans().size());
        loanRepository.add("2,1");
        assertEquals(3, userRepository.getById(1).getCurrentLoans().size());
        bookRepository.getById(3).setAvailableCopies(1);
        IllegalStateException e4 = assertThrows(IllegalStateException.class, () -> loanRepository.add("3, 1"));
        assertEquals("Нельзя брать больше 3 книг!", e4.getMessage());

        UserNotFoundException e5 = assertThrows(UserNotFoundException.class, () -> loanRepository.add("4, 28"));
        assertEquals("Пользователь с ID 28 не найден", e5.getMessage());
    }

    @Test
    void getAllLoansByUserID() {
        assertEquals(1, loanRepository.getByUserId(1).size());

    }

    @Test
    void getLoansByBookID() {
        assertEquals(1, loanRepository.getByBookId(1).size());
    }

    @Test
    void getOverdueBooks() {
        assertEquals(1, loanRepository.collectOverdue().size());
    }

}
