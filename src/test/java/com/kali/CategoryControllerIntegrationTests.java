package com.kali;

import com.kali.entity.Category;
import com.kali.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerIntegrationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
        Category programming = new Category();
        programming.setName("Programming");
        programming.setDescription("All programming courses");
        categoryRepository.save(programming);
    }

    @Test
    void testGetAllCategories() {
        ResponseEntity<Category[]> response = restTemplate.getForEntity("/api/categories", Category[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThanOrEqualTo(1);
    }

    @Test
    void testCreateCategory() {
        Category category = new Category();
        category.setName("Mathematics");
        category.setDescription("All math courses");
        ResponseEntity<Category> response = restTemplate.postForEntity("/api/categories", category, Category.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().getCategoryId()).isNotNull();
            assertThat(response.getBody().getName()).isEqualTo("Mathematics");
    }

    @Test
    void testGetCategoryById() {
        Category category = new Category();
        category.setName("Java");
        category.setDescription("Java programming courses");
        Category saved = categoryRepository.save(category);
            ResponseEntity<Category> response = restTemplate.getForEntity("/api/categories/" + saved.getCategoryId(), Category.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Java");
    }

    @Test
    void testUpdateCategory() {
        Category category = new Category();
        category.setName("Old Name");
        category.setDescription("Old description");
        Category saved = categoryRepository.save(category);
        Category update = new Category();
        update.setName("New Name");
        update.setDescription("New description");
            ResponseEntity<Category> response = restTemplate
                .exchange("/api/categories/" + saved.getCategoryId(), org.springframework.http.HttpMethod.PUT, new org.springframework.http.HttpEntity<>(update), Category.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("New Name");
    }

    @Test
    void testDeleteCategory() {
        Category category = new Category();
        category.setName("To Delete");
        category.setDescription("To be deleted");
        Category saved = categoryRepository.save(category);
            ResponseEntity<Void> response = restTemplate.exchange("/api/categories/" + saved.getCategoryId(), org.springframework.http.HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
            assertThat(categoryRepository.findById(saved.getCategoryId())).isEmpty();
    }
}
