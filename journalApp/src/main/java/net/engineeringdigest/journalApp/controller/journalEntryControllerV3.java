//package net.engineeringdigest.journalApp.controller;
//
//import net.engineeringdigest.journalApp.entity.JournalEntry;
//import net.engineeringdigest.journalApp.entity.User;
//import net.engineeringdigest.journalApp.service.JournalEntryService;
//import net.engineeringdigest.journalApp.service.UserService;
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
//@RequestMapping("/v3/journal")
//public class journalEntryControllerV3 {
//
//    @Autowired
//    private JournalEntryService journalEntryService;
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/getEntries/{userName}")
//    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUsers(@PathVariable String userName) {
//
//        User user = userService.findByUserName(userName);
//
//        if(user == null){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        List<JournalEntry> entries = user.getJournalEntries();
//
//
//        if (entries != null && !entries.isEmpty()) {
//            return new ResponseEntity<>(entries, HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//
//    @PostMapping("/sendEntry/{userName}")
//    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry,@PathVariable String userName) {
//
//
//        /* we will have to save the user details of the journal entry as well
//        to which the journal entry belongs */
//
//        journalEntryService.saveEntry(myEntry, userName);
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
//    @DeleteMapping("/deleteEntryById/{userName}/{id}")
//    public ResponseEntity<?> deleteEntry(
//            @PathVariable String id, @PathVariable String userName) {
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
//    @PutMapping("/update/{userName}/{id}")
//    public ResponseEntity<JournalEntry> updateJournalById(
//            @PathVariable String id,
//            @PathVariable String userName,
//            @RequestBody JournalEntry updateEntry) {
//
//        JournalEntry old =journalEntryService.findEntryByID(id).orElse(null);
//
//        if (old != null) {
//
//            old.setTitle(updateEntry.getTitle());
//            old.setContent(updateEntry.getContent());
//
//            journalEntryService.saveEntry(old);
//
//            return new ResponseEntity<>(old, HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//}