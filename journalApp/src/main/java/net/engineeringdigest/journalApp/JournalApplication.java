package net.engineeringdigest.journalApp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement

// All the tasks related to the transaction methods will be managed by the PlatformTransactionManager which is a interface which is implemented by the MongoTransactionManager
public class JournalApplication {

    public static void main(String[] args) {
        SpringApplication.run(JournalApplication.class, args);
    }

    @Bean
//    @Profile("dev")
    public PlatformTransactionManager mongoTransactions(MongoDatabaseFactory dbFactory){
        return new MongoTransactionManager(dbFactory);
    }
}