package com.restapi.service;

import com.restapi.exceptions.RestTemplateResponseErrorHandler;
import com.restapi.gitHub.GitHubProfile;
import com.restapi.gitHub.GitHubProfileRaw;
import com.restapi.gitHub.GitHubRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class UsersService {

    private RestTemplate restTemplate;
    private RepositoriesService repositoriesService;
    @Value("${github.token}")
    private String token;

    public UsersService(RestTemplateBuilder restTemplateBuilder, RepositoriesService repositoriesService) {
        restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
        this.repositoriesService = repositoriesService;
    }

    public GitHubProfile getUser (String username) throws IOException {
        GitHubProfileRaw gitHubProfileRaw = getUserInfo(username);
        Map<String,Long> allLanguages = mergeAllLanguages(username);
        GitHubProfile githubProfile = new GitHubProfile(gitHubProfileRaw.getBio(),gitHubProfileRaw.getLogin(),
                gitHubProfileRaw.getName(),allLanguages);

        return githubProfile;
    }

    public GitHubProfileRaw getUserInfo (String username){
        HttpHeaders headers = new HttpHeaders();
        String urlToAPI = "https://api.github.com/users/" + username;

        headers.set("Authorization", String.format("Bearer %s",token));
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<GitHubProfileRaw> response = restTemplate.exchange(urlToAPI, HttpMethod.GET,
                requestEntity, GitHubProfileRaw.class);

        return response.getBody();
    }

    public Map<String, Long> mergeAllLanguages(String username) throws IOException {
        ArrayList<GitHubRepository> allRepositories = repositoriesService.getRepositories(username);
        Map<String,Long> allLanguages = new HashMap<>();
        for(GitHubRepository repository: allRepositories) {
            Map<String, Long> language = repositoriesService.getLanguagesForRepository(username, repository.getName());
            language.forEach((name, size) -> allLanguages.merge(name, size, (oldSize, newSize) -> oldSize + newSize));
        }
        return allLanguages;
    }

}
