package cn.com.hyit.config.exception;

/**
 * 自定义401异常
 */
public class Estate401Exception extends RuntimeException {
	private static final long serialVersionUID = 7243086455700419624L;

	public Estate401Exception(String message){
		super(message);
	}

	public Estate401Exception(Throwable cause)
	{
		super(cause);
	}

	public Estate401Exception(String message, Throwable cause)
	{
		super(message,cause);
	}
}
