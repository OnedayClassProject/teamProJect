<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/classList/seoulClass.css">
</head>
<body><jsp:include page="../header.jsp"/>
<section>
<div class="pic"></div>
    <div class="my_wrap">
        <div class="side_menu">
           <div class="side_detail">
           <div class="current_menu">지역별 클래스</div>
           <div class="line"></div>
                 <a href="${pageContext.request.contextPath}/seoulClass.do" class='current_menu2'><div>서울</div>
                </a>
            </div>
            <hr>
            <div class="side_detail">
               <a href="${pageContext.request.contextPath}/gyunggiClass.do" class='current_menu2'><div>경기</div>
                </a>
            </div>
            <hr>
            <div class="side_detail">
               <a href="${pageContext.request.contextPath}/busanClass.do" class='current_menu2'><div>부산</div>
                </a>
            </div>
            <hr>
            <div class="side_detail">
               <a href="${pageContext.request.contextPath}/daeguClass.do" class='current_menu2'><div>대구</div>
                </a>
            </div>
            <hr>
            <div class="side_detail">
               <a href="${pageContext.request.contextPath}/daejeonClass.do" class='current_menu2'><div>대전</div>
                </a>
            </div>
            <hr>
            <div class="side_detail">
               <a href="${pageContext.request.contextPath}/gyeongsangClass.do" class='current_menu2'><div>경상도</div>
                </a>
            </div>
            <hr>
            <div class="side_detail">
               <a href="${pageContext.request.contextPath}/chungcheongClass.do" class='current_menu2'><div>충청도</div>
                </a>
            </div>
            <hr>
            <div class="side_detail">
               <a href="${pageContext.request.contextPath}/jeollaClass.do" class='current_menu2'><div>전라도</div>
                </a>
            <hr>
            </div>
            <div class="side_detail">
               <a href="${pageContext.request.contextPath}/gangwonClass.do" class='current_menu2'><div>강원도</div>
                </a>
            <hr>
            </div>
            <div class="side_detail">
               <a href="${pageContext.request.contextPath}/jejuClass.do" class='current_menu2'><div>제주도</div>
                </a>
            </div>
            <hr>
        </div>

            <div class="my_main">
            <div>서울</div>
         <c:if test="${count != 0}">
            <c:set var="j" value="1"/> 

            <c:forEach var="classBean" items="${list}">
         
           		<div class="best-class">
                 <div class="thumbnail" >
                 <a href="${pageContext.request.contextPath}/ClassInfo.do?class_registrynum=${classBean.class_registrynum}" >
                 <img src="${pageContext.request.contextPath}/thumbnailImage/${classBean.thumbnail}">
                 </a>
                 </div>
                 <div class="class-name">
                         <div class="class-name1">카테고리 : ${classBean.category}</div>
                         <div class="class-name2">클래스명 : ${classBean.class_name}</div>

                         	<input type="hidden" class="rating" value="${classBean.rating }">
                         	<div class = "starRev">
				        	<input class="staR" value="1">
				        	<input class="staR" value="2">
				        	<input class="staR" value="3">
				        	<input class="staR" value="4">
				        	<input class="staR" value="5">
        					</div>

                    </div>
                     <div class="like_image"> 
                 <input type="hidden" value="${classBean.class_registrynum}" class="num">
                  <img class="like" src="${pageContext.request.contextPath}/images/heart_empty.png">
                 <img class="like" src="${pageContext.request.contextPath}/images/star2.png">
                 </div>
            	 </div>
        	   <c:if test="${j%3==0}">
        	   	<br>
        	   </c:if>
             <c:set var="j" value="${j+1}"/>
            </c:forEach>
        	 </c:if>
        	 
            <div class="pageNum">
            	<c:forEach var = "i" begin="${startPage}" end ="${endPage}">
            		<a href="${pageContext.request.contextPath}/seoulClass.do?pageNum=${i}">[${i}]</a>
            	</c:forEach>
            </div>
                   
           </div> 
         
          <c:if test="${count == 0}">
          	<div class="no_list"> NO LIST </div>
          </c:if>


        </div>
