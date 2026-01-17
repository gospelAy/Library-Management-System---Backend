package com.example.Library.Management.System;

import com.example.Library.Management.System.dto.request.BookRequestDTO;
import com.example.Library.Management.System.dto.response.BookResponseDTO;
import com.example.Library.Management.System.exception.BookNotFoundException;
import com.example.Library.Management.System.model.Book;
import com.example.Library.Management.System.repository.BookRepository;
import com.example.Library.Management.System.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookService Unit Tests")
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book testBook;
    private BookRequestDTO testRequest;

    @BeforeEach
    void setUp() {
        testBook = new Book();
        testBook.setId(1L);
        testBook.setTitle("Clean Code");
        testBook.setAuthor("Robert C. Martin");
        testBook.setIsbn("978-0132350884");
        testBook.setPublishedDate(LocalDate.of(2008, 8, 1));
        testRequest = new BookRequestDTO();
        testRequest.setTitle("Clean Code");
        testRequest.setAuthor("Robert C. Martin");
        testRequest.setIsbn("978-0132350884");
        testRequest.setPublishedDate(LocalDate.of(2008, 8, 1));
    }



    @Test
    @DisplayName("Should add book successfully")
    void addBook_Success() {
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);
        BookResponseDTO result = bookService.addBook(testRequest);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Clean Code");
        assertThat(result.getAuthor()).isEqualTo("Robert C. Martin");
        assertThat(result.getIsbn()).isEqualTo("978-0132350884");
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Should save book with correct data")
    void addBook_VerifyData() {
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);
        bookService.addBook(testRequest);
        verify(bookRepository).save(argThat(book ->
                book.getTitle().equals("Clean Code") &&
                        book.getAuthor().equals("Robert C. Martin") &&
                        book.getIsbn().equals("978-0132350884")
        ));
    }


    @Test
    @DisplayName("Should return all books")
    void getAllBooks_Success() {
        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Effective Java");
        book2.setAuthor("Joshua Bloch");
        book2.setIsbn("978-0134685991");
        book2.setPublishedDate(LocalDate.of(2017, 12, 27));
        List<Book> books = Arrays.asList(testBook, book2);
        when(bookRepository.findAll()).thenReturn(books);
        List<BookResponseDTO> result = bookService.getAllBooks();
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTitle()).isEqualTo("Clean Code");
        assertThat(result.get(1).getTitle()).isEqualTo("Effective Java");
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no books exist")
    void getAllBooks_EmptyList() {
        when(bookRepository.findAll()).thenReturn(List.of());
        List<BookResponseDTO> result = bookService.getAllBooks();
        assertThat(result).isEmpty();
        verify(bookRepository, times(1)).findAll();
    }


    @Test
    @DisplayName("Should return book when found by ID")
    void getBookById_Success() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
        BookResponseDTO result = bookService.getBookById(1L);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Clean Code");
        verify(bookRepository, times(1)).findById(1L);
    }


    @Test
    @DisplayName("Should throw BookNotFoundException when book not found")
    void getBookById_NotFound() {
        Long nonExistentId = 999L;
        when(bookRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.getBookById(nonExistentId))
                .isInstanceOf(BookNotFoundException.class);
        verify(bookRepository, times(1)).findById(nonExistentId);
    }

    @Test
    @DisplayName("Should update book successfully")
    void updateBook_Success() {
        BookRequestDTO updateRequest = new BookRequestDTO();
        updateRequest.setTitle("Clean Code - Updated");
        updateRequest.setAuthor("Robert C. Martin");
        updateRequest.setIsbn("978-0132350884");
        updateRequest.setPublishedDate(LocalDate.of(2008, 8, 1));
        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitle("Clean Code - Updated");
        updatedBook.setAuthor("Robert C. Martin");
        updatedBook.setIsbn("978-0132350884");
        updatedBook.setPublishedDate(LocalDate.of(2008, 8, 1));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);
        BookResponseDTO result = bookService.updateBook(1L, updateRequest);
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Clean Code - Updated");
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Should throw BookNotFoundException when updating non-existent book")
    void updateBook_NotFound() {
        Long nonExistentId = 999L;
        when(bookRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.updateBook(nonExistentId, testRequest))
                .isInstanceOf(BookNotFoundException.class);
        verify(bookRepository, times(1)).findById(nonExistentId);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    @DisplayName("Should update all fields correctly")
    void updateBook_AllFields() {
        BookRequestDTO updateRequest = new BookRequestDTO();
        updateRequest.setTitle("New Title");
        updateRequest.setAuthor("New Author");
        updateRequest.setIsbn("111-1111111111");
        updateRequest.setPublishedDate(LocalDate.of(2020, 1, 1));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
        when(bookRepository.save(any(Book.class))).thenAnswer(i -> i.getArguments()[0]);
        bookService.updateBook(1L, updateRequest);
        verify(bookRepository).save(argThat(book ->
                book.getTitle().equals("New Title") &&
                        book.getAuthor().equals("New Author") &&
                        book.getIsbn().equals("111-1111111111") &&
                        book.getPublishedDate().equals(LocalDate.of(2020, 1, 1))
        ));
    }

    @Test
    @DisplayName("Should delete book successfully")
    void deleteBook_Success() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
        doNothing().when(bookRepository).delete(testBook);
        String result = bookService.deleteBook(1L);
        assertThat(result).isEqualTo("deleted successfully");
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).delete(testBook);
    }

    @Test
    @DisplayName("Should throw BookNotFoundException when deleting non-existent book")
    void deleteBook_NotFound() {
        Long nonExistentId = 999L;
        when(bookRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.deleteBook(nonExistentId))
                .isInstanceOf(BookNotFoundException.class);
        verify(bookRepository, times(1)).findById(nonExistentId);
        verify(bookRepository, never()).delete(any(Book.class));
    }


    @Test
    @DisplayName("Should search books by title")
    void searchBooks_ByTitle() {
        String query = "clean";
        when(bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query))
                .thenReturn(List.of(testBook));
        List<BookResponseDTO> result = bookService.searchBooks(query);
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).containsIgnoringCase("clean");
        verify(bookRepository, times(1))
                .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query);
    }

    @Test
    @DisplayName("Should search books by author")
    void searchBooks_ByAuthor() {
        String query = "martin";
        when(bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query))
                .thenReturn(List.of(testBook));
        List<BookResponseDTO> result = bookService.searchBooks(query);
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAuthor()).containsIgnoringCase("martin");
        verify(bookRepository, times(1))
                .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query);
    }

    @Test
    @DisplayName("Should return empty list when no books match search")
    void searchBooks_NoResults() {
        String query = "nonexistent";
        when(bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query))
                .thenReturn(List.of());
        List<BookResponseDTO> result = bookService.searchBooks(query);
        assertThat(result).isEmpty();
        verify(bookRepository, times(1))
                .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query);
    }

    @Test
    @DisplayName("Should search books case-insensitively")
    void searchBooks_CaseInsensitive() {
        String query = "CLEAN";
        when(bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(anyString(), anyString()))
                .thenReturn(List.of(testBook));
        List<BookResponseDTO> result = bookService.searchBooks(query);
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        verify(bookRepository, times(1))
                .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase("CLEAN", "CLEAN");
    }
}