package com.restapi.gitHub;

import java.util.ArrayList;
import java.util.Map;

public class GitHubRepository {
    private final String name;
    private final Map<String,Long> languages;

    public GitHubRepository(String name, Map<String,Long> languages) {
        this.name = name;
        this.languages = languages;
    }

    public String getName() {
        return name;
    }

    public Map<String,Long> getLanguages() {
        return languages;
    }

}
