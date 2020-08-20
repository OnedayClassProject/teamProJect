package com.member.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class memberDAO {
	
		//�������� ����
		Connection con = null;
		ResultSet  rs = null;
		PreparedStatement pstmt = null;
		String sql="";
			
		//�ڿ� ���� �ϴ� �޼ҵ� 
		public void resourceClose(){
		  try{	
			if(pstmt != null) pstmt.close();
			if(rs != null) rs.close();
			if(con != null) con.close();
		  }catch(Exception e){
			  System.out.println("�ڿ����� ���� : " + e);
		  }
		}//resourceClose()
		private Connection getConnection() throws Exception {

			Connection con = null;
			Context init = new InitialContext();
			DataSource ds = (DataSource) init.lookup("java:/comp/env/jdbc/teamProject");
			con = ds.getConnection();
			return con;
		}
		
		public boolean insertMember(memberBean mbean){
			
			
			int result = 0;
			try {
				con = getConnection();
				
				
				sql = "insert into member(useremail,userpassword,username,phone,postcode,address,joindate,point,membership) "
						+ " values(?,?,?,?,?,?,?,?,?)";
				
				pstmt = con.prepareStatement(sql);
				
				
				pstmt.setString(1, mbean.getUseremail());
				pstmt.setString(2, mbean.getUserpassword());
				pstmt.setString(3, mbean.getUsername());
				pstmt.setString(4, mbean.getPhone());
				pstmt.setString(5, mbean.getPostcode());
				pstmt.setString(6, mbean.getAddress());
				pstmt.setTimestamp(7, mbean.getJoindate());
				pstmt.setString(8, "0");
				pstmt.setString(9, "basic");
				
				result = pstmt.executeUpdate();
				
				if(result != 0){
					return true;
				}
				
			} catch (Exception e) {
				System.out.println("insertMember() �޼ҵ� ���ο��� ���� �߻�"+ e);
			}finally {
				resourceClose();
			}
			
			return false;
		}
		public int checkEmail(String email) {
			int result = 0;
			
			try{
				con = getConnection();
				
				sql = "select * from member where useremail=? ";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, email);
				rs = pstmt.executeQuery();
				
				if (rs.next()){
					result = 1;
				}else{
					result = 0;
				}
			}catch(Exception e){
				//System.out.println("checkEmail() �޼ҵ� ���ο��� ���� �߻� "+e);
				e.printStackTrace();
			}finally {
				resourceClose();
			}
			
			return result;
		}
		public memberBean getMember(String email) {
			memberBean mbean = new memberBean();
			
			try {
				con = getConnection();
				sql = "select* from member where useremail=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, email);
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					mbean.setUseremail(rs.getString("useremail"));
					mbean.setUsername(rs.getString("username"));
					mbean.setPhone(rs.getString("phone"));
					mbean.setPostcode(rs.getString("postcode"));
					mbean.setAddress(rs.getString("address"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				resourceClose();
			}
			
			return mbean;
		}
		public int loginCheck(String email, String password) {
			int check = 0;
			
			try{
				con = getConnection();
				sql = "select* from member where useremail=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, email);
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					if(password.equals(rs.getString("userpassword"))){
						check = 1; // �̸���, ��й�ȣ ����
					}
				}else{ //�̸����� �������� ���� ��
					check = 0;
				}
				
			}catch (Exception e){
				e.printStackTrace();
			}finally {
				
			}
			
			return check;
		}
	
}