package com.hwjava.springbootmybatisplus.code;

public enum CodeEnum {
	/*
	 * 枚举的错误代码
	 * */
	ERROR_400(400, "请求处理异常，请等待研发哥哥处理..."),
	ERROR__500(500, "内部错误，是否空指针？,请检查"),
	ERROR__404(404, "资源不存在"),
	ERROR__502(502, "权限不足"),
	ERROR__10000(10000, "账户已存在"),
	ERROR__000(000, "Token已过期,请重新登陆"),
    ERROR_10001(10001,"获取信息失败！");
	private int errorCode;
	private String errorMsg;

	CodeEnum(int errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

}