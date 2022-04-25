package com.restapi.gitHub;

import java.util.Map;

public class GitHubProfile {
    private String bio;
    private String login;
    private String name;
    private Map<String,Long> languages;


    public GitHubProfile(String bio, String login, String name, Map<String, Long> languages) {
        this.bio = bio;
        this.login = login;
        this.name = name;
        this.languages = languages;
    }

    public String getBio() {
        return bio;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public Map<String, Long> getLanguages() {
        return languages;
    }


}
