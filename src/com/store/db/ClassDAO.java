package com.store.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ClassDAO {
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
	
	public Vector<ClassBean> getClassInfo(){
		Vector<ClassBean> v = new Vector<ClassBean>();
		try {
			con = getConnection();
			String sql = "select class_registrynum,thumbnail,category,class_name,reservation_count from class"
					+ "order by reservation_count desc limit 1,6";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				ClassBean cbean = new ClassBean();
				
				cbean.setClass_registrynum(rs.getInt("class_registrynum"));
				cbean.setThumbnail(rs.getString("thumbnail"));
				cbean.setCategory(rs.getString("category"));
				cbean.setClass_name(rs.getString("class_name"));
				
				v.add(cbean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			resourceClose();
		}
		
		return v;
	}
	
	
	public int beginnerCount() {
		
		int cnt = 0;
		
		try {
			con = getConnection();
			
			String sql = "select count(*) from class "
					+ "where level = 'easy'";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		
		} finally {
			resourceClose();
		}
		
		return cnt;
	}
	
	
	
	// 클래스 list 메서드 
	public Vector<ClassBean> beginnerList(int startRow, int endRow) {
		
		Vector<ClassBean> v = new Vector<ClassBean>();
		
		ClassBean cbean = null;
		
		try {
			con = getConnection();
			
			// level 중 하(easy)를 가져옴 
			String sql = "select storenum,class_company, class_registrynum, class_name, category, level, thumbnail,rating "
					+ "from class "
					+ "where level = 'easy' limit ?,?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			
		while(rs.next()) {
			
			cbean = new ClassBean();
			cbean.setStorenum(rs.getInt("storenum"));
			cbean.setClass_registrynum(rs.getInt("class_registrynum"));
			cbean.setClass_name(rs.getString("class_name"));
			cbean.setCategory(rs.getString("category"));
			cbean.setLevel(rs.getString("level"));
			cbean.setThumbnail(rs.getString("thumbnail"));
			cbean.setClass_company(rs.getString("class_company"));
			cbean.setRating(rs.getInt("rating"));
			
			// vector에 classbean 객체 저장 
			v.add(cbean);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			resourceClose();
		}
		return v;
	}

	public int popularCount(){
		int count=0;
		try{
			con=getConnection();
			String sql= "select count(*) from class";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				count=rs.getInt(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			resourceClose();
		}
		return count;
	}//popularCount 硫붿냼�뱶 �걹
	
	public ArrayList <ClassBean> popularList(int startRow,int endRow){
		ArrayList<ClassBean> list = new ArrayList<ClassBean>();
		try{
			con= getConnection();
			String sql = "select class_registrynum,thumbnail,category,class_name,class_company,rating from class order by reservation_count desc limit ?,?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs=pstmt.executeQuery();
			while(rs.next()){
				ClassBean cbean = new ClassBean();
				cbean.setClass_registrynum(rs.getInt("class_registrynum"));
				cbean.setThumbnail(rs.getString("thumbnail"));
				cbean.setCategory(rs.getString("category"));
				cbean.setClass_name(rs.getString("class_name"));
				cbean.setClass_company(rs.getString("class_company"));
				cbean.setRating(rs.getInt("rating"));
				
				list.add(cbean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			resourceClose();
		}
		
		return list;
	
	}//popularList 硫붿냼�뱶 �걹


	
	// 전라도 Count
	public int jeollaCount() {
			
		int cnt = 0;
		
		try {
			con = getConnection();
			
			String sql = "select count(*) from class "
					+ "where location LIKE '%전남%' or location LIKE '%전북%'";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		
		} finally {
			resourceClose();
		}
		
		return cnt;
	}
	
	// 전라도 List
	public Vector<ClassBean> jeollaList(int startRow, int endRow) {

		Vector<ClassBean> v = new Vector<ClassBean>();
		
		ClassBean cbean = null;
		
		try {
			con = getConnection();

			// location 중 '전남','전북'을 가져옴 
			String sql = "select storenum, class_registrynum, class_name, category, level, thumbnail,rating "
					+ "from class "
					+ "where location LIKE '%전남%' or location LIKE '%전북%' or location LIKE '%광주%' limit ?,?";
						
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			
		while(rs.next()) {
			
			cbean = new ClassBean();
			cbean.setStorenum(rs.getInt("storenum"));
			cbean.setClass_registrynum(rs.getInt("class_registrynum"));
			cbean.setClass_name(rs.getString("class_name"));
			cbean.setCategory(rs.getString("category"));
			cbean.setLevel(rs.getString("level"));
			cbean.setThumbnail(rs.getString("thumbnail"));
			cbean.setRating(rs.getInt("rating"));
			
			// vector에 classbean 객체 저장 
			v.add(cbean);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			resourceClose();
		}
		return v;
	}
	
	
	// 충청도 Count
	public int chungcheongCount() {
				
			int cnt = 0;
			
			try {
				con = getConnection();
				
				String sql = "select count(*) from class "
						+ "where location LIKE '%충남%' or location LIKE '%충북%'";
				
				
				pstmt = con.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					cnt = rs.getInt(1);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			
			} finally {
				resourceClose();
			}
			
			return cnt;
		}
		
	// 충청도 List
	public Vector<ClassBean> chungcheongList(int startRow, int endRow) {

		Vector<ClassBean> v = new Vector<ClassBean>();
		
		ClassBean cbean = null;
		
		try {
			con = getConnection();
			
			// location에서 '충남', '충북'을 가져옴 
			String sql = "select storenum, class_registrynum, class_name, category, level, thumbnail,rating "
					+ "from class "
					+ "where location LIKE '%충남%' or location LIKE '%충북%' limit ?,?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			
		while(rs.next()) {
			
			cbean = new ClassBean();
			cbean.setStorenum(rs.getInt("storenum"));
			cbean.setClass_registrynum(rs.getInt("class_registrynum"));
			cbean.setClass_name(rs.getString("class_name"));
			cbean.setCategory(rs.getString("category"));
			cbean.setLevel(rs.getString("level"));
			cbean.setThumbnail(rs.getString("thumbnail"));
			cbean.setRating(rs.getInt("rating"));
			
			// vector에 classbean 객체 저장 
			v.add(cbean);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			resourceClose();
		}
		return v;
	}

	// 강원도 Count
	public int gangwonCount() {
				
			int cnt = 0;
			
			try {
				con = getConnection();
				
				String sql = "select count(*) from class "
						+ "where location LIKE '%강원%'";
				
				pstmt = con.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					cnt = rs.getInt(1);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			
			} finally {
				resourceClose();
			}
			
			return cnt;
		}
			
	// 강원도 List
	public Vector<ClassBean> gangwonList(int startRow, int endRow) {

		Vector<ClassBean> v = new Vector<ClassBean>();
		
		ClassBean cbean = null;
		
		try {
			con = getConnection();
			
			// location에서 '충남', '충북'을 가져옴 
			String sql = "select storenum, class_registrynum, class_name, category, level, thumbnail,rating "
					+ "from class "
					+ "where location LIKE '%강원%' limit ?,?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			
		while(rs.next()) {
			
			cbean = new ClassBean();
			cbean.setStorenum(rs.getInt("storenum"));
			cbean.setClass_registrynum(rs.getInt("class_registrynum"));
			cbean.setClass_name(rs.getString("class_name"));
			cbean.setCategory(rs.getString("category"));
			cbean.setLevel(rs.getString("level"));
			cbean.setThumbnail(rs.getString("thumbnail"));
			cbean.setRating(rs.getInt("rating"));
			
			// vector에 classbean 객체 저장 
			v.add(cbean);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			resourceClose();
		}
		return v;
	}


	// 경상도 Count
	public int gyeongsangCount() {
				
			int cnt = 0;
			
			try {
				con = getConnection();
				
				String sql = "select count(*) from class "
						+ "where location LIKE '%경남%' or location LIKE '%경북%'";
				
				pstmt = con.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					cnt = rs.getInt(1);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			
			} finally {
				resourceClose();
			}
			
			return cnt;
		}
			
	// 경상도 List
	public Vector<ClassBean> gyeongsangList(int startRow, int endRow) {

		Vector<ClassBean> v = new Vector<ClassBean>();
		
		ClassBean cbean = null;
		
		try {
			con = getConnection();
			
			// location 중 '경남','경북'을 가져옴 
			String sql = "select storenum, class_registrynum, class_name, category, level, thumbnail,rating "
					+ "from class "
					+ "where location LIKE '%경남%' or location LIKE '%경북%' limit ?,?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			
		while(rs.next()) {
			
			cbean = new ClassBean();
			cbean.setStorenum(rs.getInt("storenum"));
			cbean.setClass_registrynum(rs.getInt("class_registrynum"));
			cbean.setClass_name(rs.getString("class_name"));
			cbean.setCategory(rs.getString("category"));
			cbean.setLevel(rs.getString("level"));
			cbean.setThumbnail(rs.getString("thumbnail"));
			cbean.setRating(rs.getInt("rating"));
			
			// vector에 classbean 객체 저장 
			v.add(cbean);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			resourceClose();
		}
		return v;
	}


	// 제주도 Count
	public int jejuCount() {
				
			int cnt = 0;
			
			try {
				con = getConnection();
				
				String sql = "select count(*) from class "
						+ "where location LIKE '%제주%'";
				
				pstmt = con.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					cnt = rs.getInt(1);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			
			} finally {
				resourceClose();
			}
			
			return cnt;
		}
			
	// 제주도 List
	public Vector<ClassBean> jejuList(int startRow, int endRow) {

		Vector<ClassBean> v = new Vector<ClassBean>();
		
		ClassBean cbean = null;
		
		try {
			con = getConnection();
			
			// location 중 '제주'를 가져옴 
			String sql = "select storenum, class_registrynum, class_name, category, level, thumbnail,rating "
					+ "from class "
					+ "where location LIKE '%제주%' limit ?,?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			
		while(rs.next()) {
			
			cbean = new ClassBean();
			cbean.setStorenum(rs.getInt("storenum"));
			cbean.setClass_registrynum(rs.getInt("class_registrynum"));
			cbean.setClass_name(rs.getString("class_name"));
			cbean.setCategory(rs.getString("category"));
			cbean.setLevel(rs.getString("level"));
			cbean.setThumbnail(rs.getString("thumbnail"));
			cbean.setRating(rs.getInt("rating"));
			
			// vector에 classbean 객체 저장 
			v.add(cbean);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			resourceClose();
		}
		return v;
	}
	


	public int seoulCount(){
		int count=0;
		try{
			con=getConnection();
			String sql= "select count(*) from class where location like '%서울%'";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				count=rs.getInt(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			resourceClose();
		}
		return count;
	}//popularCount 硫붿냼�뱶 �걹
	
	public ArrayList <ClassBean> seoulList(int startRow,int endRow){
		ArrayList<ClassBean> list = new ArrayList<ClassBean>();
		try{
			con= getConnection();
			String sql = "select class_registrynum,thumbnail,category,class_name,rating from class where location LIKE '%서울%' limit ?,?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs=pstmt.executeQuery();
			while(rs.next()){
				ClassBean cbean = new ClassBean();
				cbean.setClass_registrynum(rs.getInt("class_registrynum"));
				cbean.setThumbnail(rs.getString("thumbnail"));
				cbean.setCategory(rs.getString("category"));
				cbean.setClass_name(rs.getString("class_name"));
				cbean.setRating(rs.getInt("rating"));
				
				list.add(cbean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			resourceClose();
		}
		
		return list;
	
	}//popularList 硫붿냼�뱶 �걹
	

	public int gyunggiCount(){
		int count=0;
		try{
			con=getConnection();
			String sql= "select count(*) from class where location like '%경기%'";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				count=rs.getInt(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			resourceClose();
		}
		return count;
	}//popularCount 硫붿냼�뱶 �걹
	
	public ArrayList <ClassBean> gyunggiList(int startRow,int endRow){
		ArrayList<ClassBean> list = new ArrayList<ClassBean>();
		try{
			con= getConnection();
			String sql = "select class_registrynum,thumbnail,category,class_name,rating from class where location LIKE '%경기%' limit ?,?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs=pstmt.executeQuery();
			while(rs.next()){
				ClassBean cbean = new ClassBean();
				cbean.setClass_registrynum(rs.getInt("class_registrynum"));
				cbean.setThumbnail(rs.getString("thumbnail"));
				cbean.setCategory(rs.getString("category"));
				cbean.setClass_name(rs.getString("class_name"));
				cbean.setRating(rs.getInt("rating"));
				
				list.add(cbean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			resourceClose();
		}
		
		return list;
	
	}//popularList 硫붿냼�뱶 �걹
	

	public int busanCount(){
		int count=0;
		try{
			con=getConnection();
			String sql= "select count(*) from class where location like '%부산%'";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				count=rs.getInt(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			resourceClose();
		}
		return count;
	}//popularCount 硫붿냼�뱶 �걹
	
	public ArrayList <ClassBean> busanList(int startRow,int endRow){
		ArrayList<ClassBean> list = new ArrayList<ClassBean>();
		try{
			con= getConnection();
			String sql = "select class_registrynum,thumbnail,category,class_name,rating from class where location LIKE '%부산%' limit ?,?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs=pstmt.executeQuery();
			while(rs.next()){
				ClassBean cbean = new ClassBean();
				cbean.setClass_registrynum(rs.getInt("class_registrynum"));
				cbean.setThumbnail(rs.getString("thumbnail"));
				cbean.setCategory(rs.getString("category"));
				cbean.setClass_name(rs.getString("class_name"));
				cbean.setRating(rs.getInt("rating"));
				
				list.add(cbean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			resourceClose();
		}
		
		return list;
	
	}//popularList 硫붿냼�뱶 �걹
	
	public int daeguCount(){
		int count=0;
		try{
			con=getConnection();
			String sql= "select count(*) from class where location like '%대구%'";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				count=rs.getInt(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			resourceClose();
		}
		return count;
	}//popularCount 硫붿냼�뱶 �걹
	
	public ArrayList <ClassBean> daeguList(int startRow,int endRow){
		ArrayList<ClassBean> list = new ArrayList<ClassBean>();
		try{
			con= getConnection();
			String sql = "select class_registrynum,thumbnail,category,class_name,rating from class where location LIKE '%대구%' limit ?,?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs=pstmt.executeQuery();
			while(rs.next()){
				ClassBean cbean = new ClassBean();
				cbean.setClass_registrynum(rs.getInt("class_registrynum"));
				cbean.setThumbnail(rs.getString("thumbnail"));
				cbean.setCategory(rs.getString("category"));
				cbean.setClass_name(rs.getString("class_name"));
				cbean.setRating(rs.getInt("rating"));
				
				list.add(cbean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			resourceClose();
		}
		
		return list;
	
	}//popularList 硫붿냼�뱶 �걹
	
	
	public int daejeonCount(){
		int count=0;
		try{
			con=getConnection();
			String sql= "select count(*) from class where location like '%대전%'";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				count=rs.getInt(1);
				}
		}catch(Exception e){
				e.printStackTrace();
			}finally{
				resourceClose();
			}
			return count;
		}//popularCount 硫붿냼�뱶 �걹
		
	
	public int diffuserCount(){
		int count=0;
		try{
			con=getConnection();
			String sql= "select count(*) from class where category='디퓨저'";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				count=rs.getInt(1);
				}
		}catch(Exception e){
				e.printStackTrace();
			}finally{
				resourceClose();
			}
			return count;
		}
	public ArrayList<ClassBean> diffuserClassList(int startRow,int endRow){
		ArrayList<ClassBean> list = new ArrayList<ClassBean>();
		//하나의 레코드를 저장할 객체 선언
		ClassBean bean =null;
		try{
			//커넥션 메소드 호출하여 DB연결객체 하나 얻기
			con=getConnection();
			//쿼리준비 : 전체 차량 레코드 검색
			String sql="select class_registrynum,thumbnail,category,class_name,rating from class where category='디퓨저' limit ?,?";
			//쿼리를 실행할 수 있는 객체 선언
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			//쿼리 실행 후 결과를 리턴
			rs=pstmt.executeQuery();
			//반복문을 돌면서 빈 클래스에 컬럼데이터를 저장
			while(rs.next()){
				bean=new ClassBean();
				bean.setClass_registrynum(rs.getInt("class_registrynum"));
				bean.setThumbnail(rs.getString("thumbnail"));
				bean.setCategory(rs.getString("category"));
				bean.setClass_name(rs.getString("class_name"));
				bean.setRating(rs.getInt("rating"));
				
				list.add(bean);
			}			

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			resourceClose();
		}


		return list;//벡터 리턴
	}//diffuserClassList()메소드 끝

	 
	public ArrayList <ClassBean> daejeonList(int startRow,int endRow){
		ArrayList<ClassBean> list = new ArrayList<ClassBean>();
		try{
			con= getConnection();
			String sql = "select class_registrynum,thumbnail,category,class_name,rating from class where location LIKE '%서울%' limit ?,?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs=pstmt.executeQuery();
			while(rs.next()){
				ClassBean cbean = new ClassBean();
				cbean.setClass_registrynum(rs.getInt("class_registrynum"));
				cbean.setThumbnail(rs.getString("thumbnail"));
				cbean.setCategory(rs.getString("category"));
				cbean.setClass_name(rs.getString("class_name"));
				cbean.setRating(rs.getInt("rating"));
				
				list.add(cbean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			resourceClose();
		}
		
		return list;
	}
	
	public int candleCount(){
		int count=0;
		try{
			con=getConnection();
			String sql= "select count(*) from class where category='캔들'";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				count=rs.getInt(1);
				}
		}catch(Exception e){
				e.printStackTrace();
			}finally{
				resourceClose();
			}
			return count;
		}
	public ArrayList<ClassBean> candleClassList(int startRow,int endRow){
		ArrayList<ClassBean> list = new ArrayList<ClassBean>();
		//하나의 레코드를 저장할 객체 선언
		ClassBean bean =null;
		try{
			//커넥션 메소드 호출하여 DB연결객체 하나 얻기
			con=getConnection();
			//쿼리준비 : 전체 차량 레코드 검색
			String sql="select class_registrynum,thumbnail,category,class_name,rating from class where category='캔들' limit ?,?";
			//쿼리를 실행할 수 있는 객체 선언
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			//쿼리 실행 후 결과를 리턴
			rs=pstmt.executeQuery();
			//반복문을 돌면서 빈 클래스에 컬럼데이터를 저장
			while(rs.next()){
				bean=new ClassBean();
				bean.setClass_registrynum(rs.getInt("class_registrynum"));
				bean.setThumbnail(rs.getString("thumbnail"));
				bean.setCategory(rs.getString("category"));
				bean.setClass_name(rs.getString("class_name"));
				bean.setRating(rs.getInt("rating"));
				
				list.add(bean);
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			resourceClose();
		}
		return list;
	}//candleClassList()메소드 끝
	public int cookingCount(){
		int count=0;
		try{
			con=getConnection();
			String sql= "select count(*) from class where category='요리'";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				count=rs.getInt(1);
				}
		}catch(Exception e){
				e.printStackTrace();
			}finally{
				resourceClose();
			}
			return count;
		}
	public ArrayList<ClassBean> cookingClassList(int startRow,int endRow){
		ArrayList<ClassBean> list = new ArrayList<ClassBean>();
		//하나의 레코드를 저장할 객체 선언
		ClassBean bean =null;
		try{
			//커넥션 메소드 호출하여 DB연결객체 하나 얻기
			con=getConnection();
			//쿼리준비 : 전체 차량 레코드 검색
			String sql="select class_registrynum,thumbnail,category,class_name,rating from class where category='요리' limit ?,?";
			//쿼리를 실행할 수 있는 객체 선언
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			//쿼리 실행 후 결과를 리턴
			rs=pstmt.executeQuery();
			//반복문을 돌면서 빈 클래스에 컬럼데이터를 저장
			while(rs.next()){
				bean=new ClassBean();
				bean.setClass_registrynum(rs.getInt("class_registrynum"));
				bean.setThumbnail(rs.getString("thumbnail"));
				bean.setCategory(rs.getString("category"));
				bean.setClass_name(rs.getString("class_name"));
				bean.setRating(rs.getInt("rating"));
				
				list.add(bean);
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			resourceClose();
		}
		return list;
	}//cookingClassList()메소드 끝
	public int bakingCount(){
		int count=0;
		try{
			con=getConnection();
			String sql= "select count(*) from class where category='베이킹'";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				count=rs.getInt(1);
				}
		}catch(Exception e){
				e.printStackTrace();
			}finally{
				resourceClose();
			}
			return count;
		}
	public ArrayList<ClassBean> bakingClassList(int startRow,int endRow){
		ArrayList<ClassBean> list = new ArrayList<ClassBean>();
		//하나의 레코드를 저장할 객체 선언
		ClassBean bean =null;
		try{
			//커넥션 메소드 호출하여 DB연결객체 하나 얻기
			con=getConnection();
			//쿼리준비 : 전체 차량 레코드 검색
			String sql="select class_registrynum,thumbnail,category,class_name,rating from class where category='베이킹' limit ?,?";
			//쿼리를 실행할 수 있는 객체 선언
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			//쿼리 실행 후 결과를 리턴
			rs=pstmt.executeQuery();
			//반복문을 돌면서 빈 클래스에 컬럼데이터를 저장
			while(rs.next()){
				bean=new ClassBean();
				bean.setClass_registrynum(rs.getInt("class_registrynum"));
				bean.setThumbnail(rs.getString("thumbnail"));
				bean.setCategory(rs.getString("category"));
				bean.setClass_name(rs.getString("class_name"));
				bean.setRating(rs.getInt("rating"));
				
				list.add(bean);
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			resourceClose();
		}
		return list;
	}//bakingClassList()메소드 끝
	public int potteryCount(){
		int count=0;
		try{
			con=getConnection();
			String sql= "select count(*) from class where category='도자기'";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				count=rs.getInt(1);
				}
		}catch(Exception e){
				e.printStackTrace();
			}finally{
				resourceClose();
			}
			return count;
		}
	public ArrayList<ClassBean> potteryClassList(int startRow,int endRow){
		ArrayList<ClassBean> list = new ArrayList<ClassBean>();
		//하나의 레코드를 저장할 객체 선언
		ClassBean bean =null;
		try{
			//커넥션 메소드 호출하여 DB연결객체 하나 얻기
			con=getConnection();
			//쿼리준비 : 전체 차량 레코드 검색
			String sql="select class_registrynum,thumbnail,category,class_name,rating from class where category='도자기' limit ?,?";
			//쿼리를 실행할 수 있는 객체 선언
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			//쿼리 실행 후 결과를 리턴
			rs=pstmt.executeQuery();
			//반복문을 돌면서 빈 클래스에 컬럼데이터를 저장
			while(rs.next()){
				bean=new ClassBean();
				bean.setClass_registrynum(rs.getInt("class_registrynum"));
				bean.setThumbnail(rs.getString("thumbnail"));
				bean.setCategory(rs.getString("category"));
				bean.setClass_name(rs.getString("class_name"));
				bean.setRating(rs.getInt("rating"));
				
				list.add(bean);
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			resourceClose();
		}
		return list;
	}//potteryClassList()메소드 끝
	public int perfumeCount(){
		int count=0;
		try{
			con=getConnection();
			String sql= "select count(*) from class where category='향수'";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				count=rs.getInt(1);
				}
		}catch(Exception e){
				e.printStackTrace();
			}finally{
				resourceClose();
			}
			return count;
		}
	public ArrayList<ClassBean> perfumeClassList(int startRow,int endRow){
		ArrayList<ClassBean> list = new ArrayList<ClassBean>();
		//하나의 레코드를 저장할 객체 선언
		ClassBean bean =null;
		try{
			//커넥션 메소드 호출하여 DB연결객체 하나 얻기
			con=getConnection();
			//쿼리준비 : 전체 차량 레코드 검색
			String sql="select class_registrynum,thumbnail,category,class_name,rating from class where category='향수' limit ?,?";
			//쿼리를 실행할 수 있는 객체 선언
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			//쿼리 실행 후 결과를 리턴
			rs=pstmt.executeQuery();
			//반복문을 돌면서 빈 클래스에 컬럼데이터를 저장
			while(rs.next()){
				bean=new ClassBean();
				bean.setClass_registrynum(rs.getInt("class_registrynum"));
				bean.setThumbnail(rs.getString("thumbnail"));
				bean.setCategory(rs.getString("category"));
				bean.setClass_name(rs.getString("class_name"));
				bean.setRating(rs.getInt("rating"));
				
				list.add(bean);
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			resourceClose();
		}
		return list;
	}//perfumeClassList()메소드 끝
	public int soapCount(){
		int count=0;
		try{
			con=getConnection();
			String sql= "select count(*) from class where category='비누'";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				count=rs.getInt(1);
				}
		}catch(Exception e){
				e.printStackTrace();
			}finally{
				resourceClose();
			}
			return count;
		}
	public ArrayList<ClassBean> soapClassList(int startRow,int endRow){
		ArrayList<ClassBean> list = new ArrayList<ClassBean>();
		//하나의 레코드를 저장할 객체 선언
		ClassBean bean =null;
		try{
			//커넥션 메소드 호출하여 DB연결객체 하나 얻기
			con=getConnection();
			//쿼리준비 : 전체 차량 레코드 검색
			String sql="select class_registrynum,thumbnail,category,class_name,rating from class where category='비누' limit ?,?";
			//쿼리를 실행할 수 있는 객체 선언
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			//쿼리 실행 후 결과를 리턴
			rs=pstmt.executeQuery();
			//반복문을 돌면서 빈 클래스에 컬럼데이터를 저장
			while(rs.next()){
				bean=new ClassBean();
				bean.setClass_registrynum(rs.getInt("class_registrynum"));
				bean.setThumbnail(rs.getString("thumbnail"));
				bean.setCategory(rs.getString("category"));
				bean.setClass_name(rs.getString("class_name"));
				bean.setRating(rs.getInt("rating"));
				
				list.add(bean);
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			resourceClose();
		}
		return list;
	}//soapClassList()메소드 끝
	public ArrayList<ClassBean> getStoreClassInfo(int storenum,int startRow,int endRow){
		ArrayList<ClassBean> list = new ArrayList<ClassBean>();
		//하나의 레코드를 저장할 객체 선언
		ClassBean bean =null;
		System.out.println(storenum);
		System.out.println(startRow);
		System.out.println(endRow);
		
		
		try{
			//커넥션 메소드 호출하여 DB연결객체 하나 얻기
			con=getConnection();
			//쿼리준비 : 전체 차량 레코드 검색
			String sql="select class_registrynum,thumbnail,category,class_name,rating,price,personnel,level,storenum from class where storenum=? order by reservation_count desc limit ?,?";
			//쿼리를 실행할 수 있는 객체 선언
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, storenum);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			//쿼리 실행 후 결과를 리턴
			rs=pstmt.executeQuery();
			//반복문을 돌면서 빈 클래스에 컬럼데이터를 저장
	
			
			while(rs.next()){
				bean=new ClassBean();
				bean.setClass_registrynum(rs.getInt("class_registrynum"));
				bean.setThumbnail(rs.getString("thumbnail"));
				bean.setCategory(rs.getString("category"));
				bean.setClass_name(rs.getString("class_name"));
				bean.setRating(rs.getInt("rating"));
				bean.setPrice(rs.getString("price"));
				bean.setPersonnel(rs.getString("personnel"));
				bean.setLevel(rs.getString("level"));
				bean.setStorenum(rs.getInt("storenum"));
				list.add(bean);
			}			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			resourceClose();
		}
		return list;
	}//soapClassList()메소드 끝
	
	public int StoreClassCount(int storenum){
		int count=0;
		try{
			con=getConnection();
			String sql= "select count(*) from class where storenum=? ";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, storenum);
			rs=pstmt.executeQuery();
			if(rs.next()){
				count=rs.getInt(1);
				System.out.println(count);
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(count);
		}finally{
			resourceClose();
		}
		return count;
	}//popularCount
	public ClassBean getClassbean(String class_registrynum,int storenum){
		ClassBean cbean = new ClassBean();
		try {
			con = getConnection();
			String sql = "select * from class where storenum=? and class_registrynum=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, storenum);
			pstmt.setString(2, class_registrynum);
			
			rs=pstmt.executeQuery();
			if(rs.next()){
				cbean=new ClassBean();
				cbean.setClass_registrynum(rs.getInt("class_registrynum"));
				cbean.setThumbnail(rs.getString("thumbnail"));
				cbean.setCategory(rs.getString("category"));
				cbean.setClass_name(rs.getString("class_name"));
				cbean.setRating(rs.getInt("rating"));
				cbean.setPrice(rs.getString("price"));
				cbean.setPersonnel(rs.getString("personnel"));
				cbean.setLevel(rs.getString("level"));
				cbean.setLocation(rs.getString("location"));
				cbean.setClass_company((rs.getString("class_company")));
				cbean.setStorenum(rs.getInt("storenum"));
				cbean.setParking(rs.getString("parking"));
				cbean.setContent(rs.getString("content"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			resourceClose();
		}
		
		return cbean;
	}
	//ClassInfoUpdate
	//클래스등록
	public int classInfoUpdate(ClassBean cb,int class_registrynum) {
		
		try {
			con = getConnection();
			
			String sql = "update class set class_name=?,category=?,location=?,level=?,time=?,personnel=?,price=?,parking=?,content=?,thumbnail=?"
					+ "where class_registrynum=? and storenum=?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, cb.getClass_name());
			pstmt.setString(2, cb.getCategory());
			
			pstmt.setString(3, cb.getLocation());
			pstmt.setString(4, cb.getLevel());
			pstmt.setString(5, cb.getTime());
			pstmt.setString(6, cb.getPersonnel());
			pstmt.setString(7, cb.getPrice());
			pstmt.setString(8, cb.getParking());
			
			pstmt.setString(9, cb.getContent());
			pstmt.setString(10, cb.getThumbnail());
			
			pstmt.setInt(11, class_registrynum);
			pstmt.setInt(12, cb.getStorenum());
			

			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			resourceClose();
		}
		
		return 1;
	}
	
	public ArrayList<ClassBean> myList(String email){
		ArrayList<ClassBean> list = new ArrayList<ClassBean>();
		ClassBean bean = null;
		try{
			con=getConnection();
			String sql="select c.thumbnail, c.category, c.level, c.class_name, c.class_company, r.class_registrynum from class as c join classreservation as r on r.class_registrynum = c.class_registrynum where r.useremail=? limit 3";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, email);
			rs=pstmt.executeQuery();
			while(rs.next()){
				bean=new ClassBean();
				bean.setThumbnail(rs.getString("thumbnail"));
				bean.setCategory(rs.getString("category"));
				bean.setLevel(rs.getString("level"));
				bean.setClass_name(rs.getString("class_name"));
				bean.setClass_company(rs.getString("class_company"));
				bean.setClass_registrynum(rs.getInt("class_registrynum"));
				list.add(bean);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			resourceClose();
		}
		return list;
	}
	
	public ArrayList<ClassBean> refundList(String email){
		ArrayList<ClassBean> list = new ArrayList<ClassBean>();
		ClassBean bean = null;
		try{
			con=getConnection();
			String sql="select c.thumbnail, c.category, c.level, c.class_name, c.class_company, r.class_registrynum from class as c join classcancle as r on r.class_registrynum = c.class_registrynum where r.useremail=? limit 3";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, email);
			rs=pstmt.executeQuery();
			while(rs.next()){
				bean=new ClassBean();
				bean.setThumbnail(rs.getString("thumbnail"));
				bean.setCategory(rs.getString("category"));
				bean.setLevel(rs.getString("level"));
				bean.setClass_name(rs.getString("class_name"));
				bean.setClass_company(rs.getString("class_company"));
				bean.setClass_registrynum(rs.getInt("class_registrynum"));
				list.add(bean);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			resourceClose();
		}
		return list;
	}
	
	public ArrayList<ClassBean> favorList(String email){
		ArrayList<ClassBean> list = new ArrayList<ClassBean>();
		ClassBean bean = null;
		try{
			con=getConnection();
			String sql="select c.thumbnail, c.category, c.level, c.class_name, c.class_company, f.class_registrynum from class as c join favor as f on f.class_registrynum = c.class_registrynum where f.useremail=? limit 3";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, email);
			rs=pstmt.executeQuery();
			while(rs.next()){
				bean=new ClassBean();
				bean.setThumbnail(rs.getString("thumbnail"));
				bean.setCategory(rs.getString("category"));
				bean.setLevel(rs.getString("level"));
				bean.setClass_name(rs.getString("class_name"));
				bean.setClass_company(rs.getString("class_company"));
				bean.setClass_registrynum(rs.getInt("class_registrynum"));
				list.add(bean);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			resourceClose();
		}
		return list;
	}
	
	public int reserveCount(String userid){
		int count=0;
		try{
			con=getConnection();
			String sql= "select count(*) from classreservation where useremail = ?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs=pstmt.executeQuery();
			if(rs.next()){
				count=rs.getInt(1);
				}
		}catch(Exception e){
				e.printStackTrace();
			}finally{
				resourceClose();
			}
			return count;
		}
	public ArrayList<ClassBean> reserveList(String email,int startRow, int endRow){
		ArrayList<ClassBean> list = new ArrayList<ClassBean>();
		ClassBean bean = null;
		try{
			con=getConnection();
			String sql="select c.thumbnail,c.class_name, c.category, r.class_registrynum,r.reservationnum,r.reviewCheck ,r.reservation_personnel, r.reservation_date,"
					+ "r.user_name,r.time,r.reservation_tel,r.reservation_price,r.reservation_location,r.content,r.refundCheck "
					+ " from class as c join classreservation as r on r.class_registrynum = c.class_registrynum where r.useremail=? "
					+ " order by pay_date desc limit ?,?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rs=pstmt.executeQuery();
			while(rs.next()){
				bean=new ClassBean();
				bean.setThumbnail(rs.getString("thumbnail"));
				bean.setCategory(rs.getString("category"));
				bean.setClass_name(rs.getString("class_name"));
				bean.setClass_company(rs.getString("user_name"));
				bean.setClass_registrynum(rs.getInt("class_registrynum"));
				bean.setPersonnel(rs.getString("reservation_personnel"));
				bean.setTime(rs.getString("reservation_date")+"<br>"+rs.getString("time"));
				bean.setLevel(rs.getString("reservation_date"));
				bean.setContent(rs.getString("content"));
				bean.setRating(rs.getInt("reservationnum"));
				bean.setReviewCheck(rs.getInt("reviewCheck"));
				bean.setPrice(rs.getString("reservation_price"));
				bean.setLocation(rs.getString("reservation_location"));
				bean.setSale(rs.getString("reservation_tel"));
				bean.setReservation_count(rs.getInt("refundCheck"));
				list.add(bean);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			resourceClose();
		}
		return list;
	}
	
}
