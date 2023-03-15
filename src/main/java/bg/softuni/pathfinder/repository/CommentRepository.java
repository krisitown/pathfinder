package bg.softuni.pathfinder.repository;

import bg.softuni.pathfinder.model.Comment;
import bg.softuni.pathfinder.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByRoute(Route route);
}
