package com.example.Library.Management.System.dto;


import com.example.Library.Management.System.dto.request.BookRequestDTO;
import com.example.Library.Management.System.dto.response.BookResponseDTO;
import com.example.Library.Management.System.model.Book;

public class BookMapper {

    public static Book toEntity(BookRequestDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setPublishedDate(dto.getPublishedDate());
        return book;
    }

    public static BookResponseDTO toDTO(Book book) {
        BookResponseDTO dto = new BookResponseDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setPublishedDate(book.getPublishedDate());
        return dto;
    }
}
