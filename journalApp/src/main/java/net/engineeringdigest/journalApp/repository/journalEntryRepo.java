package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;


/* WE don't have to write any code here just by extending the MongoRepository we can get
access to all the predefined methods to interact with the mongodb */

public interface journalEntryRepo  extends  MongoRepository<JournalEntry,String> {



}
