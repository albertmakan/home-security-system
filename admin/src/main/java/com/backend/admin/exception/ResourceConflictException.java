package com.backend.admin.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
@Getter @Setter
public class ResourceConflictException extends RuntimeException {
	private String resourceId;

	public ResourceConflictException(String resourceId, String message) {
		super(message);
		setResourceId(resourceId);
	}
}
