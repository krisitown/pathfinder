package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.model.Comment;
import bg.softuni.pathfinder.model.Route;
import bg.softuni.pathfinder.model.User;
import bg.softuni.pathfinder.model.dto.CommentDto;
import bg.softuni.pathfinder.model.view.CommentView;
import bg.softuni.pathfinder.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private RouteService routeService;

    @Autowired
    public CommentService(CommentRepository commentRepository, RouteService routeService) {
        this.commentRepository = commentRepository;
        this.routeService = routeService;
    }

    public List<Comment> getCommentsByRoute(Long routeId) {
        return commentRepository.findAllByRoute(routeService.getRouteById(routeId));
    }

    public Comment createComment(CommentDto commentDto, Long routeId, User author) {
        Comment comment = new Comment();
        comment.setCreated(LocalDateTime.now());
        comment.setRoute(routeService.getRouteById(routeId));
        comment.setAuthor(author);
        comment.setText(commentDto.getText());
        comment.setApproved(true);

        commentRepository.save(comment);

        return comment;
    }

    public Comment getComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found!"));
    }

    public Comment deleteComment(Long id) {
        Comment comment = getComment(id);
        commentRepository.delete(comment);
        return comment;
    }
}
