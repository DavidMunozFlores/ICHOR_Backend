package com.erguidos.ichor.component;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("bcrypt")
public class HashBcrypt implements HashInterface {
	private static final int SALT = 12;
	
	@Override
	public String hashPassword(String p) {
		return BCrypt.hashpw(p, BCrypt.gensalt(SALT));
	}

	@Override
	public boolean matchPasswords(String plaintext, String hashed) {
		return BCrypt.checkpw(plaintext, hashed);
	}
}
