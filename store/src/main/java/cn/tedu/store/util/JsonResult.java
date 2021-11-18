package cn.tedu.store.util;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 向客戶端響應操作結果的數據類型
 * @param <T> 向客戶端響應的數據的類型
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class JsonResult<T> {
	
	private Integer state;
	private String message;
	private T data;
	
	
	
	public JsonResult(Integer state, T data) {
		super();
		this.state = state;
		this.data = data;
	}

	//要添加無參構造方法否則BaseController中的
	//JsonResult<Void> jr = new JsonResult<>();會報錯誤
	public JsonResult() {
		super();
	}
	
	//設計給UserController中return方便使用
	public JsonResult(Integer state) {
		super();
		this.state = state;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
}
