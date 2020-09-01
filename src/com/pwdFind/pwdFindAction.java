package com.pwdFind;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.command.CommandHandler;
import com.member.db.memberDAO;

public class pwdFindAction implements CommandHandler {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * HttpSession session = request.getSession(); String useremail =
		 * (String)session.getAttribute("userid");
		 */	
		

		request.setCharacterEncoding("UTF-8");
		String email=request.getParameter("email");
		String checkNum=request.getParameter("checkNum");
		
		memberDAO mdao = new memberDAO();
		
		String authNum = mdao.authNum();//DAO �������� �޾Ƽ� �Ѹ��� authnum
		boolean result = mdao.sendEmail(email,authNum);//���� ����

		request.setAttribute("data",authNum);
		
/*		int num = mdao.checkNum(checkNum);//������ȣ ��ġ Ȯ��
*/		return "board/check.jsp";
	}

}
