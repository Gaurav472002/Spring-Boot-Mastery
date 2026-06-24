//package net.engineeringdigest.journalApp.controller;
//
//
//import net.engineeringdigest.journalApp.entity.JournalEntry;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
//// Rest Controllers are special type of components that are used to create the rest api's
//@RestController
//@RequestMapping("/v1/journal")
//public class journalEntryController {
//
//
//    private Map<Long ,JournalEntry> journalEntries = new HashMap();
//
//    @GetMapping("/getEntries")
//    public List<JournalEntry> getAll(){
//        return new ArrayList<>(journalEntries.values());
//    }
//
//    @PostMapping("/sendEntry")
//    public void createEntry(@RequestBody JournalEntry myEntry){
//
//        journalEntries.put(myEntry.getId(), myEntry);
//    }
//    @GetMapping("getEntryById/{id}")
//    public JournalEntry getById( @PathVariable Long id){
//
//        return journalEntries.get(id);
//    }
//
//    @DeleteMapping("deleteEntryById/{id}")
//    public Boolean deleteEntry( @PathVariable Long id){
//        JournalEntry x = journalEntries.get(id);
//        if(x!=null){
//            journalEntries.remove(id);
//            return true;
//        }
//        return false;
//
//    }
//
//    @PutMapping("update/{id}")
//    public JournalEntry updateJournalById(@PathVariable Long id, @RequestBody JournalEntry myEntry){
//        return journalEntries.put(id,myEntry);
//    }
//}
