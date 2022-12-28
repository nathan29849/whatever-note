package dev.whatevernote.be.repository;

import dev.whatevernote.be.service.domain.Card;
import dev.whatevernote.be.service.domain.Note;
import dev.whatevernote.be.service.dto.request.CardRequestDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

	List<Card> findAllByOrderBySeq();

	Slice<Card> findAllByNoteOrderBySeq(Pageable pageable, Note note);

	List<Card> findAllByNoteId(Integer noteId);

	@Modifying(clearAutomatically = true)
	@Query("update Card c SET c.deleted = TRUE WHERE c.note.id = :note_id")
	void deleteAll(@Param("note_id") Integer noteId);
}
