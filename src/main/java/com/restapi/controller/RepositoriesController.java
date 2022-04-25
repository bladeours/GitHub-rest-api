package com.restapi.controller;

import com.restapi.gitHub.GitHubRepository;
import com.restapi.service.RepositoriesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class RepositoriesController {

    private final RepositoriesService repositoriesService;

    //Autowired, create bean
    public RepositoriesController(RepositoriesService repositoriesService) {
        this.repositoriesService = repositoriesService;
    }

    @GetMapping("/user-repositories/{username}")
    public ArrayList<GitHubRepository> githubRepositories(@PathVariable String username) throws IOException {
        return repositoriesService.getRepositories(username);
    }
}