</section>
<script type="text/javascript">

		// 하트 아이콘 눌렀을 때
		$(".like").on("click",function(){
			var image = $(this).attr("src");
			var num = $(this).prev(".num").val();
			var likeOn = "${pageContext.request.contextPath}/images/heart_full.png";
			var likeOff = "${pageContext.request.contextPath}/images/heart_empty.png"
			console.log(num);
			
			if( '${sessionScope.userid}' != ""){
			if(image == likeOff){
				$(this).attr("src",likeOn)
				$.ajax({
					type : "post",
					url : "${pageContext.request.contextPath}/likeOn.do",
					data : {num : num},
					dataType : "text",
					async : true,
					success:function(data,status){
						if(data == 1){
							alert("좋아요");
							
						}
					},
					error : function(data,status){
						alert("에러가 발생했습니다.")
					}
				});
			}else if(image == likeOn){
				$(this).attr("src",likeOff)
				$.ajax({
					type : "post",
					url : "${pageContext.request.contextPath}/likeOff.do",
					data : {num : num},
					dataType : "text",
					async : true,
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
			}else{
				alert("로그인 후 눌러주세요.");
			}
		});
		
		for(var i = 0; i<"${fn:length(list)}"; i++){
			
			var cla = $(".like_image").eq(i);
			var num = cla.children(".num").val();
			
			var current = $(".starRev").eq(i);
			var rating  = $(".rating").eq(i).val();
			if(rating == "0"){
				var rating1 = current.children(".staR").eq(rating)
			} else {
			var rating1 = current.children(".staR").eq(rating-1)
				rating1.parent().children("input").removeClass("on");
				rating1.addClass("on").prevAll("input").addClass("on");
			}
			
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
					cla.children(".like").attr("src","${pageContext.request.contextPath}/images/heart_full.png");
					console.log(cla.children(".like").attr("src"));
				}
			},
			error: function(data,status){
				alert("에러가 발생했습니다.");
			}
		});
		}

		/*favor 눌렀을때*/
		$(".favor").on("click",function(){
			var ima = $(this).attr('src');
			var num = $(this).prev().val();
			console.log(ima);
			console.log(num);
			if(ima == '${pageContext.request.contextPath}/images/star2.png'){
				$(this).attr('src','${pageContext.request.contextPath}/images/star1.png');
			$.ajax({
				type:"post",
				url:"${pageContext.request.contextPath}/favorReg.do",
				data:{num:num},
				async: false,
				dataType:"text",
				success : function(data,status){
					console.log(data);
					if(data==1){
						alert('저장성공');
					}else{
						alert('실패');
					}
				},
				error:function(data,status){
					alert('에러발생');
					}
				
			});
			}else if(ima=="${pageContext.request.contextPath}/images/star1.png"){
				$(this).attr('src','${pageContext.request.contextPath}/images/star2.png');
				$.ajax({
					type:"post",
					url:"${pageContext.request.contextPath}/favorCancle.do",
					data:{num:num},
					dataType:"text",
					success:function(data,status){
						if(data==1){
							alert('저장성공');
						}else{
							alert('실패');
						}
					},
					error:function(data,status){
						alert('에러발생');
					}
				});
				
			}
			
		});
		for(var i=0;i< "${fn:length(list)}";i++){
			var cla=$('.class-name3').eq(i);
			var num=cla.children('.num').val();
			console.log(num);
			$.ajax({
				type:"post",
				url : "${pageContext.request.contextPath}/isFavor.do",
				data:{num:num},
				async:false,
				dataType:"text",
				success:function(data,status){
					console.log(data);
					if(data==1){
						cla.children('.favor').attr("src","${pageContext.request.contextPath}/images/star1.png")
					}
				},error:function(data,status){
					alert('에러발생');
				}
			});
		}
	</script>
</body>
</html>