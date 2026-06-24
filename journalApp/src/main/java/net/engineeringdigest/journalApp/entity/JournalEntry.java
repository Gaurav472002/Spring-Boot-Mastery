package net.engineeringdigest.journalApp.entity;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "journal_entries")
public class JournalEntry {

    // If we don't provide Id while creating an entry mongodb will assign an Id of type OBjectID automatically
    @Id
    private String id;

    @NonNull
    private String title;

    private LocalDateTime date;

    private String content;



}
