package dev.whatevernote.be.repository;

import dev.whatevernote.be.service.domain.Note;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {

	Optional<Note> findFirstByOrderBySeq();

	List<Note> findAllByOrderBySeq();
}
