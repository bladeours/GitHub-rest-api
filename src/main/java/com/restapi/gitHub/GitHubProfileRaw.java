package com.restapi.gitHub;

import java.util.Map;

public class GitHubProfileRaw {
        private String bio;
        private String login;
        private String name;

        public GitHubProfileRaw() {
        }

    public GitHubProfileRaw(String bio, String login, String name) {
        this.bio = bio;
        this.login = login;
        this.name = name;
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

    public void setBio(String bio) {
        this.bio = bio;
    }

}
