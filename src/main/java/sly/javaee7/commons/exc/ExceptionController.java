package sly.javaee7.commons.exc;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExceptionController {

	public RuntimeException wrappedException(String msg, Exception e) {
		return new AppException(msg, e);
	}
}
