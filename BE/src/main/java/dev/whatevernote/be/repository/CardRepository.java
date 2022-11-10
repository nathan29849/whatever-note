package dev.whatevernote.be.repository;

import dev.whatevernote.be.service.domain.Card;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

	Optional<Card> findFirstByOrderBySeq();

	List<Card> findAllByOrderBySeq();

}
