package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


import java.util.List;


/* we will learn about Criteria
and Query that is used to write custom queries to interact with db */
@Repository
public class UserRepositoryImpl {


    // To run the custom query created using Criteria we will use MongoTemplate
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSA(){

        Query query = new Query();

        /* Just like this we can add many more criteira like greater than or less than or many more
//        query.addCriteria(Criteria.where("userName").is("Gaurav"));
//        query.addCriteria(Criteria.where("age").gte(20));
//        query.addCriteria(Criteria.where("age").lte(30)); */


        // query for finding users sentiments

//       query.addCriteria(Criteria.where("email").exists(true));
//       query.addCriteria(Criteria.where("email").ne(null).ne(""));

        // Using nin for multiple values
//        query.addCriteria(Criteria.where("userName").nin("Gaurav","Saurav", "Kumar" ));

        // Instead of above email criterias we can use regex

        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
       List<User> users =mongoTemplate.find(query, User.class);
       return users;
    }
}
