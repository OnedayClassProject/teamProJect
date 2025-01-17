package com.help.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class helpDAO {
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	private Connection getConnection() throws Exception{;
		Connection conn=null;
		Context init=new InitialContext();
		DataSource ds=(DataSource)init.lookup("java:comp/env/jdbc/teamProject");
		conn=ds.getConnection();
		return conn;
	}
	private void resourceClose(){
		try {
			if(con != null) con.close();
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
		} catch (SQLException e) {
			System.out.println("resourceClose()에서 예외발생 : " + e);
		}
	}
	public int insertHelp(helpBean bean){
		int result = 0;
		int num = 0;
		try {
			con = getConnection();
			String sql = "select max(num) from help";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()){
				num = rs.getInt(1) + 1;
			}
			sql = "insert into help(num,title,content,date,writer) values(?,?,?,now(),?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, bean.getTitle());
			pstmt.setString(3, bean.getContent());
			pstmt.setString(4, bean.getWriter());
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("insertHelp()에서 예외 발생 : "+ e);
		} finally{
			resourceClose();
		}
		return 1;
	}
	public List<helpBean> getHelpList(int startRow, int pageSize){
		List<helpBean> list = new ArrayList<helpBean>();
		try {
			con = getConnection();
			String sql ="select * from help order by num desc limit ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, pageSize);
			rs = pstmt.executeQuery();
			while(rs.next()){
				helpBean bean = new helpBean();
				bean.setNum(rs.getInt("num"));
				bean.setTitle(rs.getString("title"));
				bean.setContent(rs.getString("content"));
				bean.setDate(rs.getTimestamp("date"));
				bean.setWriter(rs.getString("writer"));
				list.add(bean);
			}
		} catch (Exception e) {
			System.out.println("getHelpList()에서 예외발생 : "+e);
		} finally{
			resourceClose();
		}
		
		return list;
	}
	public helpBean getHelpConent(int num) {
		helpBean bean = null;
		try {
			con = getConnection();
			String sql = "select * from help where num =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()){
				bean = new helpBean();
				bean.setNum(num);
				bean.setTitle(rs.getString("title"));
				bean.setWriter(rs.getString("writer"));
				bean.setContent(rs.getString("content"));
				bean.setDate(rs.getTimestamp("date"));
			}
		} catch (Exception e) {
			System.out.println("getHelpConent()에서 예외발생 : "+e);
		} finally{
			resourceClose();
		}
		
		return bean;
	}
	public int deleteHelp(int num) {
		int result = 0;
		try {
			con = getConnection();
			String sql = "delete from help where num =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			result = pstmt.executeUpdate();
			if(result !=0)
				return 1;
		} catch (Exception e) {
			System.out.println("deleteHelp()에서 예외발생 : "+e);
		} finally{
			resourceClose();
		}
		return 0;
	}
	public int updateHelp(helpBean bean) {
		int result = 0;
		try {
			con = getConnection();
			String sql = "update help set title = ?, content = ?, date = now() where num =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getTitle());
			pstmt.setString(2, bean.getContent());
			pstmt.setInt(3, bean.getNum());
			result = pstmt.executeUpdate();
			if(result != 0)
				return 1;
		} catch (Exception e) {
			System.out.println("updateHelp()에서 예외발생 : "+e);
		} finally{
			resourceClose();
		}
		return 0;
	}
	public int getHelpCount() {
		int count = 0;
		try {
			con = getConnection();
			String sql = "select count(*) from help";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next())
				count = rs.getInt(1);
		} catch (Exception e) {
			System.out.println("getHelpCount()에서 예외발생 : "+e);
		} finally{
			resourceClose();
		}
		return count;
	}
}
