package com.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.command.CommandHandler;
import com.store.db.StoreBean;
import com.store.db.StoreDAO;

public class ClassCreateAction implements CommandHandler {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		 
		String storeid = (String)session.getAttribute("storeid");
		
		StoreDAO sdao = new StoreDAO();
		int num = sdao.getClassNum();
		
		request.setAttribute("num", num);
		
		
		return "store/classCreate.jsp";
	}

}
