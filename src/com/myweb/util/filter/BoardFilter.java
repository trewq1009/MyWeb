package com.myweb.util.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myweb.user.model.UserVO;

// 1. Filter인터페이스를 상속받고, doFilter메서드를 오버라이딩 합니다.
// 2. 필터를 등록하는 방법 @WebFilter어노테이션 방법 or web.xml에 필터 설정
//@WebFilter("/*") - 모든 요청
//@WebFilter("*.board") - board
@WebFilter({"/board/write.board", "/board/regist.board"})	// 글 쓰기 화면이나, 글 등록시에만 세션검사
public class BoardFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		// ServletRequest는 HttpServletReqest의 부모타입.
		// 형변환을 통해서 자식형태로 변환
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		// 세션은 얻어서 권한 확인
		HttpSession session = req.getSession();
		UserVO user = (UserVO)session.getAttribute("user");
		
		if(user == null) {	// 세션이 존재하지 않는다는 것은 권한이 없음
			//res.sendRedirect("list.board");
			
			res.setContentType("text/html; charset=UTF-8");	// 응답 문서 설정
			PrintWriter out = res.getWriter();
			out.println("<script>");

			out.println("alert('로그인이 필요한 서비스 입니다.');");
			out.println("location.href='/MyWeb/user/login.user';");		// 로그인 화면
			
			out.println("</script>");
			
			return;
		}
		
		
		
		
		
		chain.doFilter(request, response);	// 서블릿이나, 연결되어 있는 다른 필터를 실행.
	}

}































