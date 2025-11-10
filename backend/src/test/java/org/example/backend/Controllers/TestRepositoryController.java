package org.example.backend.Controllers;

import org.example.backend.DTO.Repository;
import org.example.backend.Services.GitHubService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestRepositoryController {
    @Mock
    private GitHubService gitHubService;
    @InjectMocks
    private RepositoryController repositoryController;
    @Test
    public void getOrg_shouldReturnDefaultReposAndCallServiceCorrectly() {
        String org = "vercel";

        List<Repository> expectedRepositoryList = Collections.singletonList(new Repository("https//:html-example", 2, 4, "en", Instant.now().minusSeconds(100).toString(), "vercel", "description"));

        when(gitHubService.getRepositories(org, "1", "stars"))
                .thenReturn(expectedRepositoryList);

        List<Repository> actualRepositoryList = repositoryController.getOrg(org, "1", "stars");

        assertEquals(expectedRepositoryList, actualRepositoryList);

        verify(gitHubService).getRepositories(org, "1", "stars");
    }
}