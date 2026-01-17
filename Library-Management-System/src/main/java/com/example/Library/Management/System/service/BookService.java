package com.example.Library.Management.System.service;

import com.example.Library.Management.System.dto.BookMapper;
import com.example.Library.Management.System.dto.request.BookRequestDTO;
import com.example.Library.Management.System.dto.response.BookResponseDTO;
import com.example.Library.Management.System.exception.BookNotFoundException;
import com.example.Library.Management.System.model.Book;
import com.example.Library.Management.System.repository.BookRepository;
import com.example.Library.Management.System.utility.ResponseMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {

    private final BookRepository bookRepository;

    @Override
    public BookResponseDTO addBook(BookRequestDTO request) {
        Book book = BookMapper.toEntity(request);
        return BookMapper.toDTO(bookRepository.save(book));
    }

    @Override
    public List<BookResponseDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookMapper::toDTO)
                .toList();
    }

    @Override
    public BookResponseDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return BookMapper.toDTO(book);
    }

    @Override
    @Transactional
    public BookResponseDTO updateBook(Long id, BookRequestDTO request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPublishedDate(request.getPublishedDate());

        return BookMapper.toDTO(bookRepository.save(book));
    }

    @Override
    @Transactional
    public String deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        bookRepository.delete(book);
        return ResponseMessage.SUCCESS_MES;
    }

    @Override
    public List<BookResponseDTO> searchBooks(String query) {
        return bookRepository
                .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query)
                .stream()
                .map(BookMapper::toDTO)
                .toList();
    }
}


