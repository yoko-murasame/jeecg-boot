package cn.com.hyit.config.exception;

/**
 * 自定义异常
 */
public class EstateException extends RuntimeException {
	private static final long serialVersionUID = 7726221350128302064L;

	public EstateException(String message){
		super(message);
	}

	public EstateException(Throwable cause)
	{
		super(cause);
	}

	public EstateException(String message, Throwable cause)
	{
		super(message,cause);
	}
}
