package com.restapi.controller;

import com.restapi.gitHub.GitHubProfile;
import com.restapi.service.UsersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class UsersController {
    private final UsersService usersService;

    //Autowired, create bean
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/users/{username}")
    public GitHubProfile githubProfile(@PathVariable String username, @RequestHeader("Auth") String authToken) throws IOException {
        System.out.println(authToken);
        return usersService.getUser(username, authToken);
    }


}
