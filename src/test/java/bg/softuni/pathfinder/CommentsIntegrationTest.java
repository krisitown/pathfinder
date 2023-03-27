package bg.softuni.pathfinder;

import bg.softuni.pathfinder.model.Comment;
import bg.softuni.pathfinder.model.Route;
import bg.softuni.pathfinder.model.User;
import bg.softuni.pathfinder.repository.CommentRepository;
import bg.softuni.pathfinder.repository.RouteRepository;
import bg.softuni.pathfinder.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class CommentsIntegrationTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    private User testUser;
    private Route testRoute;

    @BeforeAll
    void beforeAll() {
        testUser = createTestUser();
        userRepository.save(testUser);

        testRoute = createTestRoute(testUser);
        routeRepository.save(testRoute);
    }

    @Test
    public void getAllComments_commentsFound_commentsReturned() throws Exception{
        Comment comment = new Comment();
        comment.setAuthor(testUser);
        comment.setRoute(testRoute);
        comment.setText("Test comment!");
        comment.setCreated(LocalDateTime.now());
        commentRepository.save(comment);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/1/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].authorName", is("John Doe")))
                .andExpect(jsonPath("$.[0].text", is("Test comment!")))
                .andExpect(jsonPath("$.[0].id", is(1)));
    }

    @Test
    @WithMockUser(username = "testUser", password = "password")
    public void deleteComment_attemptToDeleteCommentTwice_commentIsDeletedAndNotFoundIsReturnedTheSecondTime() throws Exception {
        Comment comment = new Comment();
        comment.setAuthor(testUser);
        comment.setRoute(testRoute);
        comment.setText("Test comment!");
        comment.setCreated(LocalDateTime.now());
        commentRepository.save(comment);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/1/comments/1")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is("Test comment!")));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/1/comments/1")
                .with(csrf()))
                .andExpect(status().isNotFound());
    }

    private User createTestUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setFullName("John Doe");
        user.setPassword(passwordEncoder.encode("password"));
        return user;
    }

    private Route createTestRoute(User user) {
        Route route = new Route();
        route.setAuthor(user);
        route.setName("Test Route");
        return route;
    }
}
