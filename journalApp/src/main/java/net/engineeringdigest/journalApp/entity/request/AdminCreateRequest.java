package net.engineeringdigest.journalApp.entity.request;

import lombok.Data;

@Data
public class AdminCreateRequest {

    private String userName;
    private String email;
    private String password;
    private boolean sentimentAnalysis;
}