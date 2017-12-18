//package com.zxsm.wsc.common.Controllers;
//
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.boot.autoconfigure.web.ErrorController;
//
//@RestController
//public class DjExceptionHandler implements ErrorController
//{
//	@Override
//	public String getErrorPath() {
//		// TODO Auto-generated method stub
//		return "/wx/login";
//	}
//	
//	@RequestMapping(value = "/error")
//    public Object error(HttpServletResponse resp, HttpServletRequest req, Exception e) {
//        // 错误处理逻辑
//        return "其他异常" + req.getRequestURI() + "-1111-" + e.getMessage();
//    }
//
//}
