//package net.engineeringdigest.journalApp.controller;
//
//import net.engineeringdigest.journalApp.entity.JournalEntry;
//import net.engineeringdigest.journalApp.service.JournalEntryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//// Rest Controllers are special type of components that are used to create the REST APIs
//@RestController
//@RequestMapping("/v2/journal")
//public class journalEntryControllerV2 {
//
//    @Autowired
//    private JournalEntryService journalEntryService;
//
//    @GetMapping("/getEntries")
//    public ResponseEntity<List<JournalEntry>> getAll() {
//
//        List<JournalEntry> entries = journalEntryService.getAllEntries();
//
//        if (entries != null && !entries.isEmpty()) {
//            return new ResponseEntity<>(entries, HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @PostMapping("/sendEntry")
//    public ResponseEntity<JournalEntry> createEntry(
//            @RequestBody JournalEntry myEntry) {
//
//        myEntry.setDate(LocalDateTime.now());
//        journalEntryService.saveEntry(myEntry, user);
//
//        return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
//    }
//
//    @GetMapping("/getEntryById/{id}")
//    public ResponseEntity<JournalEntry> getById(
//            @PathVariable String id) {
//
//        JournalEntry entry =
//                journalEntryService.findEntryByID(id).orElse(null);
//
//        if (entry != null) {
//            return new ResponseEntity<>(entry, HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @DeleteMapping("/deleteEntryById/{id}")
//    public ResponseEntity<?> deleteEntry(
//            @PathVariable String id) {
//
//        JournalEntry entry =
//                journalEntryService.findEntryByID(id).orElse(null);
//
//        if (entry != null) {
//            journalEntryService.deleteEntryById(id, userName);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<JournalEntry> updateJournalById(
//            @PathVariable String id,
//            @RequestBody JournalEntry updateEntry) {
//
//        JournalEntry old =
//                journalEntryService.findEntryByID(id).orElse(null);
//
//        if (old != null) {
//
//            old.setTitle(updateEntry.getTitle());
//            old.setContent(updateEntry.getContent());
//
//            journalEntryService.saveEntry(old, user);
//
//            return new ResponseEntity<>(old, HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//}