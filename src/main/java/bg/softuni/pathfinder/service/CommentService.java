package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.model.Comment;
import bg.softuni.pathfinder.model.User;
import bg.softuni.pathfinder.model.dto.CommentServiceDto;
import bg.softuni.pathfinder.model.views.CommentView;
import bg.softuni.pathfinder.repository.CommentRepository;
import bg.softuni.pathfinder.repository.RouteRepository;
import bg.softuni.pathfinder.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private RouteRepository routeRepository;

    private UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, RouteRepository routeRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.routeRepository = routeRepository;
        this.userRepository = userRepository;
    }

    public List<CommentView> getCommentsByRoute(Long routeId) {
        List<Comment> comments = routeRepository.findById(routeId)
                .map(route -> commentRepository.findAllByRoute(route))
                .orElse(new ArrayList<>());

        return comments.stream()
                .map(comment -> new CommentView(comment.getText(), comment.getAuthor().getFullName()))
                .collect(Collectors.toList());
    }

    public CommentView createComment(CommentServiceDto commentServiceDto) {
        Comment comment = new Comment();
        comment.setText(commentServiceDto.getMessage());
        comment.setAuthor(userRepository.findByUsername(commentServiceDto.getUserName()).orElseThrow());
        comment.setCreated(LocalDateTime.now());
        comment.setApproved(true);
        comment.setRoute(routeRepository.getById(commentServiceDto.getRouteId()));

        commentRepository.save(comment);

        return new CommentView(comment.getText(), comment.getAuthor().getFullName());
    }
}
