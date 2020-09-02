package com.hwjava.springbootmybatisplus.controller;

import com.hwjava.springbootmybatisplus.code.CodeEnum;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class PageErrorController implements ErrorController {

	private static final String ERROR_PATH = "/error";

	/*
	 * 404错误
	 */
	@RequestMapping(value = ERROR_PATH)
	@ResponseBody
	public CodeEnum handleError() {
		return CodeEnum.ERROR__404;
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}
}

