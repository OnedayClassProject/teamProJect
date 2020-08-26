<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/customcenter/help.css">
</head>
<body>
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
        	<form id ="form" method ="post">
        		제목 <input type="text" id ="title" name ="title" style="width: 650px; height: 30px;"><br><br>
        		내용 <br><textarea rows="30" cols="100" id="content" name="content"></textarea><br><br>
        		<button type="button" onclick ="submitForm()">제출하기</button>
        	</form>
        </div>
    </div>
</section>
<script type="text/javascript">
	function submitForm(){
		var title = $("#title").val();
		var content = $("#content").val();
		if(title==""){
			alert("제목을 입력해주세요");
			$("#title").focus();
			return;
		}else if(content==""){
			alert("내용을 입력해주세요");
			$("#content").focus();
			return;
		}else{
			writeAction();
		}
	}
    function writeAction(){
    	var form = $("#form").serialize();
   		$.ajax('${pageContext.request.contextPath}/helpWriteAction.do',{
   			type:"post",
   			data:form,
   			dataType:"text",
   			success:function(data,status){
   				alert(data);
   				console.log(data);
   				console.debug(data);
   				 if(data == 1){
   					location.href="${pageContext.request.contextPath}/helpMainPage.do?pageNum=1";
   				}else{
   					alert("등록 실패.");
   				} 
   			}, error:function(data){
   				alert("에러가 발생했습니다.");
   			}
   		});
    }
</script>
</body>
</html>