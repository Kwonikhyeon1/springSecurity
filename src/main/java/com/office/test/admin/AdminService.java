package com.office.test.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.office.test.member.AuthorityDto;
import com.office.test.member.MemberDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AdminService {

	final private PasswordEncoder passwordEncoder;
	final private AdminMapper adminMapper;
	
	public AdminService(AdminMapper adminMapper, PasswordEncoder passwordEncoder) {
		this.adminMapper = adminMapper;
		this.passwordEncoder = passwordEncoder;
		
	}

	public int createAdminConfirm(AdminDto adminDto) {
		log.info("createAdminConfirm()");
		
		adminDto.setA_pw(passwordEncoder.encode(adminDto.getA_pw()));
		
		return adminMapper.insertNewAdmin(adminDto);
		
	}

	public AdminDto getAdminDto(String a_id) {
		log.info("getAdminDto()");
		
		return adminMapper.selectAdminByAId(a_id);
		
	}

	public int modifyAdminConfirm(AdminDto adminDto) {
		log.info("modifyAdminConfirm()");
		
		adminDto.setA_pw(passwordEncoder.encode(adminDto.getA_pw()));
		
		return adminMapper.updateAdminByANo(adminDto);
		
	}

	public List<MemberDto> memberList() {
		log.info("memberList()");
		
		return adminMapper.getMembers();
		
	}

	public List<AuthorityDto> getMemberAuthority() {
		log.info("getMemberAuthority()");
		
		return adminMapper.getMemberAuthority();
		
	}

	public Map<String, Object> updateMemberAuthority(Map<String, String> msg) {
		log.info("updateMemberAuthority()");
		
		Map<String, Object> resultMap = new HashMap<>();
		
		int result = adminMapper.updateMemberAuthority(msg);
		resultMap.put("result", result);
		
		return resultMap;
		
	}
	
}
