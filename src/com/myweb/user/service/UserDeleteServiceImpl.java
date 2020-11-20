package com.myweb.user.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myweb.user.model.UserDAO;
import com.myweb.user.model.UserVO;

public class UserDeleteServiceImpl implements UserService {

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		
		UserVO vo = (UserVO)session.getAttribute("user");
		String pw = request.getParameter("pw");
		
		UserDAO dao = UserDAO.getInstance();
		int result = dao.checkPw(vo, pw);	// 중복시 1, 중복x 0
		
		if(result == 1) {
			dao.delete(vo.getId());
			session.invalidate();
			return 1;
		} 
		
		return 0;
	}

}
