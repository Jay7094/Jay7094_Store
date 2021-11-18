package cn.tedu.store.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ExceptionHandler;

import cn.tedu.store.controller.ex.FileEmptyException;
import cn.tedu.store.controller.ex.FileSizeException;
import cn.tedu.store.controller.ex.FileTypeException;
import cn.tedu.store.controller.ex.FileUploadException;
import cn.tedu.store.controller.ex.FileUploadIOException;
import cn.tedu.store.controller.ex.FileUploadStateException;
import cn.tedu.store.service.ex.AddressCountLimitException;
import cn.tedu.store.service.ex.CartNotFoundException;
import cn.tedu.store.service.ex.DeleteException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameDuplicateException;
import cn.tedu.store.util.JsonResult;

/**
 * 控制器類的基類
 * @author user
 * 
 */
public abstract class BaseController {
	
	//應該取方便讀取的變量名字更好表示代碼的意義
	//static別人使用時就不用new 用類名點就可以
	//操作結果的成功狀態
	public static final Integer SUCCESS = 2000;
	
	protected final Integer getUidFromSession(HttpSession session){
		return Integer.valueOf(session.getAttribute("uid").toString());
	}
	
	protected final String getUsernameFromSession(HttpSession session){
		return session.getAttribute("username").toString();
	}
	
	@ExceptionHandler({ServiceException.class,FileUploadException.class})
	public JsonResult<Void> handleException(Throwable e){
		JsonResult<Void> jr = new JsonResult<>();
		jr.setMessage(e.getMessage());
		
		if(e instanceof UsernameDuplicateException){
			//用戶名衝突
			jr.setState(4000);
		}else if(e instanceof UserNotFoundException){
			jr.setState(4001);
		}else if(e instanceof PasswordNotMatchException){
			jr.setState(4002);
		}else if(e instanceof AddressCountLimitException){
			jr.setState(4003);
		}else if(e instanceof CartNotFoundException){
			jr.setState(4006);
		}else if(e instanceof InsertException){
			jr.setState(5000);
		}else if(e instanceof UpdateException){
			jr.setState(5001);
		}else if(e instanceof DeleteException){
			jr.setState(5002);
		}else if(e instanceof FileEmptyException){
			jr.setState(6000);
		}else if(e instanceof FileSizeException){
			jr.setState(6001);
		}else if(e instanceof FileTypeException){
			jr.setState(6002);
		}else if(e instanceof FileUploadStateException){
			jr.setState(6003);
		}else if(e instanceof FileUploadIOException){
			jr.setState(6004);
		}
		
		return jr;
	}
}
