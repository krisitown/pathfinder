package bg.softuni.pathfinder.repository;

import bg.softuni.pathfinder.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
