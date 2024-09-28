package com.office.test.member;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestParam;





@Log4j2
@Controller
@RequestMapping("/member")
public class MemberController {

	final private MemberService memberService;
	
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
		
	}
	
	/*
	 *	Member 홈
	 */
	@GetMapping({"", "/"})
	public String home() {
		log.info("home()");
		
		String nextPage = "member/home";
		
		return nextPage;
		
	}
	
	/*
	 * 회원 가입 양식
	 */
	@GetMapping("/create_member_form")
	public String createMemberForm() {
		log.info("createMemberForm()");
		
		String nextPage = "member/create_member_form";
		
		return nextPage;
		
	}
	
	/*
	 * 회원 가입 확인
	 */
	@PostMapping("/create_member_confirm")
	public String createMemberConfirm(MemberDto memberDto) {
		log.info("createMemberForm()");
		
		String nextPage = "member/create_member_ok";
		
		int result = memberService.createMemberConfirm(memberDto);
		if (result <= 0)
			nextPage = "member/create_member_ng";
		
		return nextPage;
		
	}
	
	/*
	 * 회원 로그인 양식
	 */
	@GetMapping("/login_member_form")
	public String loginMemberForm() {
		log.info("loginMemberForm()");
		
		String nextPage = "member/login_member_form";
		
		return nextPage;
		
	}
	
	/*
	 * 접근 권한 실패 시
	 */
	@GetMapping("/access_denied")
	public String accessDenied() {
		log.info("loginMemberForm()");
		
		String nextPage = "member/access_denied";
		
		return nextPage;
		
	}
	//인증 없이 접근시
	@GetMapping("authentication_entry_point")
	public String authenticationEntryPoint() {
		log.info("authenticationEntryPoint()");

		String nextPage = "member/authentication_entry_point";

		return nextPage;
	}
	
	/*
	 * 회원 계정 수정 양식
	 */
	@GetMapping("/modify_member_form")
	public String modifyMemberForm(Principal principal, Model model) {
		log.info("modifyMemberForm()");
		
		String nextPage = "member/modify_member_form";
		
		MemberDto loginedMemberDto =
				memberService.getMemberDto(principal.getName());
		
		model.addAttribute("loginedMemberDto", loginedMemberDto);
		
		return nextPage;
		
	}
	
	/*
	 * 회원 계정 수정 확인
	 */
	@PostMapping("/modify_member_confirm")
	public String modifyMemberConfirm(MemberDto memberDto) {
		log.info("modifyMemberConfirm()");
		
		String nextPage = "member/modify_member_ok";
		
		int result = memberService.modifyMemberConfirm(memberDto);
		if (result <= 0)
			nextPage = "member/modify_member_ng";
		
		return nextPage;
		
	}
	
	/*
	 * 회원 계정 삭제 확인
	 */
	@GetMapping("/delete_member_confirm")
	public String deleteMemberConfirm(Principal principal) {
		log.info("deleteMemberConfirm()");
		
		int result = memberService.deleteMemberConfirm(principal.getName());
		if (result > 0) 
			return "redirect:/member/logout_member_confirm";
		else
			return "redirect:/member";
		
	}
	
}
