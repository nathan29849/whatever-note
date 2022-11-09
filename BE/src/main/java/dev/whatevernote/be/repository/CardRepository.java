package dev.whatevernote.be.repository;

import dev.whatevernote.be.service.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

}
