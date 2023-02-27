package dev.whatevernote.be.login.repository;

import dev.whatevernote.be.login.service.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByUniqueId(String uniqueId);
}
