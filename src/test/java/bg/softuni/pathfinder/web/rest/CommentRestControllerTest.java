package bg.softuni.pathfinder.web.rest;

import bg.softuni.pathfinder.model.Comment;
import bg.softuni.pathfinder.model.Role;
import bg.softuni.pathfinder.model.User;
import bg.softuni.pathfinder.model.dto.CommentDto;
import bg.softuni.pathfinder.model.enums.UserRoles;
import bg.softuni.pathfinder.service.AuthService;
import bg.softuni.pathfinder.service.CommentService;
import com.cloudinary.provisioning.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommentRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private AuthService authService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getAllComments_requestIsMade_allCommentsReturned() throws Exception {
        when(commentService.getCommentsByRoute(1L)).thenReturn(List.of(
            createComment("Test comment 1"), createComment("Test comment 2")
        ));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/1/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].text", is("Test comment 1")))
                .andExpect(jsonPath("$.[0].authorName", is("Test User")))
                .andExpect(jsonPath("$.[1].text", is("Test comment 2")))
                .andExpect(jsonPath("$.[1].authorName", is("Test User")));
    }

    @Test
    public void getComment_commentExists_commentReturned() throws Exception {
        when(commentService.getComment(1L)).thenReturn(createComment("Test comment."));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/1/comments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is("Test comment.")))
                .andExpect(jsonPath("$.authorName", is("Test User")))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void getComment_commentDoesNotExist_notFoundStatusIsReturned() throws Exception {
        when(commentService.getComment(1L)).thenThrow(RuntimeException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/1/comments/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createComment_anonymousUser_forbiddenStatusIsReturned() throws Exception {
        CommentDto commentDto = new CommentDto();
        commentDto.setText("Test comment!");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/1/comments")
                .content(objectMapper.writeValueAsString(commentDto)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testUser")
    public void createComment_withTestUser_commentIsCreated() throws Exception {
        CommentDto commentDto = new CommentDto();
        commentDto.setText("Test comment!");
        User user = new User();
        user.setUsername("testUser");
        when(authService.getUserByUsername("testUser")).thenReturn(user);
        when(commentService.createComment(eq(commentDto), anyLong(), eq(user))).thenReturn(createComment("Test comment!"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/1/comments")
                        .content(objectMapper.writeValueAsString(commentDto))
                        .contentType("application/json")
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text", is("Test comment!")))
                .andExpect(jsonPath("$.authorName", is("Test User")));
    }

    @Test
    @WithAnonymousUser
    public void deleteComment_anonymousUser_forbiddenStatusIsReturned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/1/comments/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testUser")
    public void deleteComment_withAuthorOfComment_commentIsDeletedSuccessfully() throws Exception {
        User user = new User();
        user.setUsername("testUser");
        user.setFullName("Test User");
        when(authService.getUserByUsername("testUser")).thenReturn(user);
        Comment comment = createComment("Comment to be deleted!");
        comment.setAuthor(user);
        when(commentService.getComment(1L)).thenReturn(comment);
        when(commentService.deleteComment(1L)).thenReturn(comment);

        mockMvc.perform(delete("/api/1/comments/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is("Comment to be deleted!")))
                .andExpect(jsonPath("$.authorName", is("Test User")));
    }

    @Test
    @WithMockUser(username = "adminUser")
    public void deleteComment_withAdmin_commentIsDeletedSuccessfully() throws Exception {
        User admin = new User();
        admin.setUsername("adminUser");
        Role role = new Role();
        role.setName(UserRoles.ADMIN);
        admin.setRoles(Collections.singleton(role));
        when(authService.getUserByUsername("adminUser")).thenReturn(admin);
        Comment comment = createComment("Comment to be deleted!");
        when(commentService.getComment(1L)).thenReturn(comment);
        when(commentService.deleteComment(1L)).thenReturn(comment);

        mockMvc.perform(delete("/api/1/comments/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is("Comment to be deleted!")))
                .andExpect(jsonPath("$.authorName", is("Test User")));
    }

    @Test
    @WithMockUser(username = "adminUser")
    public void deleteComment_withAdminCommentDoesNotExist_notFoundStatusIsReturned() throws Exception {
        User admin = new User();
        admin.setUsername("adminUser");
        Role role = new Role();
        role.setName(UserRoles.ADMIN);
        admin.setRoles(Collections.singleton(role));
        when(authService.getUserByUsername("adminUser")).thenReturn(admin);
        when(commentService.getComment(1L)).thenThrow(RuntimeException.class);

        mockMvc.perform(delete("/api/1/comments/1")
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    private Comment createComment(String text) {
        User author = new User();
        author.setId(1L);
        author.setUsername("testUser");
        author.setFullName("Test User");

        Comment comment = new Comment();
        comment.setCreated(LocalDateTime.now());
        comment.setText(text);
        comment.setAuthor(author);
        comment.setId(1);

        return comment;
    }
}