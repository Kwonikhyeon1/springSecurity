package com.office.test.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.office.test.member.AuthorityDto;
import com.office.test.member.MemberDto;

@Mapper
public interface AdminMapper {

	AdminDto selectAdminByAId(String username);

	int insertNewAdmin(AdminDto adminDto);

	int updateAdminByANo(AdminDto adminDto);

	List<MemberDto> getMembers();

	List<AuthorityDto> getMemberAuthority();

	int updateMemberAuthority(Map<String, String> msg);

}
