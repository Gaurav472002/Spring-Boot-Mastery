package net.engineeringdigest.journalApp.cache;

import net.engineeringdigest.journalApp.entity.ConfigJournalAppEntity;
import net.engineeringdigest.journalApp.repository.configJournalAppRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum Keys {
        WEATHER_API
    }


    @Autowired
    private configJournalAppRepo ConfigJournalAppRepo;

    public Map<String,String> APP_CACHE;



    /* Suppose we make any changes to the data in the credentials present in the db
    instead of restarting the application we can expose an enpoint that will reinitialize
    the map with the updated credentials by calling the init method directly
    This method will empty the hashmap at first and then it will fill with updated creds

     */
    @PostConstruct
    public void init(){

        APP_CACHE = new HashMap<>();
        List<ConfigJournalAppEntity> all = ConfigJournalAppRepo.findAll();

        for(ConfigJournalAppEntity configJournalAppEntity: all){
            APP_CACHE.put(configJournalAppEntity.getKey(),configJournalAppEntity.getValue());
        }
    }
}
