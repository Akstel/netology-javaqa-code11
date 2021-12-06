package ru.netology.manager;

import org.junit.jupiter.api.Test;
import ru.netology.domain.Book;
import ru.netology.domain.Product;
import ru.netology.domain.Smartphone;
import ru.netology.repository.ProductRepository;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ManagerTest {
    private ProductRepository repository = new ProductRepository();
    private ProductManager manager = new ProductManager(repository);


    private Book book1 = new Book(1, "book1", 300, "Достоевский");
    private Book book2 = new Book(2, "book2", 300, "Пушкин");
    private Book book3 = new Book(3, "book3", 300, "Пушкин");
    private Book book4 = new Book(4, "book4", 300, "Есенин");
    private Book book5 = new Book(5, "book5", 300, "Note");

    private Smartphone smartphone1 = new Smartphone(1, "Z", 10000, "Samsung");
    private Smartphone smartphone2 = new Smartphone(2, "S", 10000, "Samsung");
    private Smartphone smartphone3 = new Smartphone(3, "A", 10000, "Samsung");
    private Smartphone smartphone4 = new Smartphone(4, "M", 10000, "Samsung");
    private Smartphone smartphone5 = new Smartphone(5, "Note", 10000, "Samsung");


    @Test
    public void shouldSaveOneItem() {
        manager.add(book1);
        manager.add(smartphone1);
        manager.add(smartphone1);
        manager.add(smartphone1);

        Product[] expected = new Product[]{book1, smartphone1, smartphone1, smartphone1};
        Product[] actual = repository.findAll();
        assertArrayEquals(expected, actual);
    }


    @Test
    public void shouldMatchPositive() {
        Book book = new Book(1, "book", 300, "Достоевский");
        Smartphone smartphone = new Smartphone(2, "S", 10000, "Samsung");
        boolean expected = true;
        boolean actual = manager.matches(book, "Достоевский");
        assertEquals(expected, actual);
    }

    @Test
    public void shouldMatchNegative() {
        Book book = new Book(1, "book", 300, "Достоевский");
        Smartphone smartphone = new Smartphone(2, "S", 10000, "Samsung");
        boolean expected = false;
        boolean actual = manager.matches(book, "Иванов");
        assertEquals(expected, actual);
    }

    @Test
    public void shouldMatchCoverForCoverage() {

        Product book = new Product();

        boolean expected = false;
        boolean actual = manager.matches(book, "Иванов");
        assertEquals(expected, actual);
    }

    @Test
    public void shouldSearchByLength5Matches5_Smartphones() {
        manager.add(smartphone1);
        manager.add(smartphone2);
        manager.add(smartphone3);
        manager.add(smartphone4);
        manager.add(smartphone5);

        Product[] expected = new Product[]{smartphone1, smartphone2, smartphone3, smartphone4, smartphone5};
        Product[] actual = manager.searchBy("Samsung");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByLength5Matches1_Smartphone() {
        manager.add(smartphone1);
        manager.add(smartphone2);
        manager.add(smartphone3);
        manager.add(smartphone4);
        manager.add(smartphone5);

        Product[] expected = new Product[]{smartphone1};
        Product[] actual = manager.searchBy("Z");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByLength5Matches0_Smartphone() {
        manager.add(smartphone1);
        manager.add(smartphone2);
        manager.add(smartphone3);
        manager.add(smartphone4);
        manager.add(smartphone5);

        Product[] expected = new Product[]{};
        Product[] actual = manager.searchBy("45");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByLength5Matches5_Books() {
        manager.add(book1);
        manager.add(book2);
        manager.add(book3);
        manager.add(book4);
        manager.add(book5);

        Product[] expected = new Product[]{book1, book2, book3, book4, book5};
        Product[] actual = manager.searchBy("book");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByLength5Matches2_BookEqualBook() {
        manager.add(book1);
        manager.add(book1);
        manager.add(book3);
        manager.add(book4);
        manager.add(book5);

        Product[] expected = new Product[]{book1, book1};
        Product[] actual = manager.searchBy("Достоевский");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByLength5Matches2_BookBook() {
        manager.add(book1);
        manager.add(book2);
        manager.add(book3);
        manager.add(book4);
        manager.add(book5);

        Product[] expected = new Product[]{book2, book3};
        Product[] actual = manager.searchBy("Пушкин");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByLength5Matches2_BookSmartphone() {
        manager.add(smartphone5);
        manager.add(book2);
        manager.add(book3);
        manager.add(book4);
        manager.add(book5);

        Product[] expected = new Product[]{smartphone5, book5};
        Product[] actual = manager.searchBy("Note");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByLength5Matches1_Book() {
        manager.add(book1);
        manager.add(book2);
        manager.add(book3);
        manager.add(book4);
        manager.add(book5);

        Product[] expected = new Product[]{book1};
        Product[] actual = manager.searchBy("Достоевский");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByLength5Matches0_Book() {
        manager.add(book1);
        manager.add(book2);
        manager.add(book3);
        manager.add(book4);
        manager.add(book5);

        Product[] expected = new Product[]{};
        Product[] actual = manager.searchBy("Nte");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByLength1Matches1_Smartphone() {
        manager.add(smartphone1);

        Product[] expected = new Product[]{smartphone1};
        Product[] actual = manager.searchBy("S");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByLength1Matches0_Smartphone() {
        manager.add(smartphone1);

        Product[] expected = new Product[]{};
        Product[] actual = manager.searchBy("Mn");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByLength1Matches1_Book() {
        manager.add(book1);

        Product[] expected = new Product[]{book1};
        Product[] actual = manager.searchBy("Достоевский");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByLength1Matches0_Book() {
        manager.add(book1);

        Product[] expected = new Product[]{};
        Product[] actual = manager.searchBy("fgdfv");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByLength0Matches0() {

        Product[] expected = new Product[]{};
        Product[] actual = manager.searchBy("Достоевский");
        assertArrayEquals(expected, actual);
    }
}