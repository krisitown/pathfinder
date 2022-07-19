package bg.softuni.pathfinder.repository;

import bg.softuni.pathfinder.model.Comment;
import bg.softuni.pathfinder.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByRoute(Route route);
}
