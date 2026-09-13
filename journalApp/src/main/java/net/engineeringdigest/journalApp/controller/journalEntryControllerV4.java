package net.engineeringdigest.journalApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// Rest Controllers are special type of components that are used to create the REST APIs
@RestController
@RequestMapping("/v4/journal")
@Tag(
        name = "Journal APIs",
        description = "Create, read, update and delete journal entries"
)
@SecurityRequirement(name = "bearerAuth")
public class journalEntryControllerV4 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Get all journal entries",
            description = "Returns all journal entries belonging to the authenticated user"
    )
    @GetMapping("/getEntries")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUsers() {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.findByUserName(userName);

        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<JournalEntry> entries = user.getJournalEntries();


        if (entries != null && !entries.isEmpty()) {
            return new ResponseEntity<>(entries, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @Operation(
            summary = "Create journal entry",
            description = "Creates a new journal entry for the authenticated user"
    )
    @PostMapping("/sendEntry")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        /* we will have to save the user details of the journal entry as well
        to which the journal entry belongs */

        journalEntryService.saveEntry(myEntry, userName);

        return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
    }

//    @GetMapping("/getEntryById/{id}")
//    public ResponseEntity<JournalEntry> getById(
//            @PathVariable String id) {
//
//        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
//        String userName = authentication.getName();
//        User user = userService.findByUserName(userName);
//        List<JournalEntry> collect = user.getJournalEntries().stream().filter(
//                x->x.getId().equals(id)).collect(Collectors.toList()) ;
//
//        if(!collect.isEmpty()){
//            JournalEntry entry = journalEntryService.findEntryByID(id).orElse(null);
//
//            if (entry != null) {
//                return new ResponseEntity<>(entry, HttpStatus.OK);
//            }
//        }
//
//
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @Operation(
            summary = "Get journal entry by ID",
            description = "Returns a journal entry belonging to the authenticated user"
    )
    @GetMapping("/getEntryById/{id}")
    public ResponseEntity<JournalEntry> getById(@PathVariable String id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.findByUserName(userName);

        boolean exists = user.getJournalEntries()
                .stream()
                .anyMatch(x -> x.getId().equals(id));

        if (exists) {

            JournalEntry entry =
                    journalEntryService.findEntryByID(id).orElse(null);

            if (entry != null) {
                return new ResponseEntity<>(entry, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "Delete journal entry",
            description = "Deletes a journal entry belonging to the authenticated user"
    )
    @DeleteMapping("/deleteEntryById/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable String id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.findByUserName(userName);

        boolean exists = user.getJournalEntries()
                .stream()
                .anyMatch(x -> x.getId().equals(id));

        if (exists) {

            journalEntryService.deleteEntryById(id, userName);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "Update journal entry",
            description = "Updates an existing journal entry"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<JournalEntry> updateJournalById(
            @PathVariable String id,
            @RequestBody JournalEntry updateEntry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.findByUserName(userName);

        boolean exists = user.getJournalEntries()
                .stream()
                .anyMatch(x -> x.getId().equals(id));

        if (exists) {

            JournalEntry old =
                    journalEntryService.findEntryByID(id).orElse(null);

            if (old != null) {

                old.setTitle(updateEntry.getTitle());
                old.setContent(updateEntry.getContent());

                journalEntryService.saveEntry(old);

                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}