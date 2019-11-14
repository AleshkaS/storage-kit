package com.example.storagekit.controller;

import com.example.storagekit.Utils;
import com.example.storagekit.dto.EntryDto;
import com.example.storagekit.model.Entry;
import com.example.storagekit.repository.EntryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/storage")
public class EntryController {

    @Autowired
    private EntryRepository entryRepository;

    @PostMapping
    public ResponseEntity addEntry(@Valid @RequestBody Entry entry) throws ResponseStatusException {
        Optional<Entry> repeatedEntry = entryRepository.findByKey(entry.getKey());
        if (repeatedEntry.isPresent()) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
                    .body(Utils.getMessage("This key '" + entry.getKey() + "' already exists"));
        } else {
            entryRepository.insert(entry);
            return ResponseEntity.ok(Utils.getMessage("This key '" + entry.getKey() + "' has been successfully added"));
        }
    }

    @GetMapping
    public ResponseEntity<List> getEntries() {
        return ResponseEntity
                .ok(entryRepository.findAll().stream().map(this::transformToDto).collect(Collectors.toList()));
    }

    @GetMapping("/{key}")
    public ResponseEntity<EntryDto> getEntryByKey(@PathVariable String key) throws ResponseStatusException {
        return ResponseEntity.ok(transformToDto(findValueByKey(key)));
    }

    @PutMapping
    public ResponseEntity changeEntryByKey(@Valid @RequestBody Entry entry)
            throws ResponseStatusException {
        entry.setId(findValueByKey(entry.getKey()).getId());
        entryRepository.save(entry);
        return ResponseEntity.ok(Utils.getMessage("This key '" + entry.getKey() + "' has been successfully updated"));
    }

    @DeleteMapping("/{key}")
    public ResponseEntity deleteEntryByKey(@PathVariable String key) throws ResponseStatusException {
        entryRepository.delete(findValueByKey(key));
        return ResponseEntity.ok(Utils.getMessage("This key '" + key + "' has been successfully deleted"));
    }

    private Entry findValueByKey(String key) throws ResponseStatusException {
        return entryRepository.findByKey(key).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Value not found for this key : " + key));
    }

    private EntryDto transformToDto(Entry entry) {
        EntryDto entryDto = new EntryDto();
        BeanUtils.copyProperties(entry, entryDto);
        return entryDto;
    }
}
