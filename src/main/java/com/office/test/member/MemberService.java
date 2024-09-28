package com.office.test.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MemberService {

	final private MemberMapper memberMapper;
	final private PasswordEncoder passwordEncoder;
	
	public MemberService(MemberMapper memberMapper, PasswordEncoder passwordEncoder) {
		this.memberMapper = memberMapper;
		this.passwordEncoder = passwordEncoder;
		
	}

	public int createMemberConfirm(MemberDto memberDto) {
		log.info("createMemberConfirm()");
		
		memberDto.setM_pw(passwordEncoder.encode(memberDto.getM_pw()));
		
		return memberMapper.insertNewMember(memberDto);
		
	}

	public MemberDto getMemberDto(String m_id) {
		log.info("getMemberDto()");
		
		return memberMapper.selectMemberByMId(m_id);
		
	}

	public int modifyMemberConfirm(MemberDto memberDto) {
		log.info("modifyMemberConfirm()");
		
		memberDto.setM_pw(passwordEncoder.encode(memberDto.getM_pw()));
		
		return memberMapper.updateMemberByMNo(memberDto);
		
	}

	public int deleteMemberConfirm(String m_id) {
		log.info("deleteMemberConfirm()");
		
		return memberMapper.deleteMemberByMId(m_id);
		
	}
	
}
