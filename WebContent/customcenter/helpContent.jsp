<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/customcenter/help.css">
</head>
<body>
<script type="text/javascript">
	function deletePage(num,pageNum){
		if (confirm("삭제하시겠습니까?")) {
			$.ajax('${pageContext.request.contextPath}/helpDelete.do',{
				type:"post",
				data: {number : num},
				success:function(data){
					if(data==1){
						alert("삭제했습니다.");
						location.href="${pageContext.request.contextPath}/helpMainPage.do?pageNum="+pageNum;
					}else{
						alert("오류가 발생했습니다.");
					}
				},
				error:function(e){
					alert("에러가 발생했습니다.");
				}
			});
        }
	}
	function commentWrite(num,pageNum){
		var form = $("#commentWriteForm").serialize();
		var comment = $("#comment").val();
		if(comment ==""){
			alert("덧글을 입력해주세요");
		}else{
			$.ajax('${pageContext.request.contextPath}/helpCommentWrite.do',{
				type:"post",
				data: form,
				success:function(data){
					if(data==1){
						location.href="${pageContext.request.contextPath}/helpPage.do?num="+num +"&pageNum="+pageNum;
					}else{
						alert("오류가 발생했습니다.");
					}
				},
				error:function(e){
					alert("에러가 발생했습니다.");
				}
			});
		}
	}
	function deleteComment(commentnum, num,pageNum){
		if (confirm("삭제하시겠습니까?")) {
			$.ajax('${pageContext.request.contextPath}/helpCommentDelete.do',{
				type:"post",
				data: {number : commentnum},
				success:function(data){
					if(data==1){
						alert("삭제했습니다.");
						location.href="${pageContext.request.contextPath}/helpPage.do?num="+num +"&pageNum="+pageNum;
					}else{
						alert("오류가 발생했습니다.");
					}
				},
				error:function(e){
					alert("에러가 발생했습니다.");
				}
			});
        }
	}
	function updateComment(commentnum, num,pageNum){
		$.ajax('${pageContext.request.contextPath}/helpCommentUpdate.do',{
			type:"post",
			data: {number : commentnum},
			success:function(data){
				if(data==1){
					alert("삭제했습니다.");
					location.href="${pageContext.request.contextPath}/helpPage.do?num="+num +"&pageNum="+pageNum;
				}else{
					alert("오류가 발생했습니다.");
				}
			},
			error:function(e){
				alert("에러가 발생했습니다.");
			}
		});
	}
</script>
<script src="https://code.jquery.com/jquery-3.1.0.js"></script>
<jsp:include page="../header.jsp"/>
<section>
<div class="pic"></div>
    <div class="my_wrap">
        <div class="side_menu">
           <div class="side_detail">
           <div class="current_menu">문의하기</div>
           <div class="line"></div>
                <a href="${pageContext.request.contextPath}/helpMainPage.do" class="current_menu2"><div>문의하기</div></a>
            </div>
            <hr>
            <div class="side_detail">
               <a href="${pageContext.request.contextPath}/faqMainPage.do"class="current_menu3"><div>FAQ</div></a>
            </div>
            <hr>
        </div>
        <div class="my_main">
       		<p><h2>${bean.title}</h2><br><hr>
       		<p><h2>내용</h2><br>${bean.content}<br><hr>
       		<button type="button" onclick ="location.href='${pageContext.request.contextPath}/helpMainPage.do?pageNum=${pageNum}'">목록으로</button>
       		<button type="button" onclick ="location.href='${pageContext.request.contextPath}/helpUpdate.do?num=${bean.num}&pageNum=${pageNum}'">수정하기</button>
       		<button type="button" onclick ="deletePage('${bean.num}','${pageNum}')">삭제하기</button><br><br>
       		<div class="comments">
       			<c:if test="${count !=0 }">
       				<form id ="commentForm" method="post">
       					<table border="1px solid">
       						<tr>
       							<td>이메일</td><td></td><td>날짜</td><td></td>
       						</tr>
       						<c:forEach var = "v" items="${list}">
	       						<tr id ="comment_show">
	       							<td>${v.id }</td><td>${v.comment }</td><td>${v.date }</td>
	       							<td><input id = "update" onclick ="updateComment('${v.commentnum}','${bean.num}','${pageNum}')" type="button" value ="수정하기">&nbsp;
	       							<input type="button" onclick="deleteComment('${v.commentnum}','${bean.num}','${pageNum}')" value ="삭제하기"></td>
	       						</tr>
       						</c:forEach>
       					</table>
       				</form>
       			</c:if>
       		</div>
       		<div class ="comment_input">
	        	<form id = "commentWriteForm" method="post">
	        		<input type ="hidden" name ="num" value="${bean.num}">
	        		<input type = "text" id = "comment" name="comment"> &nbsp;<input type="button" onclick="commentWrite('${bean.num}','${pageNum}')" value="덧글달기">
	        	</form>
        	</div>
        </div>
    </div>
</section>
</body>
</html>