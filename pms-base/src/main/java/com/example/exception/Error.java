
package com.example.exception;


import com.example.model.Code;

/**
 * 通用错误信息
 * @author GLaDOS
 * @version 1.0
 */
public enum Error implements Code{

	FAILED(111),

	UNKNOWN_FAILED(222),

	UNAUTHORIZED(401),
	TOKEN_LOAD_FAILED(777),

	NO_SUCH_USER(999),
	WRONG_PASSWORD(988),
	NO_AUTH(977),
	SAME_USERNAME(966),

	DATABASE_INSERT_FAILED(666),
	DATABASE_DELETE_FAILED(655),
	DATABASE_UPDATE_FAILED(644),
	DATABASE_SELECT_FAILED(633),

	UPLOAD_FILE_FAILED(303);

	private final Integer code;

	public Integer getCode() {
		return code;
	}

	private Error(Integer code) {
		this.code = code;
	}

}
