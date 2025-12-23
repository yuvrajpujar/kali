package com.kali;

import com.kali.entity.User;
import com.kali.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/users";
    }

    @Test
    void testGetAllUsers() {
        ResponseEntity<User[]> response = restTemplate.getForEntity(getBaseUrl(), User[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThanOrEqualTo(3);
    }

    @Test
    void testGetUserById() {
        List<User> users = userRepository.findAll();
        User user = users.get(0);
        ResponseEntity<User> response = restTemplate.getForEntity(getBaseUrl() + "/" + user.getUserId(), User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void testCreateUser() {
        User newUser = new User();
        newUser.setUsername("integrationuser");
        newUser.setEmail("integrationuser@example.com");
        newUser.setPasswordHash("hash5");
        newUser.setFullName("Integration User");
        newUser.setRole("student");
        newUser.setBio("Bio for integration user");
        newUser.setMetadata("{}");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<>(newUser, headers);
        ResponseEntity<User> response = restTemplate.postForEntity(getBaseUrl(), request, User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo("integrationuser");
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setUsername("updateuser");
        user.setEmail("updateuser@example.com");
        user.setPasswordHash("hash6");
        user.setFullName("Update User");
        user.setRole("student");
        user.setBio("Bio for update user");
        user.setMetadata("{}");
        user = userRepository.save(user);
        user.setFullName("Updated Name");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<User> response = restTemplate.exchange(getBaseUrl() + "/" + user.getUserId(), HttpMethod.PUT, request, User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getFullName()).isEqualTo("Updated Name");
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setUsername("deleteuser");
        user.setEmail("deleteuser@example.com");
        user.setPasswordHash("hash7");
        user.setFullName("Delete User");
        user.setRole("student");
        user.setBio("Bio for delete user");
        user.setMetadata("{}");
        user = userRepository.save(user);
        ResponseEntity<Void> response = restTemplate.exchange(getBaseUrl() + "/" + user.getUserId(), HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(userRepository.findById(user.getUserId())).isEmpty();
    }
}
