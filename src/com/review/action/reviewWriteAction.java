package com.review.action;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.command.CommandHandler;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.review.db.ReviewBean;
import com.review.db.ReviewDAO;

public class reviewWriteAction implements CommandHandler{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		ServletContext ctx = request.getServletContext();
		String realPath = ctx.getRealPath("/thumbnailImage");
		
		System.out.println(realPath);
		
		int max = 100*1024*1024;
		MultipartRequest multipartRequest = new MultipartRequest(request,realPath,max,"UTF-8",new DefaultFileRenamePolicy());
		  
		int class_reg = Integer.parseInt(multipartRequest.getParameter("class_regisitrynum"));
		int classnum = Integer.parseInt(multipartRequest.getParameter("classnum"));
		String company_name = multipartRequest.getParameter("company_name");
		String class_name = multipartRequest.getParameter("class_name");
		String email = (String)session.getAttribute("userid");
		email = "skdms";
		String res_date = multipartRequest.getParameter("reservation_date");
		int rating = Integer.parseInt(multipartRequest.getParameter("rating"));
		String content = multipartRequest.getParameter("content");
		String fileImage = multipartRequest.getFilesystemName("image");

		System.out.println("rating"+rating);
		
		ReviewBean rbean = new ReviewBean();
		rbean.setClass_reg(class_reg);
		rbean.setClassnum(classnum);
		rbean.setCompany_name(company_name);
		rbean.setClass_name(class_name);
		rbean.setEmail(email);
		rbean.setRes_date(res_date);
		rbean.setRating(rating);
		rbean.setContent(content);
		rbean.setFileImage(fileImage);
		rbean.setReviewdate(new Timestamp(System.currentTimeMillis()));
		
		
		ReviewDAO rdao = new ReviewDAO();
		int result = rdao.reviewWrite(rbean);
		request.setAttribute("data", result);
		
		
		
		 
		return "board/check.jsp";
	}
	
	

}