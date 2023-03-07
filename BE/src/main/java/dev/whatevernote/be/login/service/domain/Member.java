package dev.whatevernote.be.login.service.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String uniqueId;
	private String nickname;
	private String email;
	private String profileImage;

	public Member(String uniqueId, String nickname, String email, String profileImage) {
		this.uniqueId = uniqueId;
		this.nickname = nickname;
		this.email = email;
		this.profileImage = profileImage;
	}
}
