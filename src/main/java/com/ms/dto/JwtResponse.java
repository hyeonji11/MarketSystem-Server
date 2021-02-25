package com.ms.dto;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private int userIdx;
	private String id;
	private String name;

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public JwtResponse(String jwttoken, int userIdx, String id, String name) {
		this.jwttoken = jwttoken;
		this.userIdx = userIdx;
		this.id = id;
		this.name = name;
	}

	public String getJwtToken() {
		return this.jwttoken;
	}

	public int getUserIdx() {
		return this.userIdx;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}
}
