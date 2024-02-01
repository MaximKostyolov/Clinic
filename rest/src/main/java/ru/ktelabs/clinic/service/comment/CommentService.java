package ru.ktelabs.clinic.service.comment;

import ru.ktelabs.clinic.dto.comment.CommentParams;
import ru.ktelabs.clinic.dto.comment.CommentUpdateDto;
import ru.ktelabs.clinic.dto.comment.NewCommentDto;
import ru.ktelabs.clinic.model.comment.Comment;
import ru.ktelabs.clinic.model.comment.Status;

import java.util.List;

public interface CommentService {

    Comment create(Long patientId, NewCommentDto newCommentDto);

    Comment update(Long patientId, Long id, CommentUpdateDto commentUpdateDto);

    Comment getById(Long id);

    void deleteById(Long patientId, Long id);

    List<Comment> getAll(CommentParams params, int page, int size);

    Comment updateFromAdmin(Long id, Status status);

}
