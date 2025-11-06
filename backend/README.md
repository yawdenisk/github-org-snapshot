# Backend: GitHub Org Snapshot API

This is a production-ready backend service that implements an API Facade pattern for the public GitHub API.

This application provides a unified endpoint to fetch, sort, and limit an organization's repositories, built following best practices for creating secure and resilient APIs.

##  Running Locally

1.  Ensure you have Java 17+ and Maven installed.
2.  Navigate to the `/backend` directory:
    ```bash
    cd /path/to/project/backend
    ```
3.  Run the application using the Maven wrapper:
    ```bash
    mvn spring-boot:run
    ```
    The service will start on `http://localhost:8080`.

## Curl Examples

You can test the API using `curl` or any API client like Postman or Insomnia.

**1. Default Request (for `spring-projects`)**
*(Will use `limit=5` and `sort=stars` by default)*

```bash
curl "http://localhost:8080/api/org/spring-projects/repos"
```

**2. Request Sorting by `updated`**
*(Will use `limit=5` by default)*

```bash
curl "http://localhost:8080/api/org/spring-projects/repos?sort=updated"
```

**3. Request with a `limit` of 10**
*(Will use `sort=stars` by default)*

```bash
curl "http://localhost:8080/api/org/spring-projects/repos?limit=10"
```

**4. Full Request with all parameters**
*(Top 3 repositories from `vercel`, sorted by `updated`)*

```bash
curl "http://localhost:8080/api/org/vercel/repos?limit=3&sort=updated"
```

**5. Validation Demo: Request with invalid `limit`**
*(The server will forcibly clamp this request to `20`)*

```bash
curl "http://localhost:8080/api/org/google/repos?limit=1000"
```

**6. Resilience Demo: Non-existent organization**
*(The server will return a `200 OK` and an empty list `[]`)*

```bash
curl "http://localhost:8080/api/org/non-existent-org-12345"
```