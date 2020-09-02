<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
   <link rel="stylesheet" href="${pageContext.request.contextPath}/member/mypage.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/member/reserveList.css">

</head>
<script src="https://code.jquery.com/jquery-3.1.0.js"></script>
<body>
<jsp:include page="../header.jsp" />
<section>
	<div class="my_wrap">
		<div class="side_menu">
            <div class="side_detail">
                <a href='${pageContext.request.contextPath}/AllGetMember.do'><div>회원조회</div>
                <div class="side_detail2">></div>
                </a>
            </div>
            <hr>
            <div class="side_detail">
               <a href='${pageContext.request.contextPath}/AllGetStore.do'><div>업체조회</div>
                <div class="side_detail2">></div>
                </a>
            </div>
            <hr>
            <div class="side_detail">
                <a href='${pageContext.request.contextPath}/AllGetReservation.do'><div>예약조회</div>
                <div class="side_detail2">></div>
                </a>
            </div>
            <hr>
            
        </div>
        <div class="my_main">
        	<div>업체 리스트</div>
        	<hr>
        	<div>
        		<div class="reserveInfo1">
        			<div>업체 이메일</div>
        			<div>업체 이름</div>
        			<div>업체 전화번호</div>
        			<div>업체 주소</div>
        			<div>업체 우편번호</div>
        			<div>업체 가입날짜</div>
        		</div>
        		<div class="line"></div>
        		<!-- 회원 목록 반복 -->
        		
        		<c:forEach var="getStore" items="${AllGetStore }">
        		<div class="reserveDe">
	        		<div class="reserveInfo ">
	        			<div class="email">${getStore.storeemail }</div>
	        			<div>${getStore.storename }</div>
	        			<div>${getStore.storetel }</div>
	        			<div>${getStore.storeaddress1}</div>
	        			<div>${getStore.storepostcode }</div>
	        			<div>${getStore.store_joindate }</div>
	        		</div>
        			
        			<div class="InfoDetail">
	        			<div class="reserveInfo1">
	        				<div>클래스 정보</div>
	        				<div>소요시간</div>
	        				<div>난이도</div>
	        				<div>예약현황</div>
	        				<div>세일 여부</div>
	        			</div>
	        			<div class="line"></div>
	        			<div class = "reserveInfo2">
	        				<div class="reserveInfo3">
	        					<div class="class_pic"> 사진 </div>
	        					<div class="class_name">
	        						<div>카테고리</div>
	        						<div>클래스명</div>
	        						<div>가격</div>
	        						<div>평점</div>
	        					</div>
	        				</div>
	        				<div class="reserveInfo4">소요시간</div>
	        				<div class="reserveInfo4">난이도</div>
	        				<div class="reserveInfo5">예약 현황</div>
	        				<div class="reserveInfo4">세일 여부</div>
	        			</div>
        			</div>
        		<hr>
        		</div>
        		</c:forEach>
        		<!-- 반복 -->
        		
        	</div>
        </div>
	</div>

</section>
<jsp:include page="../footer.jsp" />
<script type="text/javascript">

	$(".reserveDe").on("click",function(){
		
		
		var email = $(this).find(".email").text();
		console.log(email);
		
		$.ajax({
			type : "post",
			url : "${pageContext.request.contextPath}/GetClass.do",
			dataType : "text",
			data : {email : email},
			success : function(data,status){
				var result = JSON.parse(data);
				var all = result.all;
				
				if(all.length != 0){
					for(var i = 0; i < all.length ; i++){
						
					}
				}
				
			},
			error : function (data,status){
				alert("에러가 발생했습니다.");
			}
		});
		
		
		
		
		
		
		
		
		if($(this).children(".detail").css("display")=="none"){
			$(this).children(".detail").css("display","block");
			$(this).children('.detail').animate({height : "450px"},500);
			
		}else{
			$(this).children(".detail").css("display","none");
			$(this).children('.detail').animate({height : "450px"},500);
		}
		
		
		
		
		
		
	});


</script>
</body>
</html>