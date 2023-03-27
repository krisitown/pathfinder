package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.model.Comment;
import bg.softuni.pathfinder.model.Route;
import bg.softuni.pathfinder.model.User;
import bg.softuni.pathfinder.model.dto.CommentDto;
import bg.softuni.pathfinder.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private RouteService routeService;

    @InjectMocks
    private CommentService commentService;

    @Captor
    private ArgumentCaptor<Comment> commentCaptor;

    @Test
    public void createComment_validData_commentIsSaved() {
        CommentDto commentDto = new CommentDto();
        commentDto.setText("Test comment!");
        long routeId = 1;
        User author = new User();
        Route route = new Route();
        when(routeService.getRouteById(routeId)).thenReturn(route);

        commentService.createComment(commentDto, routeId, author);

        verify(commentRepository, times(1)).save(commentCaptor.capture());

        Comment argument = commentCaptor.getValue();
        assertEquals("Test comment!", argument.getText());
        assertEquals(author, argument.getAuthor());
        assertEquals(route, argument.getRoute());
    }
}