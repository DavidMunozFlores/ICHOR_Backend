package com.erguidos.ichor.component;

public interface HashInterface {
	String hashPassword(String p);
	boolean matchPasswords(String plaintext, String hashed);
}
