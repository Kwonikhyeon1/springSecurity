package com.office.test.admin;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AdminDetailService implements UserDetailsService {

	final private AdminMapper adminMapper;
	
	public AdminDetailService(AdminMapper adminMapper) {
		this.adminMapper = adminMapper;
		
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("loadUserByUsername()");
		
		AdminDto selectedAdminDto =
				adminMapper.selectAdminByAId(username);
		
		if (selectedAdminDto != null)
			return new AdminDetails(selectedAdminDto);
		
		return null;
		
	}

}
