package net.engineeringdigest.journalApp.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponse {

    private String id;
    private String userName;
    private String email;
    private boolean sentimentAnalysis;
    private List<String> roles;
}