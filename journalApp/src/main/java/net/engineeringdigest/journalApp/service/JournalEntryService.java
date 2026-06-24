package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.journalEntryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class JournalEntryService {

    @Autowired
    private journalEntryRepo journalEntryRepo ;

    @Autowired
    private UserService userService;


    /* In the following method if any error occurs between any of the steps then this may raise an exeception
    To make sure either all of the statements get executed or none we will make this method a transaction so that if some steps are
    executed they will be rolled back if the next step fails to maintain the data consistency
     */
    /* Add this transactional annotation and also add @EnableTransactionManagement in main app file */
    @Transactional
    public void saveEntry(JournalEntry je, String userName){

        try{
            User user = userService.findByUserName(userName);

            if(user == null){
                throw new RuntimeException("User not found");
            }

            je.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepo.save(je);

            // Add this journal entries inside the array of the particular user
            user.getJournalEntries().add(saved);
            // saved the user with the new journal entry
            userService.saveEntry(user);
        }
        catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occured while saving the entry",e);
        }

    }

    public void saveEntry(JournalEntry je){

        journalEntryRepo.save(je);
    }

    public List<JournalEntry> getAllEntries(){

        return journalEntryRepo.findAll();
    }
    public Optional <JournalEntry> findEntryByID(String Id){

        return journalEntryRepo.findById(Id);
    }

    @Transactional
    public void deleteEntryById(String id, String userName){

        User user = userService.findByUserName(userName);

        if(user != null){

            user.getJournalEntries()
                    .removeIf(x -> x.getId().equals(id));

            userService.saveEntry(user);
        }

        journalEntryRepo.deleteById(id);
    }

}
