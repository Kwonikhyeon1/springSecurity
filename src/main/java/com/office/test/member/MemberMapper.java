package com.office.test.member;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

	int insertNewMember(MemberDto memberDto);

	MemberDto selectMemberByMId(String username);

	int updateMemberByMNo(MemberDto memberDto);

	int deleteMemberByMId(String m_id);

}
