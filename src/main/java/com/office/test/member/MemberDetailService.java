package com.office.test.member;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MemberDetailService implements UserDetailsService {

	final private MemberMapper memberMapper;
	
	public MemberDetailService(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
		
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("loadUserByUsername()");
		MemberDto selectedMemberDto = memberMapper.selectMemberByMId(username);
		
		if (selectedMemberDto != null)
			return new MemberDetails(selectedMemberDto);
		
		return null;
		
	}

}
