package com.Online_BookStore.Satya;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LibraryController {

    private static final Logger logger =
            LoggerFactory.getLogger(LibraryController.class);

    @Autowired
    private LibraryService libraryService;

    // ==================== BOOK ENDPOINTS ====================

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = libraryService.getAllBooks();
        logger.info("List of books returned {}", books);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = libraryService.getBookById(id);
        logger.info("Book retrieved {}", book);

        if (book.isPresent()) {
            return new ResponseEntity<>(book.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        libraryService.addBook(book);
        logger.info("Book added {}", book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Long id,
            @RequestBody Book updatedBook) {

        if (!libraryService.getBookById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        updatedBook.setId(id);
        libraryService.updateBook(updatedBook);
        logger.info("Book updated {}", updatedBook);

        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {

        if (!libraryService.getBookById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        libraryService.deleteBook(id);
        logger.info("Book deleted with id {}", id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // ==================== MEMBER ENDPOINTS ====================

    @GetMapping("/members")
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = libraryService.getAllMembers();
        logger.info("Members list returned {}", members);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        Optional<Member> member = libraryService.getMemberById(id);
        logger.info("Member retrieved {}", member);

        if (member.isPresent()) {
            return new ResponseEntity<>(member.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/members")
    public ResponseEntity<Member> addMember(@RequestBody Member member) {
        libraryService.addMember(member);
        logger.info("Member added {}", member);
        return new ResponseEntity<>(member, HttpStatus.CREATED);
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<Member> updateMember(
            @PathVariable Long id,
            @RequestBody Member updatedMember) {

        if (!libraryService.getMemberById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        updatedMember.setId(id);
        libraryService.updateMember(updatedMember);
        logger.info("Member updated {}", updatedMember);

        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {

        if (!libraryService.getMemberById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        libraryService.deleteMember(id);
        logger.info("Member deleted with id {}", id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // ==================== BORROW RECORD ENDPOINTS ====================

    @GetMapping("/borrow-records")
    public ResponseEntity<List<BorrowRecord>> getAllBorrowRecords() {
        List<BorrowRecord> records = libraryService.getAllBorrowRecords();
        logger.info("Borrow records returned {}", records);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    @PostMapping("/borrow")
    public ResponseEntity<BorrowRecord> borrowBook(
            @RequestBody BorrowRecord record) {

        // Validate Book
        if (record.getBook() == null ||
                !libraryService.getBookById(record.getBook().getId()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Validate Member
        if (record.getMember() == null ||
                !libraryService.getMemberById(record.getMember().getId()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(14));

        libraryService.borrowBook(record);
        logger.info("Book borrowed {}", record);

        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }

    @PutMapping("/return/{recordId}")
    public ResponseEntity<String> returnBook(@PathVariable long recordId) {

        Optional<BorrowRecord> record =
                libraryService.getBorrowRecordById(recordId);

        if (!record.isPresent()) {
            return new ResponseEntity<>("Borrow record not found",
                    HttpStatus.NOT_FOUND);
        }

        libraryService.returnBook(recordId, LocalDate.now());
        logger.info("Book returned for record {}", recordId);

        return new ResponseEntity<>("Book returned successfully",
                HttpStatus.OK);
    }
}
