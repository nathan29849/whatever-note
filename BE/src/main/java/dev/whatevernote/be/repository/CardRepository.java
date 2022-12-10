package dev.whatevernote.be.repository;

import dev.whatevernote.be.service.domain.Card;
import dev.whatevernote.be.service.domain.Note;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

	Optional<Card> findFirstByOrderBySeq();

	List<Card> findAllByOrderBySeq();

	Slice<Card> findAllByNoteOrderBySeq(Pageable pageable, Note note);

}
