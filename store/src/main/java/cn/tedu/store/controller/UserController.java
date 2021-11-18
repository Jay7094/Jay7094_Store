package cn.tedu.store.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.tedu.store.controller.ex.FileEmptyException;
import cn.tedu.store.controller.ex.FileSizeException;
import cn.tedu.store.controller.ex.FileTypeException;
import cn.tedu.store.controller.ex.FileUploadStateException;
import cn.tedu.store.entity.User;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UsernameDuplicateException;
import cn.tedu.store.util.JsonResult;

/**
 * 處理用戶數據相關請求的控制器類
 * @author user
 *
 */
@RestController
@RequestMapping("users")
public class UserController extends BaseController{
	
	private static final long AVATAR_MAX_SIZE = 1 *1024 *1024;
	
	private static final List<String> AVATAR_CONTENT_TYPES = new ArrayList<>();
	
	static {
		AVATAR_CONTENT_TYPES.add("image/jpeg");
		AVATAR_CONTENT_TYPES.add("image/png");
	}
	
	//添加業務層對象
	@Autowired
	private IUserService userService;
	
	@RequestMapping("reg")
	public JsonResult<Void> reg(User user){	
		//執行註冊
		userService.reg(user);
		//返回成功
//		JsonResult<Void> jr = new JsonResult<Void>();
//		jr.setState(1);
// 		或是利用構造方法寫在return中		
		return new JsonResult<Void>(SUCCESS);
	}
	
	@RequestMapping("login")
	public JsonResult<User> login(
		String username, String password, HttpSession session){
		//執行登錄
		User user = userService.login(username, password);
		//封裝數據
		session.setAttribute("uid", user.getUid());
		session.setAttribute("username", user.getUsername());
		//響應操作成功
		return new JsonResult<>(SUCCESS, user);
	}
	
	@RequestMapping("change_password")
	public JsonResult<Void> changePassword(
			@RequestParam("old_password") String oldPassword,
			@RequestParam("new_password") String newPassword,
			HttpSession session){
		//從session中獲取uid跟username
		Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
		String username = session.getAttribute("username").toString();
		//執行修改密碼
		userService.changePassword(uid, username, oldPassword, newPassword);
		//響應修改成功
		return new JsonResult<>(SUCCESS);
	}
	
	@GetMapping("get_info")
	public JsonResult<User> getByUid(HttpSession session){
		//從seesion中獲取uid
		Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
		//查詢匹配的數據
		User data = userService.getByUid(uid);
		return new JsonResult<>(SUCCESS, data);
	}
	
	@RequestMapping("change_info")
	public JsonResult<Void> changeInfo(User user, HttpSession session){
		//Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
		Integer uid = getUidFromSession(session);
		//String username = session.getAttribute("username").toString();
		String username = getUsernameFromSession(session);
		//封裝到user中
		user.setUid(uid);
		user.setUsername(username);
		//執行修改
		userService.changeInfo(user);
		return new JsonResult<>(SUCCESS);
	}
	
	@PostMapping("change_avatar")
	public JsonResult<String> changeAvatar(
			HttpServletRequest request, 
			@RequestParam("file") MultipartFile file){
		//檢查文件是否為空
		if(file.isEmpty()){
			throw new FileEmptyException("請選擇有效的文件!!!");
		}
		//檢查文件大小
		if(file.getSize() > AVATAR_MAX_SIZE){
			throw new FileSizeException("上傳失敗 不允許使用超過"+(AVATAR_MAX_SIZE/1024)+"KB的文件");
		}
		//檢查文件類型
		if (!AVATAR_CONTENT_TYPES.contains(file.getContentType())) {
			throw new FileTypeException("上傳失敗!僅允許"+AVATAR_CONTENT_TYPES);
		}
		//確定文件夾
		String dirPath = request.getServletContext().getRealPath("upload");
		File dir = new File(dirPath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		//文件名
		String originalFilename = file.getOriginalFilename();
		String suffix = "";
		int beginIndex = originalFilename.lastIndexOf(".");
		if(beginIndex != -1){
			suffix = originalFilename.substring(beginIndex);
		}
		String filename = UUID.randomUUID().toString() + suffix;
		//執行保存
		File dest = new File(dir, filename);
		try {
			file.transferTo(dest);
		} catch (IllegalStateException e) {
			throw new FileUploadStateException(
					"上傳失敗!請檢查源文件是否存在");
		} catch (IOException e) {
			throw new FileUploadStateException(
					"上傳失敗!讀出數據時出現未知錯誤");
		}
		//更新數據表
		String avatar = "/upload/" + filename;
		HttpSession session = request.getSession();
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		userService.changeAvatar(uid, username, avatar);
		//返回
		JsonResult<String> jr = new JsonResult<String>();
		jr.setState(SUCCESS);
		jr.setData(avatar);
		return jr;
	}
}












