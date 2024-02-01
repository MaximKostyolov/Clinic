package ru.ktelabs.clinic.repository.comment;

import ru.ktelabs.clinic.model.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository  extends JpaRepository<Comment, Long>, QuerydslPredicateExecutor<Comment> {
}
