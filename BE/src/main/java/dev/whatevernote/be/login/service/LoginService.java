package dev.whatevernote.be.login.service;

import dev.whatevernote.be.exception.not_found.NotFoundMemberException;
import dev.whatevernote.be.login.repository.MemberRepository;
import dev.whatevernote.be.login.service.domain.Member;
import dev.whatevernote.be.login.service.dto.response.KakaoAccountResponse;
import dev.whatevernote.be.login.service.dto.response.LoginResponse;
import dev.whatevernote.be.login.service.provider.JwtProvider;
import dev.whatevernote.be.login.service.provider.KakaoProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class LoginService {

	private final JwtProvider jwtProvider;
	private final KakaoProvider kakaoProvider;
	private final MemberRepository memberRepository;

	public LoginService(JwtProvider jwtProvider, KakaoProvider kakaoProvider, MemberRepository memberRepository) {
		this.jwtProvider = jwtProvider;
		this.kakaoProvider = kakaoProvider;
		this.memberRepository = memberRepository;
	}

	@Transactional
	public LoginResponse login(final String code) {
		KakaoAccountResponse memberInfo = kakaoProvider.getMemberInformation(code);
		Member member = memberRepository.findByUniqueId(memberInfo.getUniqueId())
			.orElseGet(() -> memberRepository.save(new Member(
				memberInfo.getUniqueId(),
				memberInfo.getNickname(),
				memberInfo.getEmail(),
				memberInfo.getProfileImage()
			)));

		return new LoginResponse(
			member.getId(),
			member.getNickname(),
			member.getProfileImage(),
			member.getEmail(),
			jwtProvider.generateAccessToken(member.getId()),
			jwtProvider.generateRefreshToken(member.getId())
		);
	}

	public String reIssueAccessToken(final Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(NotFoundMemberException::new);
		return jwtProvider.generateAccessToken(member.getId());
	}
}
