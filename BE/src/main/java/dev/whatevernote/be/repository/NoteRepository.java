package dev.whatevernote.be.repository;

import dev.whatevernote.be.service.domain.Note;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoteRepository extends JpaRepository<Note, Integer> {


//	@Query(value = "select n from Note n where n.member.id = :memberId order by n.seq limit 1")
//	Optional<Note> findFirstByOrderBySeq(Long memberId);

	Optional<Note> findFirstByMemberIdOrderBySeqAsc(Long memberId);

	List<Note> findAllByMemberIdOrderBySeq(Long memberId);

	Slice<Note> findAllByMemberIdOrderBySeq(Long memberId, Pageable pageable);
}
