package bg.softuni.pathfinder.web.rest;

import bg.softuni.pathfinder.model.Comment;
import bg.softuni.pathfinder.model.User;
import bg.softuni.pathfinder.model.dto.CommentDto;
import bg.softuni.pathfinder.model.view.CommentView;
import bg.softuni.pathfinder.service.AuthService;
import bg.softuni.pathfinder.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static bg.softuni.pathfinder.model.enums.UserRoles.ADMIN;
import static bg.softuni.pathfinder.model.enums.UserRoles.MODERATOR;

@RestController
public class CommentRestController {
    private CommentService commentService;
    private AuthService authService;

    @Autowired
    public CommentRestController(CommentService commentService, AuthService authService) {
        this.commentService = commentService;
        this.authService = authService;
    }

    @GetMapping("/api/{routeId}/comments")
    public ResponseEntity<List<CommentView>> getCommentsRoutes(@PathVariable("routeId") Long routeId) {
        var comments = commentService.getCommentsByRoute(routeId)
                .stream().map(c -> mapToCommentView(c))
                .collect(Collectors.toList());

        return ResponseEntity.ok(comments);
    }

    private CommentView mapToCommentView(Comment c) {
        return new CommentView(c.getId(), c.getText(), c.getAuthor().getFullName(),
                c.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")));
    }

    @GetMapping("/api/{routeId}/comments/{commentId}")
    private ResponseEntity<CommentView> getComment(@PathVariable("commentId") Long commentId) {
        return ResponseEntity.ok(mapToCommentView(commentService.getComment(commentId)));
    }

    @PostMapping(value = "/api/{routeId}/comments", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommentView> createComment(@AuthenticationPrincipal UserDetails userDetails,
                                                     @RequestBody CommentDto commentDto,
                                                     @PathVariable("routeId") Long routeId) {
        Comment comment = commentService.createComment(commentDto,
                routeId, authService.getUserByUsername(userDetails.getUsername()));

        CommentView commentView = mapToCommentView(comment);

        return ResponseEntity.created(URI.create(String.format("/api/%s/comments/%d", routeId, comment.getId())))
                .body(commentView);
    }

    @DeleteMapping("/api/{routeId}/comments/{commentId}")
    public ResponseEntity<CommentView> deleteComment(@PathVariable("commentId") Long commentId,
                                                     @AuthenticationPrincipal UserDetails principal) {
        User user = authService.getUserByUsername(principal.getUsername());
        Comment comment = commentService.getComment(commentId);

        if(user.getRoles().stream().anyMatch(r -> r.getName() == MODERATOR || r.getName() == ADMIN)
            || user.getId() == comment.getAuthor().getId()) {
            Comment deleted = commentService.deleteComment(commentId);
            return ResponseEntity.ok(mapToCommentView(deleted));
        }
        return ResponseEntity.status(403).build();
    }
}
