package ru.ktelabs.clinic.mapper.comment;

import ru.ktelabs.clinic.dto.comment.CommentResponseDto;
import ru.ktelabs.clinic.model.comment.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface CommentMapper {

    @Mapping(target = "doctorId", source = "comment.doctor.id")
    @Mapping(target = "patientId", source = "comment.patient.id")
    CommentResponseDto toCommentResponseDto(Comment comment);

    List<CommentResponseDto> toCommentResponseDtos(List<Comment> comments);

}
