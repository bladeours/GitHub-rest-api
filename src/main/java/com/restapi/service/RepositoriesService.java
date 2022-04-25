package com.restapi.service;

import com.restapi.exceptions.RestTemplateResponseErrorHandler;
import com.restapi.gitHub.GitHubRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
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
public class RepositoriesService {

    @Value("${github.token}")
    private String token;
    private final RestTemplate restTemplate;
    public RepositoriesService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();

    }
    
    public ArrayList<GitHubRepository> getRepositories(String username) throws IOException {
        ArrayList<GitHubRepository> allRepositories = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("Bearer %s",token));
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        String urlToAPI = String.format("https://api.github.com/users/%s", username);
        ResponseEntity<String> response = restTemplate.exchange(urlToAPI, HttpMethod.GET, requestEntity,
                String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode amountOfPublicRepoJson = root.path("public_repos");
        int amountOfPublicRepo = amountOfPublicRepoJson.asInt();
        int page = 1;
        for (int i = 0; i < amountOfPublicRepo; i += 100) {

            urlToAPI = String.format("https://api.github.com/users/%s/repos?per_page=100&page=%d",username,page);
            ParameterizedTypeReference<ArrayList<HashMap<String, Object>>> responseType =
                    new ParameterizedTypeReference<>() {};
            ResponseEntity<ArrayList<HashMap<String, Object>>> responseRepositories = restTemplate.exchange(urlToAPI,
                    HttpMethod.GET, requestEntity, responseType);
            ArrayList<HashMap<String, Object>> repositoriesArrayList = responseRepositories.getBody();
            for(HashMap<String, Object> repository : repositoriesArrayList){
                String repositoryName = repository.get("name").toString();
                GitHubRepository gitHubrepository = new GitHubRepository(repositoryName,
                        getLanguagesForRepository(username, repositoryName));
                allRepositories.add(gitHubrepository);
            }
            page++;
        }
        return allRepositories;
    }

    protected Map<String,Long> getLanguagesForRepository(java.lang.String username, java.lang.String repositoryName){
        HttpHeaders headers = new HttpHeaders();
        java.lang.String urlToAPI = java.lang.String.format("https://api.github.com/repos/%s/%s/languages",username,repositoryName);
        headers.set("Authorization", String.format("Bearer %s",token));
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ParameterizedTypeReference<HashMap<java.lang.String, java.lang.Long>> responseType =
                new ParameterizedTypeReference<>() {};
        ResponseEntity<HashMap<java.lang.String, java.lang.Long>> response = restTemplate.exchange(urlToAPI, HttpMethod.GET, requestEntity,
                responseType);

        return response.getBody();
    }
}
