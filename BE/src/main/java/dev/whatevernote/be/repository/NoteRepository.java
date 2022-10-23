package dev.whatevernote.be.repository;

import dev.whatevernote.be.service.domain.Note;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Integer> {

	Optional<Note> findFirstByOrderBySeq();

	List<Note> findAllByOrderBySeq();

	Slice<Note> findAllByOrderBySeq(Pageable pageable);

}
