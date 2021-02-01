package com.iv1201.project.recruitment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void responseCodeTest() {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<String> response = testRestTemplate.getForEntity("http://localhost:" + port + "/login", String.class);
        assert(response.getStatusCode().equals(HttpStatus.OK));
    }
}