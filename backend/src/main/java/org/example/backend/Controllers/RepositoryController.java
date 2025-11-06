package org.example.backend.Controllers;

import org.example.backend.DTO.Repository;
import org.example.backend.Services.GitHubService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class RepositoryController {
    private  final GitHubService gitHubService;
    public RepositoryController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }
    @GetMapping("/org/{org}/repos")
    public List<Repository> getOrg(@PathVariable String org,
                                   @RequestParam(defaultValue = "5") String limit,
                                   @RequestParam(defaultValue = "stars") String sort) {
        return gitHubService.getRepositories(org, limit, sort);
    }
}
