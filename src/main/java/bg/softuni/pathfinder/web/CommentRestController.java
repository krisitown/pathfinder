package bg.softuni.pathfinder.web;

import bg.softuni.pathfinder.model.Comment;
import bg.softuni.pathfinder.model.dto.CommentDto;
import bg.softuni.pathfinder.model.dto.CommentServiceDto;
import bg.softuni.pathfinder.model.views.CommentView;
import bg.softuni.pathfinder.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
public class CommentRestController {
    private CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/api/{routeId}/comments")
    public ResponseEntity<List<CommentView>> getComments(@PathVariable("routeId") Long routeId) {
        return ResponseEntity.ok(commentService.getCommentsByRoute(routeId));
    }

    @PostMapping(value = "/api/{routeId}/comments", consumes = "application/json")
    public ResponseEntity<CommentView> addComment(@PathVariable("routeId") Long routeId,
                                                  @AuthenticationPrincipal UserDetails principal,
                                                  @RequestBody CommentDto commentDto) {

        CommentServiceDto commentServiceDto = new CommentServiceDto(
                commentDto.getMessage(),
                principal.getUsername(),
                routeId
        );

        CommentView commentView = commentService.createComment(commentServiceDto);

        return ResponseEntity.ok(commentView);
    }
}
