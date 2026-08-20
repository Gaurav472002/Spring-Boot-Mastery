//package net.engineeringdigest.journalApp.scheduler;
//
//import net.engineeringdigest.journalApp.cache.AppCache;
//import net.engineeringdigest.journalApp.entity.JournalEntry;
//import net.engineeringdigest.journalApp.entity.User;
//import net.engineeringdigest.journalApp.repository.UserRepositoryImpl;
//import net.engineeringdigest.journalApp.service.EmailService;
//import net.engineeringdigest.journalApp.service.SentimentAnalysisService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//public class UserScheduler {
//
//    @Autowired
//    private EmailService emailService;
//
//    @Autowired
//    private UserRepositoryImpl userRepository;
//
////    @Autowired
////    private SentimentAnalysisService sentimentAnalysisService;
//
//    @Autowired
//    private AppCache appCache;
//
//    @Scheduled(cron = "0 */2 * * * *")
//    public void fetchUserAndSendMail() {
//
//        System.out.println("Scheduler started");
//
//        List<User> users = userRepository.getUserForSA();
//
//        if (users == null || users.isEmpty()) {
//            System.out.println("No users found");
//            return;
//        }
//
//        System.out.println("Users found: " + users.size());
//
//        for (User user : users) {
//
//            if (user == null) {
//                System.out.println("Skipping null user");
//                continue;
//            }
//
//            if (user.getEmail() == null ||
//                    user.getEmail().trim().isEmpty()) {
//
//                System.out.println(
//                        "Skipping user because email is missing: "
//                                + user.getUserName()
//                );
//
//                continue;
//            }
//
//            try {
//
//                System.out.println(
//                        "Processing user: " + user.getUserName()
//                );
//
//                List<JournalEntry> journalEntries =
//                        user.getJournalEntries();
//
//                if (journalEntries == null ||
//                        journalEntries.isEmpty()) {
//
//                    System.out.println(
//                            "No journal entries for user: "
//                                    + user.getUserName()
//                    );
//
//                    continue;
//                }
//
//                LocalDateTime sevenDaysAgo =
//                        LocalDateTime.now()
//                                .minus(7, ChronoUnit.DAYS);
//
//                List<String> filteredEntries =
//                        journalEntries.stream()
//                                .filter(x -> x != null)
//                                .filter(x -> x.getDate() != null)
//                                .filter(x -> x.getDate()
//                                        .isAfter(sevenDaysAgo))
//                                .map(JournalEntry::getContent)
//                                .filter(x -> x != null &&
//                                        !x.trim().isEmpty())
//                                .collect(Collectors.toList());
//
//                if (filteredEntries.isEmpty()) {
//
//                    System.out.println(
//                            "No journal entries from last 7 days for: "
//                                    + user.getUserName()
//                    );
//
//                    continue;
//                }
//
//                String entry =
//                        String.join(" ", filteredEntries);
//
//                System.out.println(
//                        "Journal content collected for: "
//                                + user.getUserName()
//                );
//
//                String sentiment =
//                        sentimentAnalysisService
//                                .getSentiment(entry);
//
//                System.out.println(
//                        "Sentiment: " + sentiment
//                );
//
//                System.out.println(
//                        "Sending email to: " + user.getEmail()
//                );
//
//                emailService.sendEmail(
//                        user.getEmail(),
//                        "Sentiment for last 7 days",
//                        sentiment
//                );
//
//                System.out.println(
//                        "Email sent successfully to: "
//                                + user.getEmail()
//                );
//
//            } catch (Exception e) {
//
//                System.out.println(
//                        "Error while processing user: "
//                                + user.getUserName()
//                );
//
//                e.printStackTrace();
//            }
//        }
//
//        System.out.println("Scheduler finished");
//    }
////    @Scheduled(...)
////    public void clearAppCache() {
////        appCache.init();
////    }
//
//}
//
