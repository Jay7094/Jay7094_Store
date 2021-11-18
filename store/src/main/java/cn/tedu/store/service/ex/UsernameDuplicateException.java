package cn.tedu.store.service.ex;
/**
 * 用戶名衝突異常 例如已被使用
 * @author user
 *
 */
public class UsernameDuplicateException extends ServiceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsernameDuplicateException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UsernameDuplicateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public UsernameDuplicateException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public UsernameDuplicateException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public UsernameDuplicateException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
