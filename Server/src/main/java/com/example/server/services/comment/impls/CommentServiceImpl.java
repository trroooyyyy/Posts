package com.example.server.services.comment.impls;

import com.example.server.dto.CommentDTO;
import com.example.server.dto.CommentSaveDTO;
import com.example.server.mappers.CommentMapper;
import com.example.server.model.Comment;
import com.example.server.repository.CommentRepository;
import com.example.server.services.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository repository;
    @Autowired
    CommentMapper mapper;

    @Override
    public CommentSaveDTO save(CommentSaveDTO comment) {
        if(comment.getId()!=null){
            return null;
        }
        comment.setCreatedAt(new Date());
        return mapper.toCommentSaveDTO(repository.save(mapper.toEntity(comment)));
    }

    @Override
    @PreAuthorize("@authComponent.hasPermissionComment(#id) or hasRole('ROLE_Admin')")
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    public CommentDTO getOne(int id) {
        return repository.findById(id).map(mapper::toDTO).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<CommentDTO> getAll() {
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("@authComponent.hasPermissionComment(#id) or hasRole('ROLE_Admin')")
    public CommentSaveDTO update(CommentSaveDTO comment) {
        Comment comment1 = repository.findById(comment.getId()).orElseThrow(NoSuchElementException::new);
        comment.setCreatedAt(comment1.getCreatedAt());
        return mapper.toCommentSaveDTO(repository.save(mapper.toEntity(comment1, comment)));
    }
}
