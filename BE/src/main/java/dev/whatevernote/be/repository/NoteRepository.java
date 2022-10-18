package dev.whatevernote.be.repository;

import dev.whatevernote.be.service.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {

}
