<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/classList/gangwonClass.css">
</head>
<body><jsp:include page="../header.jsp"/>
<section>
<div class="pic"></div>
    <div class="my_wrap">
        <div class="side_menu">
           <div class="side_detail">
           <div class="current_menu">강원도</div>
           <div class="line"></div>
                <a href="${pageContext.request.contextPath}/seoulClass.do" class='current_menu3'><div>서울</div>
                <div class="side_detail2">></div>
                </a>
            </div>
            <hr>
            <div class="side_detail">
               <a href="${pageContext.request.contextPath}/gyunggiClass.do" class='current_menu3'><div>경기</div>
                <div class="side_detail2">></div>
                </a>
            </div>
            <hr>
            <div class="side_detail">
               <a href="${pageContext.request.contextPath}/busanClass.do" class='current_menu3'><div>부산</div>
                <div class="side_detail2">></div>
                </a>
            </div>
            <hr>
            <div class="side_detail">
               <a href="${pageContext.request.contextPath}/daeguClass.do" class='current_menu3'><div>대구</div>
                <div class="side_detail2">></div>
                </a>
            </div>
            <hr>
            <div class="side_detail">
               <a href="${pageContext.request.contextPath}/daejeonClass.do" class='current_menu3'><div>대전</div>
                <div class="side_detail2">></div>
                </a>
            </div>
            <hr>
            <div class="side_detail">
               <a href="${pageContext.request.contextPath}/gyeongsangClass.do" class='current_menu3'><div>경상도</div>
                <div class="side_detail2">></div>
                </a>
            </div>
            <hr>
            <div class="side_detail">
               <a href="${pageContext.request.contextPath}/chungcheongClass.do" class='current_menu3'><div>충청도</div>
                <div class="side_detail2">></div>
                </a>
            </div>
            <hr>
            <div class="side_detail">
               <a href="${pageContext.request.contextPath}/jeollaClass.do" class='current_menu3'><div>전라도</div>
                <div class="side_detail2">></div>
                </a>
            <hr>
            </div>
            <div class="side_detail">
               <a href="${pageContext.request.contextPath}/gangwonClass.do" class='current_menu3'><div>강원도</div>
                <div class="side_detail2">></div>
                </a>
            <hr>
            </div>
            <div class="side_detail">
               <a href="${pageContext.request.contextPath}/jejuClass.do" class='current_menu3'><div>제주도</div>
                <div class="side_detail2">></div>
                </a>
            </div>
            <hr>
        </div>
            <div class="my_main">
            <div>강원도</div>
            
            <c:set var="i" value="1"/>
            
            <c:forEach var="ClassBean" items="${requestScope.Vector}">
            
           	<div class="best-class">
				<div class="thumbnail">
					<a href="${pageContext.request.contextPath}/classInfo.do?class_registrynum=${ClassBean.class_registrynum}">
						<img src="${pageContext.request.contextPath}/thumbnailImage/${ClassBean.thumbnail}">
	           		</a>
				</div>
					<div class="class-name">
						<div class="class-name1">${ClassBean.category}</div>
                        <div class="class-name2">${ClassBean.class_name}</div>
                        <div class="class-name3">${ClassBean.level}
                         <input type="hidden" value="${classBean.class_registrynum}" class="num">
                         	평점  : <img class="like" src="${pageContext.request.contextPath}/images/heart_empty.png">
                         </div>
                    </div>
           	</div>
           	
	            <c:if test="${i%3 == 0}">
	            	<br>
	            </c:if>
	            
	            <c:set var="i" value="${i+1}"/>
            
            </c:forEach>
	          	<div class="pageNum">
	          		<c:forEach var="p" begin="${startPage}" end="${endPage}">
	          			<a href="${pageContext.request.contextPath}/gangwonClass.do?pageNum=${p}">[${p}]</a>
	          		</c:forEach>
	          	</div>
	          			
	          	<c:if test="${cnt == 0 }">
	          		<div class="no_list">LIST가 없습니다.</div>
	          	</c:if>
	          	
            </div>
        </div>
   
</section>
<script type="text/javascript">
		
		// 하트 아이콘 눌렀을 때
		$(".like").on("click",function(){
			var image = $(this).attr("src");
			var num = $(this).prev().val();
			
			console.log(num);
			
			if(image == '${pageContext.request.contextPath}/images/heart_empty.png'){
				
				$.ajax({
					type : "post",
					url : "${pageContext.request.contextPath}/likeOn.do",
					data : {num : num},
					dataType : "text",
					success:function(data,status){
						if(data == 1){
							alert("좋아요 성공");
							$(this).attr("src","${pageContext.request.contextPath}/images/heart_full.png")
						}else{
							alert("로그인 후 눌러주세요.");
						}
					},
					error : function(data,status){
						alert("에러가 발생했습니다.")
					}
				});
			}else if(image == "${pageContext.request.contextPath}/images/heart_full.png"){
				$(this).attr("src","${pageContext.request.contextPath}/images/heart_empty.png")
				$.ajax({
					type : "post",
					url : "${pageContext.request.contextPath}/likeOff.do",
					data : {num : num},
					dataType : "text",
					success : function(data,status){
						if(data == 1){
							alert("좋아요 취소");
						}else{
							alert("실패");
						}
					},
					error : function(data,status){
						alert("에러가 발생했습니다.");
					}
				});
			}
		});
		
		for(var i = 0; i<"${fn:length(list)}"; i++){
			
			var cla = $(".class-name3").eq(i);
			var num = cla.children(".num").val();
			
			console.log("i = "+i);
			console.log("num = "+num);
			
			like();
		}
		function like(){
		$.ajax({
			type : "post",
			url : "${pageContext.request.contextPath}/isLike.do",
			data : {num : num},
			async : false,
			dataType : "text",
			success : function(data,status){
				console.log("data" + data);
				if(data == 1){
					cla.children(".like").attr("src","${pageContext.request.contextPath}/images/heart_full.png")
					console.log(cla.children(".like").attr("src"))
				}
			},
			error: function(data,status){
				alert("에러가 발생했습니다.");
			}
		});
		}
	</script>
</body>
</html>