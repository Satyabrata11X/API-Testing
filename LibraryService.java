package com.Online_BookStore.Satya;


import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LibraryService {

    private List<Book> books = new ArrayList<>();
    private List<Member> members = new ArrayList<>();
    private List<BorrowRecord> borrowRecords = new ArrayList<>();

    //    Starting Book Method
    public List<Book> getAllBooks() {
        return books;
    }

    public Optional<Book> getBookById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void updateBook(Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (Objects.equals(books.get(i).getId(), updatedBook.getId())) {
                books.set(i, updatedBook);
                return;
            }
        }
    }

    public void deleteBook(Long id) {
        books.removeIf(book -> book.getId().equals(id));
    }

//    Starting Member method

    public List<Member> getAllMembers() {
        return members;
    }

    public Optional<Member> getMemberById(Long id) {
        return members.stream()
                .filter(member -> member.getId().equals(id))
                .findFirst();
    }

    public void addMember(Member member) {
        members.add(member);
    }


    public void updateMember(Member updatedMember) {
        for (int i = 0; i < members.size(); i++) {
            if (Objects.equals(members.get(i).getId(), updatedMember.getId())) {
                members.set(i, updatedMember);
                return;
            }
        }
    }

    public void deleteMember(Long id) {
        members.removeIf(member -> member.getId().equals(id));
    }


// ==================== BORROW RECORD METHODS ====================

    public List<BorrowRecord> getAllBorrowRecords() {
        return borrowRecords;
    }

    public Optional<BorrowRecord> getBorrowRecordById(Long recordId) {
        return borrowRecords.stream()
                .filter(record -> Objects.equals(record.getId(), recordId))
                .findFirst();
    }

    public void borrowBook(BorrowRecord record) {

        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(14));

        Book book = record.getBook();
        book.setAvailableCopies(book.getAvailableCopies() - 1);

        borrowRecords.add(record);
    }

    public void returnBook(Long recordId, LocalDate returnDate) {

        for (BorrowRecord record : borrowRecords) {
            if (Objects.equals(record.getId(), recordId)) {

                record.setReturnDate(returnDate);

                Book book = record.getBook();
                book.setAvailableCopies(book.getAvailableCopies() + 1);

                return;
            }
        }
    }
}