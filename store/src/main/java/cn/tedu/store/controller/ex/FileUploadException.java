package cn.tedu.store.controller.ex;

/**
 * 上傳過程異常的基類
 * @author user
 *
 */
public class FileUploadException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileUploadException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FileUploadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public FileUploadException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public FileUploadException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public FileUploadException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
