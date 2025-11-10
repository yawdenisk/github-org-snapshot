package org.example.backend.Services;

import org.example.backend.DTO.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestGitHubService {
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private GitHubService gitHubService;
    private Repository[] mockRepositories;
    private final String TEST_ORG = "vercel";
    private final String BASE_URL = "https://api.github.com/orgs/" + TEST_ORG + "/repos?per_page=100";
    @BeforeEach
    void setUp() {
        mockRepositories = new Repository[]{
                new Repository("https://C", 50, 0, "en", Instant.now().minusSeconds(100).toString(), "repo-C", "desc"), // 50 звёзд, старая дата
                new Repository("https://A", 100, 0, "en", Instant.now().plusSeconds(100).toString(), "repo-A", "desc"), // 100 звёзд, новая дата
                new Repository("https://B", 75, 0, "en", Instant.now().toString(), "repo-B", "desc") // 75 звёзд, средняя дата
        };
    }
    @Test
    public void getRepositories_shouldSortByStarsAndApplyLimit(){
        when(restTemplate.getForObject(eq(BASE_URL), eq(Repository[].class)))
                .thenReturn(mockRepositories);
        List<Repository> result = gitHubService.getRepositories(TEST_ORG, "2", "stars");
        assertEquals(2, result.size());
        assertEquals("repo-A", result.get(0).name());
        assertEquals("repo-B", result.get(1).name());
    }
    @Test
    public void getRepositories_shouldSortByUpdateDateWhenSortIsNotStars(){
        when(restTemplate.getForObject(eq(BASE_URL), eq(Repository[].class))).thenReturn(mockRepositories);
        List<Repository> result = gitHubService.getRepositories(TEST_ORG, "2", "updated");
        assertEquals("repo-A", result.get(0).name());
        assertEquals("repo-B", result.get(1).name());
    }
    @Test
    public void getRepositories_shouldLimitToTwentyWhenInputIsTooLarge(){
        when(restTemplate.getForObject(eq(BASE_URL), eq(Repository[].class))).thenReturn(mockRepositories);
        List<Repository> result = gitHubService.getRepositories(TEST_ORG, "50", "stars");
        assertTrue(result.size() <= 20);
    }
    @Test
    public void getRepositories_shouldReturnEmptyListWhenRestTemplateReturnsNull(){
        when(restTemplate.getForObject(eq(BASE_URL), eq(Repository[].class))).thenReturn(null);
        List<Repository> result = gitHubService.getRepositories(TEST_ORG, "2", "stars");
        assertTrue(result.isEmpty());
    }
}
