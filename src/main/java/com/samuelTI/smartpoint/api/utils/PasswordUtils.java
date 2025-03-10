package com.samuelTI.smartpoint.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordUtils {

	private static final Logger log = LoggerFactory.getLogger(PasswordUtils.class);

	public PasswordUtils() {
		
	}
	
	/*
	 * Gera um hash utilizando o BCrypt
	 * 
	 * @param senha
	 * @return String
	 */
	
	public static String gerarByCrypt(String senha) {
		if(senha == null) {
			return senha;
		}
		log.info("Generating hash with BCrypt");
		//BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
		return senha;
	}
}
