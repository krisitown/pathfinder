package bg.softuni.pathfinder.web;

import bg.softuni.pathfinder.model.dto.CommentDto;
import bg.softuni.pathfinder.model.dto.CommentServiceDto;
import bg.softuni.pathfinder.model.views.CommentView;
import bg.softuni.pathfinder.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class CommentRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;


    @Test
    @WithMockUser
    public void getComments() throws Exception {
        when(commentService.getCommentsByRoute(anyLong())).thenReturn(Arrays.asList(
                new CommentView("Comment One", "Author1"),
                new CommentView("Comment Two", "Author2")
        ));

        mockMvc.perform(get("/api/1/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].message", is("Comment One")));
    }

    @Test
    @WithMockUser(username = "testUsername")
    public void createComment() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        when(commentService.createComment(any())).thenAnswer(i -> {
            CommentServiceDto commentServiceDto = i.getArgument(0);
            return new CommentView(commentServiceDto.getMessage(), commentServiceDto.getUserName());
        });

        mockMvc.perform(post("/api/1/comments")
                        .content(objectMapper.writeValueAsString(new CommentDto("This is a comment!")))
                        .with(csrf())
                        .contentType("application/json")
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("This is a comment!")))
                .andExpect(jsonPath("$.authorName", is("testUsername")));

    }
}
