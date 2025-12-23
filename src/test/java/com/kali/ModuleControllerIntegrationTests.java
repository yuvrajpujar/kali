package com.kali;

import com.kali.entity.Module;
import com.kali.repository.ModuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ModuleControllerIntegrationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private com.kali.repository.LessonRepository lessonRepository;

    @BeforeEach
    void setUp() {
        lessonRepository.deleteAll();
        moduleRepository.deleteAll();
        Module module = new Module();
        module.setTitle("Getting Started");
        module.setOrderIndex(1);
        moduleRepository.save(module);
    }

    @Test
    void testGetAllModules() {
        ResponseEntity<Module[]> response = restTemplate.getForEntity("/api/modules", Module[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThanOrEqualTo(1);
    }

    @Test
    void testCreateModule() {
        Module module = new Module();
        module.setTitle("Java Syntax");
        module.setOrderIndex(2);
        ResponseEntity<Module> response = restTemplate.postForEntity("/api/modules", module, Module.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getModuleId()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("Java Syntax");
    }

    @Test
    void testGetModuleById() {
        Module module = new Module();
        module.setTitle("Getting Started");
        module.setOrderIndex(1);
        Module saved = moduleRepository.save(module);
        ResponseEntity<Module> response = restTemplate.getForEntity("/api/modules/" + saved.getModuleId(), Module.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("Getting Started");
    }

    @Test
    void testUpdateModule() {
        Module module = new Module();
        module.setTitle("Old Title");
        module.setOrderIndex(1);
        Module saved = moduleRepository.save(module);
        Module update = new Module();
        update.setTitle("New Title");
        update.setOrderIndex(2);
        ResponseEntity<Module> response = restTemplate.exchange("/api/modules/" + saved.getModuleId(), org.springframework.http.HttpMethod.PUT, new org.springframework.http.HttpEntity<>(update), Module.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("New Title");
    }

    @Test
    void testDeleteModule() {
        Module module = new Module();
        module.setTitle("To Delete");
        module.setOrderIndex(3);
        Module saved = moduleRepository.save(module);
        ResponseEntity<Void> response = restTemplate.exchange("/api/modules/" + saved.getModuleId(), org.springframework.http.HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(moduleRepository.findById(saved.getModuleId())).isEmpty();
    }
}
