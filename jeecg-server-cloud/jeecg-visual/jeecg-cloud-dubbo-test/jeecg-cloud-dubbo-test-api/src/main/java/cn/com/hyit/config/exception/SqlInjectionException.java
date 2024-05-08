package cn.com.hyit.config.exception;

/**
 * 定义SQL注入异常
 */
public class SqlInjectionException extends RuntimeException {
	private static final long serialVersionUID = 6407854850529610853L;

	public SqlInjectionException(String message){
		super(message);
	}

	public SqlInjectionException(Throwable cause)
	{
		super(cause);
	}

	public SqlInjectionException(String message, Throwable cause)
	{
		super(message,cause);
	}
}
