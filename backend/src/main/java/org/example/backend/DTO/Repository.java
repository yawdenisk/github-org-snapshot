package org.example.backend.DTO;

public record Repository(String html_url,
                         int stargazers_count,
                         int forks_count,
                         String language,
                         String updated_at,
                         String name,
                         String description){}
