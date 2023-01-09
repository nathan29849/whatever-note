package dev.whatevernote.be.repository;

import dev.whatevernote.be.service.domain.Content;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContentRepository extends JpaRepository<Content, Long> {

	Optional<Content> findFirstByOrderBySeq();

	List<Content> findAllByCardId(Long cardId);

	Slice<Content> findAllByCardIdOrderBySeq(Pageable pageable, Long cardId);

	@Modifying(clearAutomatically = true)
	@Query("update Content c SET c.deleted = TRUE WHERE c.card.id = :cardId")
	void deleteAll(@Param("cardId") Long cardId);
}
