package dev.vdrenkov.slm.controller;

import dev.vdrenkov.slm.dto.AuthorDto;
import dev.vdrenkov.slm.entity.Author;
import dev.vdrenkov.slm.request.AuthorRequest;
import dev.vdrenkov.slm.service.AuthorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private static final Logger log = LoggerFactory.getLogger(AuthorController.class);

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<Void> addAuthor(@RequestBody @Valid AuthorRequest authorRequest) {
        Author author = authorService.addAuthor(authorRequest);

        URI location = UriComponentsBuilder.fromUriString("/authors/{id}").buildAndExpand(author.getId()).toUri();
        log.info("A new author added");
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        log.info("All authors requested from the database");
        return ResponseEntity.ok(authorService.getAllAuthorsDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable int id) {
        log.info("Author with an ID {} requested from the database.", id);
        return ResponseEntity.ok(authorService.getAuthorDtoById(id));
    }
}



