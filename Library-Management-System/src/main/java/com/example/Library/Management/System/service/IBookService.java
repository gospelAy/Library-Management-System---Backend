package com.example.Library.Management.System.service;


import com.example.Library.Management.System.dto.request.BookRequestDTO;
import com.example.Library.Management.System.dto.response.BookResponseDTO;

import java.util.List;

public interface IBookService {

    BookResponseDTO addBook(BookRequestDTO request);

    List<BookResponseDTO> getAllBooks();

    BookResponseDTO getBookById(Long id);

    BookResponseDTO updateBook(Long id, BookRequestDTO request);

    String deleteBook(Long id);

    List<BookResponseDTO> searchBooks(String query);
}
