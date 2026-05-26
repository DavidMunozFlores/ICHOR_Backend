package com.erguidos.ichor.component;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Qualifier("bcryptPasswordEncoder")
public class HashBcryptPasswordEncoder implements HashInterface {
	private final static int STRENGTH = 12;
	
	private BCryptPasswordEncoder pEncoder = new BCryptPasswordEncoder(STRENGTH);
	
	@Override
	public String hashPassword(String p) {
		return pEncoder.encode(p);
	}

	@Override
	public boolean matchPasswords(String plaintext, String hashed) {
		return pEncoder.matches(plaintext, hashed);
	}
}
