package net.engineeringdigest.journalApp.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {
    private String title;
    private String body;
    private int userId;

}
