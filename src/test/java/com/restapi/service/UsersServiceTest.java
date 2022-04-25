package com.restapi.service;

import com.restapi.gitHub.GitHubProfileRaw;
import com.restapi.gitHub.GitHubRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



@SpringBootTest
class UsersServiceTest {


    @Mock
    private RepositoriesService repositoriesService;

    @Mock
    private RestTemplate restTemplate;

    @Value("${github.token}")
    private String token;

    RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

    @InjectMocks
    private UsersService usersService = new UsersService(restTemplateBuilder,repositoriesService);


    public UsersServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void mergeAllLanguagesTest() throws IOException {
        ArrayList<GitHubRepository> allRepositories = new ArrayList<>();
        Map<String, Long> languages1 = new HashMap<>();
        languages1.put("Java",2L);
        languages1.put("CSS",2L);
        GitHubRepository gitHubRepository1 = new GitHubRepository("firstTestRepo",languages1);
        Map<String, Long> languages2 = new HashMap<>();
        languages2.put("Java",2L);
        languages2.put("C++",3L);
        GitHubRepository gitHubRepository2 = new GitHubRepository("secondTestRepo",languages2);
        allRepositories.add(gitHubRepository1);
        allRepositories.add(gitHubRepository2);

        Mockito.when(repositoriesService.getRepositories("testUser"))
                .thenReturn(allRepositories);
        Mockito.when(repositoriesService.getLanguagesForRepository("testUser","firstTestRepo"))
                .thenReturn(languages1);
        Mockito.when(repositoriesService.getLanguagesForRepository("testUser","secondTestRepo"))
                .thenReturn(languages2);

        Map<String, Long> allLanguagesModel = new HashMap<>();
        allLanguagesModel.put("Java",4L);
        allLanguagesModel.put("C++",3L);
        allLanguagesModel.put("CSS",2L);

        Assertions.assertEquals(allLanguagesModel,usersService.mergeAllLanguages("testUser"));
    }

    @Test
    public void getUserInfoTest(){
        MockitoAnnotations.openMocks(this);

        GitHubProfileRaw gitHubProfileRawModel = new GitHubProfileRaw("bio","login","name");
        GitHubProfileRaw gitHubProfileRaw = new GitHubProfileRaw("bio","login","name");

        org.springframework.http.HttpHeaders headers = new HttpHeaders();
        String urlToAPI = "https://api.github.com/users/testUser";
        headers.set("Authorization", String.format("Bearer %s",token));

        ResponseEntity<GitHubProfileRaw> myEntity = new ResponseEntity<>(gitHubProfileRaw,headers, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(ArgumentMatchers.eq(urlToAPI), ArgumentMatchers.any(HttpMethod.class),
                        ArgumentMatchers.any(), ArgumentMatchers.<Class<GitHubProfileRaw>>any()))
                .thenReturn(myEntity);

        gitHubProfileRaw = usersService.getUserInfo("testUser");

        Assertions.assertEquals(gitHubProfileRawModel.getName(),gitHubProfileRaw.getName());
        Assertions.assertEquals(gitHubProfileRawModel.getBio(),gitHubProfileRaw.getBio());
        Assertions.assertEquals(gitHubProfileRawModel.getLogin(),gitHubProfileRaw.getLogin());
    }


}