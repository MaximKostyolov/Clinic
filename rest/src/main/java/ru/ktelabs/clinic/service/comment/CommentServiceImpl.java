package ru.ktelabs.clinic.service.comment;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import ru.ktelabs.clinic.dto.comment.CommentParams;
import ru.ktelabs.clinic.exception.AccesErrorException;
import ru.ktelabs.clinic.exception.NotFoundException;
import ru.ktelabs.clinic.service.patient.PatientService;
import ru.ktelabs.clinic.dto.comment.CommentUpdateDto;
import ru.ktelabs.clinic.dto.comment.NewCommentDto;
import ru.ktelabs.clinic.model.comment.Comment;
import ru.ktelabs.clinic.model.comment.Status;
import ru.ktelabs.clinic.model.doctor.Doctor;
import ru.ktelabs.clinic.model.comment.QComment;
import ru.ktelabs.clinic.model.patient.Patient;
import ru.ktelabs.clinic.repository.comment.CommentRepository;
import ru.ktelabs.clinic.service.doctor.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private static final QComment Q_COMMENT = QComment.comment;

    private final CommentRepository commentRepository;

    private final DoctorService doctorService;

    private final PatientService patientService;

    @Override
    public Comment create(Long patientId, NewCommentDto newCommentDto) {
        Patient patient = patientService.getById(patientId);
        Doctor doctor = doctorService.getById(newCommentDto.getDoctorId());
        Comment comment = new Comment();
        comment.setText(newCommentDto.getText());
        comment.setGrade(newCommentDto.getGrade());
        comment.setDoctor(doctor);
        comment.setPatient(patient);
        comment.setStatus(Status.WAITING);
        comment.setCreated(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Long patientId, Long id, CommentUpdateDto commentUpdateDto) {
        Comment comment = getById(id);
        if (comment.getPatient().getId() == patientId) {
            if (comment.getStatus().equals(Status.WAITING) || (comment.getStatus().equals(Status.PUBLISHED))) {
                updateComment(comment, commentUpdateDto);
                return commentRepository.save(comment);
            } else {
                throw new AccesErrorException("Couldn't update comment. The comment rejected from Admin or deleted");
            }
        } else {
            throw new AccesErrorException("Couldn't update comment. The comment created other user");
        }
    }

    @Override
    public Comment getById(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Comment with id = " + id + " was not found"));
    }

    @Override
    public void deleteById(Long patientId, Long id) {
        Comment comment = getById(id);
        if (comment.getPatient().getId() == patientId) {
            if (comment.getStatus().equals(Status.WAITING) || (comment.getStatus().equals(Status.PUBLISHED))) {
                comment.setStatus(Status.DELETED);
                commentRepository.save(comment);
            } else {
                throw new AccesErrorException("Couldn't delete comment. The comment rejected from Admin or already deleted");
            }
        } else {
            throw new AccesErrorException("Couldn't delete comment. The comment was created other user");
        }
    }

    @Override
    public List<Comment> getAll(CommentParams params, int page, int size) {
        Predicate p = buildQCommentPredicateByParams(params);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return commentRepository.findAll(p, pageable).toList();
    }

    @Override
    public Comment updateFromAdmin(Long id, Status status) {
        Comment comment = getById(id);
        if (comment.getStatus().equals(Status.WAITING)) {
            if ((status.equals(Status.PUBLISHED)) || (status.equals(Status.REJECTED))) {
                comment.setStatus(status);
                return commentRepository.save(comment);
            } else {
                throw new AccesErrorException("Wrong status. Status can be PUBLISHED or REJECTED");
            }
        } else {
            throw new AccesErrorException("Comment was already viewed or deleted from patient");
        }
    }

    private void updateComment(Comment comment, CommentUpdateDto commentUpdateDto) {
        if (commentUpdateDto.getText() != null) {
            comment.setText(commentUpdateDto.getText());
        }
        if (commentUpdateDto.getGrade() != null) {
            comment.setGrade(commentUpdateDto.getGrade());
        }
    }

    private Predicate buildQCommentPredicateByParams(CommentParams params) {
        BooleanBuilder builder = new BooleanBuilder();
        if (params.getText() != null) {
            builder.and(Q_COMMENT.text.like(params.getText()));
        }
        if (params.getGrade() != null) {
            builder.and(Q_COMMENT.grade.goe(params.getGrade()));
        }
        if (params.getDoctorId() != null) {
            builder.and(Q_COMMENT.doctor.id.eq(params.getDoctorId()));
        }
        if (params.getPatientId() != null) {
            builder.and(Q_COMMENT.patient.id.eq(params.getPatientId()));
        }
        if (params.getStatus() != null) {
            builder.and(Q_COMMENT.status.eq(params.getStatus()));
        }
        return builder;
    }

}
