package com.office.test.admin;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.office.test.member.AuthorityDto;
import com.office.test.member.MemberDto;

import lombok.extern.log4j.Log4j2;




@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminController {

	final private AdminService adminService;
	
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
		
	}
	
	/*
	 * 관리자 홈
	 */
	@GetMapping({"", "/"})
	public String home() {
		log.info("home()");
		
		String nextPage = "admin/home";
		
		return nextPage;
		
	}
	
	/*
	 * 관리자 회원 가입 양식
	 */
	@GetMapping("/create_admin_form")
	public String createAdminForm() {
		log.info("createAdminForm");
		
		String nextPage = "admin/create_admin_form";
		
		return nextPage;
		
	}
	
	/*
	 * 관리자 회원 가입 확인
	 */
	@PostMapping("/create_admin_confirm")
	public String createAdminConfirm(AdminDto adminDto) {
		log.info("createAdminConfirm()");
		
		String nextPage = "admin/create_admin_ok";
		
		int result = adminService.createAdminConfirm(adminDto);
		if (result <= 0)
			nextPage = "admin/create_admin_ng";
		
		return nextPage;
		
	}
	
	/*
	 * 관리자 회원 로그인 양식
	 */
	@GetMapping("/login_admin_form")
	public String loginAdminForm(AdminDto adminDto) {
		log.info("loginAdminForm");
		
		String nextPage = "admin/login_admin_form";
		
		return nextPage;
		
	}
	
	/*
	 * 권한 없는 경우
	 */
	@GetMapping("/access_denied")
	public String accessDenied() {
		log.info("accessDenied()");
		
		String nextPage = "admin/access_denied";
		
		return nextPage;
		
	}

	//인증 없이 접근시
	@GetMapping("authentication_entry_point")
	public String authenticationEntryPoint() {
		log.info("authenticationEntryPoint()");

		String nextPage = "admin/authentication_entry_point";

		return nextPage;
	}

	/*
	 * 관리자 계정 수정 양식
	 */
	@GetMapping("/modify_admin_form")
	public String modifyAdminForm(Principal principal, Model model) {

		log.info("modifyAdminForm()");

		String nextPage = "admin/modify_admin_form";

		AdminDto loginedAdminDto =
				adminService.getAdminDto(principal.getName());

		model.addAttribute("loginedAdminDto", loginedAdminDto);

		return nextPage;

	}
	
	/*
	 * 관리자 계정 수정 확인
	 */
	@PostMapping("/modify_admin_confirm")
	public String modifyAdminConfirm(AdminDto adminDto) {
		log.info("modifyAdminConfirm()");
		
		String nextPage = "admin/modify_admin_ok";
		
		int result = adminService.modifyAdminConfirm(adminDto);
		if (result <= 0)
			nextPage = "admin/modify_admin_ng";
		
		return nextPage;
		
	}
	
	/*
	 * 회원 리스트 관리
	 */
	@GetMapping("/member_list")
	public String memberList(Model model) {
		log.info("memberList()");
		
		String nextPage = "admin/member_list";
		
		List<MemberDto> memberDtos = adminService.memberList();
		model.addAttribute("memberDtos", memberDtos);
		
		List<AuthorityDto> authorityDtos = adminService.getMemberAuthority();
		model.addAttribute("authorityDtos", authorityDtos);
		
		return nextPage;
		
	}
	
	/*
	 * 회원 권한 업데이트
	 */
	@PutMapping("/updateMemberAuthority")
	@ResponseBody
	public Object updateMemberAuthority(@RequestBody Map<String, String> msg) {
		log.info("updateMemberAuthority()");
		
		Map<String, Object> resultMap =  adminService.updateMemberAuthority(msg);
		
		return resultMap;
		
	}
	
}
