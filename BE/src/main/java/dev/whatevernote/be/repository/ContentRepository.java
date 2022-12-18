package dev.whatevernote.be.repository;

import dev.whatevernote.be.service.domain.Content;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {

	List<Content> findAllByCardId(Long cardId);
}
