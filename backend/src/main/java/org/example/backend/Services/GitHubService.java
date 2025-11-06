package org.example.backend.Services;

import org.example.backend.DTO.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitHubService {
    private final RestTemplate restTemplate;

    public GitHubService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Repository> getRepositories(String org, String limit, String sort) {
        try{
            Repository[] repositories = restTemplate.getForObject("https://api.github.com/orgs/"+org+"/repos?per_page=100", Repository[].class);
            if (repositories != null) {
                int limitAmount;
                try {
                    limitAmount = Integer.parseInt(limit);
                    if (limitAmount > 20) limitAmount = 20;
                    if (limitAmount < 1) limitAmount = 1;
                } catch (NumberFormatException e) {
                    limitAmount = 5;
                }
                return Arrays.stream(repositories)
                        .sorted("stars".equals(sort) ? Comparator.comparing(Repository::stargazers_count).reversed() : Comparator.comparing(Repository::updated_at).reversed())
                        .limit(limitAmount)
                        .collect(Collectors.toList());
            }else{
                return List.of();
            }
        }catch(Exception e){
            return List.of();
        }
    }
}
