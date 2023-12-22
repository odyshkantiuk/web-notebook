package com.webnote.webnotebook.dao;

import com.webnote.webnotebook.dao.entity.Note;
import com.webnote.webnotebook.dao.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NoteDao extends CrudRepository<Note, Integer> {
    List<Note> findAllByAuthor(User user);

    List<Note> findByTitleContaining(String title);

    @Query("SELECT n FROM Note n WHERE n.author.id = :userId")
    List<Note> findAllByUserId(int userId);
}
