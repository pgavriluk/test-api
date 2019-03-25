package com.test.api.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTest {

    @Test
    public void testAPI() {
        //Get all comments
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Comment>> response = restTemplate.exchange("https://jsonplaceholder.typicode.com/comments", HttpMethod.GET,
                                                                                null, new ParameterizedTypeReference<List<Comment>>(){});
        //Collection of objects 'Comment'
        List<Comment> comments = response.getBody();
        //Assert the response code
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        //Assert that number of comments is greater than 0
        assertThat(comments.size(), greaterThan(0));


        //assert that body has comment with email: "Jayne_Kuhic@sydney.com"
        boolean isJayneEmailExist = false;

        for (Comment comment : comments) {
             if(comment.getEmail().contains("Jayne_Kuhic@sydney.com")){
                 isJayneEmailExist = true;
                 break;
             }
        }

        assertTrue(isJayneEmailExist);

        //Keep all comments that have postId equal to 1 and comments that contain word "non" in the body.
        List<Comment> commentsFiltered = comments.stream().filter(comment -> comment.getPostId().equals(1)
                                                && comment.getBody().toLowerCase().contains("non")).collect(Collectors.toList());
        //Assert that filtered comments with only postId equal to 1 and contains word "non" in the body
        for (Comment comment: commentsFiltered){
            assertTrue(comment.getPostId().equals(1));
            assertTrue(comment.getBody().toLowerCase().contains("non"));
        }

    }

}
