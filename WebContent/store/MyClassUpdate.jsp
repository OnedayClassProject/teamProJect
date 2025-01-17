<%--
  Created by IntelliJ IDEA.
  User: gyu
  Date: 2020/08/17
  Time: 11:36 오후
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://code.jquery.com/jquery-3.1.0.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
	<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/store/classCreate.css">
    <script>
        var sel_file;
        $(document).ready(function () {
            $("#input_img").on("change", handleImgFileSelect);

            $(".sale").on("click", function () {
            	$(".sale1").empty();
                $(".sale1").append("<input type='text' name='sale' id='input_sale'>%");
                $("#sale_check").val("true");
            });
            $(".nosale").on("click", function () {
            	$("#sale_check").val("fal");
            	$(".sale1").empty();
			});
            $("#class_registry").on('click', function () {
            	 var productContent = CKEDITOR.instances["p_content"].getData();
                 $('#p_content').val(productContent);
            	var reg1 = /^[0-9]{1,3}$/;
            	var reg2 = /^[0-9]{1,7}$/;
            	
            	var result = reg1.test($("#hour").val())
            	var result1 = reg1.test($("#minute").val())
            	var result2 = reg1.test($("#class_personal").val())
            	var result3 = reg2.test($("#price").val())
          		var result4 = reg1.test($("#input_sale").val())
            	if($("#classname").val().trim() == ""){
            		alert("클래스명 입력해주세요.");
            		return;
            	} else if($("#hour").val().trim() == ""){
            		alert("시간을 입력해주세요.");
            		return;
            	} else if($("#minute").val().trim() == ""){
            		alert("분을 입력해주세요.");
            		return;
            	} else if(result != true){
            		alert("시간 : 숫자만 입력해주세요.");
            		return;
            	} else if(result1 != true){
            		alert("분  : 숫자만 입력해주세요.");
            		return;
            	} else if($("#class_personal").val().trim() == ""){
            		alert("인원수를 입력해주세요.");
            		return;
            	} else if(result2 != true){
            		alert("인원수 : 숫자만 입력해주세요.");
            		return;
            	} else if($("#price").val().trim() == ""){
            		alert("가격을 입력해주세요.");
            		return;
            	} else if(result3 != true){
            		alert("가격 : 숫자만 입력해주세요.");
            		return;
            	} else if($("#sale_check").val() == "true"){
            		if($("#input_sale").val().trim() == ""){
            			alert("할인창에 입력해주세요.");
            			return;
            		} else if(result4 != true){
            			alert("할인 : 숫자만 입력해주세요.");
            			return;
            		}
            	} else if($('#p_content').val().trim() == ""){
            		alert("내용을 입력해주세요");
            		return;
                } else {
            	 var form = $("form")[0];
                 var form1 = new FormData(form);
                 $.ajax({
                     type: "post",
                     url: "${pageContext.request.contextPath}/classUpdatePro.do",
                     processData: false,
                     contentType: false,
                     data: form1,
                     dataType: "text",
                     success:function(data, status){
							  if(data == 1){                  	 
								  alert("운영시간을 등록해주세요!!!!");
	                    	 		 $("#box").css("display","block");
	                                 $("#calander_backcolor").css("display","block");
	                                 x();
			                     	} else {
			                     	alert('실패!!!!ㅜㅜㅜㅜㅜㅜㅜ');
			                    	}
                     	},
                     	error:function(data,status){
                    		 alert("에러");
                     	}
                     	
                     });
                	  
                }
            
        	});
            $("#cancle_back").on("click", function () {
				history.back();
        	});
            
        });
        function handleImgFileSelect(e) {
            var files = e.target.files;
            var filesArr = Array.prototype.slice.call(files);

            filesArr.forEach(function (f) {
                if (!f.type.match("image.*")) {
                    alert("확장자는 이미지 확장자만 가능합니다.");
                    return;
                }
                sel_file = f;
                var reader = new FileReader();
                reader.onload = function (e) {
                    $("#img").attr("src", e.target.result);
                }
                reader.readAsDataURL(f);
            });
        }

        function fileCheck(obj) {
            var file_kind = obj.value().lastIndexOf('.');
            var file_name = obj.value().substring(file_kind + 1, obj.length);
            var file_type = file_name.toLowerCase();

            var check_file_type = new Array();
            check_file_type = ['jpg', 'gif', 'png', 'jpeg'];
            if (check_file_type.indexOf(file_type) == -1) {
                alert('이미지 파일만 선택 할 수 있습니다.');
                var parent_Obj = obj.parentNode;
                var node = parent_Obj.replaceChild(obj.cloneNode(true).obj);
                return false;
            }
        }
    </script>
</head>
<body>
<div id="calander_backcolor"></div>
<jsp:include page="/header.jsp"/>
<c:if test="${empty sessionScope.storeid }">
<script type="text/javascript">
	alert("로그인해주세요!!");
    location.href="${pageContext.request.contextPath}/login.do";
</script>
</c:if>
<section>
<form method="post" enctype="multipart/form-data">
<div class="class_wrap">
		<div>클래스 수정</div>
    	<div class="class_menu">
	        <div class="class_title" >클래스명</div>
	        <div><input type="text" name="classname" id="classname" value="${cbean.class_name }"></div>
	    </div>
	    <div class="class_menu">
	        <div class="class_title"  >카테고리</div>
	        <div>
			${cbean.category}
	        </div>
	        <input type="hidden" name="category" value="${cbean.category }">
	    </div>
	    <div class="class_menu">
	        <div class="class_title" >업체명</div>
	        <div>${cbean.class_company }</div>
	        <input type="hidden" name="class_company" value="${cbean.class_company }">
	   </div>
	   <div class="class_menu">
        <div  class="class_title"  >업체위치</div>
        <div>${cbean.location } </div>
        <input type="hidden" name="location" value="${cbean.location }">
        </div>
        <div class="class_menu">
        <div  class="class_title"  >난이도</div>
        <div>
            <select name="level">
                <option value="hard">상</option>
                <option value="nomal">중</option>
                <option value="easy">하</option>
            </select>
        </div>
        </div>
        <div class="class_menu">
        <div class="class_title"  >소요시간</div>
        <div><input type="text" name="hour" id="hour" class="class_input">시간 <input type="text" class="class_input" name="minute" id="minute">분</div> 
        </div>
        <div class="class_menu">
        <div class="class_title"  >수업인원</div>
        <div><input type="text" class="class_input class_per" name="class_personal" id="class_personal" value="${cbean.personnel}">명</div>
        </div>
        <div class="class_menu">
        <div class="class_title"  >가격</div>
        <div><input type="text" class="class_input" name="price" id="price" value="${cbean.price}">원</div>
        </div>
       <!--  <tr>
        <td class="class_title"  colspan="3">할인여부</td>
        <td class="sale">적용</td>
        <input type="hidden" value="fal" id="sale_check">
        <td class="nosale">안함</td>
        <td class="sale1"></td>
        </tr> -->
        <div class="class_menu">
        <div class="class_title" >주차장여부</div>
        <div><input type="text" name="parking" value="${cbean.parking}"></div>
        </div>
        <div class="class_menu">
        <div class="class_title class_content" >글쓰기</div>
        <div ><textarea id="p_content" name="content">${cbean.content}</textarea></div>
        </div>
        <div class="class_menu">
        <div class="class_title class_img" >대표이미지</div>
        <div class="file_box">
            <label for="input_img">+</label>
            <input type="file" accept="image/jpg,image/jpeg,image/png,image/gif," name="image" id="input_img"
                   onchange="fileCheck(this)" value="${pageContext.request.contextPath}/thumbnailImage/${cbean.thumbnail }">
        </div>
        <div class="imgMain">
            <img id="img" src="${pageContext.request.contextPath}/thumbnailImage/${cbean.thumbnail }"/>
        </div>
        </div>
        <input type="hidden" name="storenum" value="${cbean.storenum }">
        <input type="hidden" name="class_registrynum" class="class_regNum" value="${cbean.class_registrynum }">
        <div class="class_buttons">
        <button type="button" id="class_registry">수정완료</button>
        <button type="button" id="cancle_back">취소</button>
        </div>
    </div>
    <div id="box">
        <div class="content-right">
            <table id="calendar">
                <thead>
                <tr class="btn-wrap clearfix">
                    <td>
                        <label id="prev">
                            &#60;
                        </label>
                    </td>
                    <td align="center" id="current-year-month" colspan="5"></td>
                    <td>
                        <label id="next">
                            &#62;
                        </label>
                    </td>
                </tr>
                <tr>
                    <div class="main">
                        <td class="sun" align="center">Sun</td>
                        <td align="center">Mon</td>
                        <td align="center">Tue</td>
                        <td align="center">Wed</td>
                        <td align="center">Thu</td>
                        <td align="center">Fri</td>
                        <td class="sat" align="center">Sat</td>
                     
                </tr>
                </thead>
                <tbody id="calendar-body" class="calendar-body"></tbody>
            </table>
        </div>
        <div class="time">
            <div class="time_main">
                <div id="dateInput"></div>
                <div class="sub_plus">
                    <div id="plus">+</div>
                    <div id="sub">-</div>
                </div>
            </div>
            <div class="timesetting"></div>
        </div>
        <div class="store">
            
        </div>
    </div>
</div>
</form>
</section>
<script src="${pageContext.request.contextPath}/ckeditor/ckeditor.js"></script>
<script>
    $(function () {
        CKEDITOR.replace('p_content', {
            height: 350,
            removePlugins: 'resize',
            filebrowserBrowseUrl : '${pageContext.request.contextPath}/ckfinder/ckfinder.html',
        	filebrowserFlashBrowseUrl : "${pageContext.request.contextPath}/ckfinder/ckfinder.html?type=Flash",
        	filebrowserUploadUrl : "${pageContext.request.contextPath}/ckfinder/core/connector/java/connctor.java?command=QuickUpload&type=Files",
        	filebrowserImageUploadUrl : "${pageContext.request.contextPath}/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images",
        	filebrowserFlashUploadUrl : "${pageContext.request.contextPath}/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash"
        });
    })
    var currentTitle = document.getElementById('current-year-month');
    var calendarBody = document.getElementById('calendar-body');
    var today = new Date();
    var first = new Date(today.getFullYear(), today.getMonth(), 1);
    var dayList = ['Sunday', 'Monday', 'Tuesday', 'Wednesday',
        'Thursday', 'Friday', 'Saturday'];
    var monthList = ['January', 'February', 'March', 'April', 'May',
        'June', 'July', 'August', 'September', 'October',
        'November', 'December'];
    var leapYear = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    var notLeapYear = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    var pageFirst = first;
    var pageYear;
    if (first.getFullYear() % 4 === 0) {
        pageYear = leapYear;
    } else {
        pageYear = notLeapYear;
    }

    /* 달력을 보여주는 작업 */
    function showCalendar() {
        var current_year_month = document
            .getElementById('current-year-month');
        current_year_month.innerText = first.getFullYear()
            + "-"
            + (first.getMonth() + 1 > 9 ? first.getMonth() + 1
                : "0" + (first.getMonth() + 1));

        let monthCnt = 100;
        let cnt = 1;
        for (var i = 0; i < 6; i++) {
            var $tr = document.createElement('tr');
            $tr.setAttribute('id', monthCnt);
            for (var j = 0; j < 7; j++) {
                if ((i === 0 && j < first.getDay())
                    || cnt > pageYear[first.getMonth()]) {
                    var $td = document.createElement('td');
                    $tr.appendChild($td);
                } else {
                    var $td = document.createElement('td');
                    $td.textContent = cnt;
                    $td.setAttribute('id', cnt);
                    var yyyy = first.getFullYear();
                    var MM = first.getMonth() + 1;
                    MM = MM < 10 ? "0" + MM : MM;
                    var dd = cnt;
                    dd = dd < 10 ? "0" + dd : dd;
                    $td.setAttribute('value', yyyy + "-" + MM + "-" + dd);
                    $td.setAttribute("class", 'abled_td');
                    /* $td.setAttribute('value', first.getFullYear() + "-"
                            + ((first.getMonth() + 1)<10?"0"+(first.getMonth() + 1):(first.getMonth() + 1)) + "-" + cnt <10?"0"+cnt:cnt); */

                    $td.onclick = function () {
                        if (new Date(this.getAttribute("value") + " 23:59:59") < new Date()) {
                            alert("이전 날짜로 예약 할 수 없습니다.");
                            return;
                        }
                        document.getElementById("dateInput").textContent = this.getAttribute("value");
                        document.getElementById("sub").style.display = "inline-block";
                        document.getElementById("plus").style.display = "inline-block";
                        getTime();
                        var tds = document.querySelectorAll('.abled_td');
                        for (var i = 0; i < tds.length; i++) {
                            tds[i].setAttribute("class", "abled_td");
                        }
                        this.setAttribute("class", "abled_td clicked_td");
                    };
                    $tr.appendChild($td);
                    cnt++;
                    // console.log($td.getAttribute('value'));
                }
            }
            monthCnt++;
            calendarBody.appendChild($tr);
        }
    }

    showCalendar();

    /* 화면에있던 기존의 달력을 지우는 작업 */
    function removeCalendar() {
        let
            catchTr = 100;
        for (var i = 100; i < 106; i++) {
            var $tr = document.getElementById(catchTr);
            $tr.remove();
            catchTr++;
        }
    }

    /* > 버튼 클릭시 */
    var nextBtn = document.getElementById("next");
    nextBtn.addEventListener("click", function () {
        removeCalendar();
        first = new Date(today.getFullYear(), today.getMonth() + 1, 1);
        showCalendar();
        today = new Date(today.getFullYear(), today.getMonth() + 1, 1);
        x();
    });

    /* < 버튼 클릭시 */
    var prevBtn = document.getElementById("prev");
    prevBtn.addEventListener("click", function () {
        removeCalendar();
        first = new Date(today.getFullYear(), today.getMonth() - 1, 1);
        showCalendar();
        today = new Date(today.getFullYear(), today.getMonth() - 1, 1);
        x();
    })
    var plus = document.getElementById("plus");
    $("#plus").on("click", function name() {
    	if ($(".timeSet").length <= 5) {
    		$(".timesetting").empty();
            $(".timesetting").append("<div class='timeSet'><input type=\"text\" id='time_start1' class='timeStart'> ~ <input type=\"text\" id='time_end1' class='timeend'></div>"
            						+"<div class='timeSet'><input type=\"text\" id='time_start2' class='timeStart'> ~ <input type=\"text\" id='time_end2' class='timeend'></div>"
            						+"<div class='timeSet'><input type=\"text\" id='time_start3' class='timeStart'> ~ <input type=\"text\" id='time_end3' class='timeend'></div>"
            						+"<div class='timeSet'><input type=\"text\" id='time_start4' class='timeStart'> ~ <input type=\"text\" id='time_end4' class='timeend'></div>"
            						+"<div class='timeSet'><input type=\"text\" id='time_start5' class='timeStart'> ~ <input type=\"text\" id='time_end5' class='timeend'></div>"
            						+"<div class='timeSet'><input type=\"text\" id='time_start6' class='timeStart'> ~ <input type=\"text\" id='time_end6' class='timeend'></div>");
            document.getElementById('time_start1').flatpickr(
            		{ enableTime: true,
                noCalendar: true,
                dateFormat: "H:i",
            		});
            document.getElementById('time_end1').flatpickr({
                enableTime: true,
                noCalendar: true,
                dateFormat: "H:i",
            });  
            document.getElementById('time_start2').flatpickr(
            		{ enableTime: true,
                noCalendar: true,
                dateFormat: "H:i",
            		});
            document.getElementById('time_end2').flatpickr({
                enableTime: true,
                noCalendar: true,
                dateFormat: "H:i",
            });  
            document.getElementById('time_start3').flatpickr(
            		{ enableTime: true,
                noCalendar: true,
                dateFormat: "H:i",
            		});
            document.getElementById('time_end3').flatpickr({
                enableTime: true,
                noCalendar: true,
                dateFormat: "H:i",
            });  
            document.getElementById('time_start4').flatpickr(
            		{ enableTime: true,
                noCalendar: true,
                dateFormat: "H:i",
            		});
            document.getElementById('time_end4').flatpickr({
                enableTime: true,
                noCalendar: true,
                dateFormat: "H:i",
            });  
            document.getElementById('time_start5').flatpickr(
            		{ enableTime: true,
                noCalendar: true,
                dateFormat: "H:i",
            		});
            document.getElementById('time_end5').flatpickr({
                enableTime: true,
                noCalendar: true,
                dateFormat: "H:i",
            });  
            document.getElementById('time_start6').flatpickr(
            		{ enableTime: true,
                noCalendar: true,
                dateFormat: "H:i",
            		});
            document.getElementById('time_end6').flatpickr({
                enableTime: true,
                noCalendar: true,
                dateFormat: "H:i",
            });  
        }
	});
        
    
    $("#sub").on("click", function () {
        if ($(".timeSet").eq(0).length == 0) {
            alert("추가해주세요~");
            return;
        }
        $(".timeSet").last().remove();
    });
    function x(){
        console.log($(".abled_td").length);
      	var num = ${cbean.class_registrynum};
        var date= $("#current-year-month").text();
         $.ajax({
            type: "post",
            url: "${pageContext.request.contextPath}/timeSetting.do",
            dataType: "text",
            data: { date : date , num :  num},
             success:function (data, status) {
            	 var result = JSON.parse(data);
            	 var all = result.all;
            	 if(all.length != 0 ){
                  for(var i = 0; i < all.length; i++){
                	  $(".abled_td").eq(all[i].day - 1).css("background","#FACB87");
                	  $(".abled_td").eq(all[i].day - 1).css("border-radius","50%");
                  }                
                  }
             },
             error:function(data,status){
            	 alert('error');
             }
         }); 
    } 
    
    //시간수정
    function update() {
    	var date  = $("#current-year-month").text();
    	var day = $("#dateInput").text().substring(8);
    	var num = ${cbean.class_registrynum };
    	var classper = $('.class_per').val();
    	var start = []
    	var end = []
    	var calNum =[]
		 for(var i=0; i < $('.time_Start').length; i++){
			start[i] = $('.time_Start').eq(i).val();
			end[i] = $('.time_end').eq(i).val();
			calNum[i] = $('.getCalNum').eq(i).val();
			
		} 
		
		$.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/timeUpdate.do",
			data : { "start" : start, "end" : end,"date":date , 
					"day" : day, "num" : num, "personal" : classper,
					"calNum":calNum},
			dataType: "text",
			traditional : true,
			success:function(data, status){
				if(data == 1){
					alert('수정성공');
					x();
				} else {
				 	alert('저장실패');
				}
			},
			error:function(data, status){
				alert('error');
			}
		});
	};
	//날짜선택시 시간 가져오기
	function getTime(){
	var date  = $("#current-year-month").text();
    var day = $("#dateInput").text().substring(8);
    var num = ${cbean.class_registrynum}
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/saveGetTime.do",
		data : {day : day , num : num, date : date },
		dataType : "text",
		success:function (data, status){
			var result = JSON.parse(data);
			var time = result.time;
			$(".timesetting").empty();
			if(time.length != 0){
			for(var i = 0; i < time.length; i++){
				$(".timesetting").append("<div class='timeSet'>"
								+"<input type=\"text\" class='time_start' value='"+time[i].start+"'> ~ "
								+"<input type=\"text\" class='time_end' value='"+time[i].end+"'>"
								+"<input type='hidden' class='getCalNum' value='"+time[i].calnum+"'></div>");
			}
			$('.store').empty();
			$('.store').append("<input type='button' class='update' onclick='update()' value='수정'>"
					            +"<input type='button' class='delete' onclick='delete1()' value='삭제'>" 
					            +"<input type='button' onclick='main()' value='메인'>"
					            +"<input type='button' onclick='close1()' value='닫기'>")
			} else {
				$('.store').empty();
				$('.store').append("<input type='button' class='save' onclick='save()' value='저장'>"
						+"<input type='button' onclick='main()' value='메인'>"
			            +"<input type='button' onclick='close1()' value='닫기'>")
			}
		},
		error:function(data,status){
			alert('error');
		}
	});
	}
	  //저장한 시간삭제
    function delete1() {
    	var date  = $("#current-year-month").text();
    	var day = $("#dateInput").text().substring(8);
    	var num = ${cbean.class_registrynum };
    	var classper = $('.class_per').val();
    	var start = []
    	var end = []
    	var calNum =[]
		 for(var i=0; i < $('.time_Start').length; i++){
			start[i] = $('.time_Start').eq(i).val();
			end[i] = $('.time_end').eq(i).val();
			calNum[i] = $('.getCalNum').eq(i).val();
			
		} 
		
		$.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/timeDelete.do",
			data : { "start" : start, "end" : end,"date":date , 
					"day" : day, "num" : num, "personal" : classper,
					"calNum":calNum},
			dataType: "text",
			traditional : true,
			success:function(data, status){
				if(data == 1){
					alert('시간삭제');
					$(".timesetting").empty();
					$("#"+day).css("background", "white");
					$('.store').empty();
					$('.store').append("<div class='save' onclick='save()'>저장</div>"
							+"<input type='button' onclick='main()' value='메인'>"
				            +"<div onclick='close1()'>닫기</div>")
				} else {
				 	alert('저장실패');
				}
			},
			error:function(data, status){
				alert('error');
			}
		});
	};
	  //시간수정
    function save() {
    	var date  = $("#current-year-month").text();
    	var day = $("#dateInput").text().substring(8);
    	var num = ${cbean.class_registrynum };
    	var classper = $('.class_per').val();
    	var start = []
    	var end = []
    	var calNum =[]
		 for(var i=0; i < $('.timeStart').length; i++){
			start[i] = $('.timeStart').eq(i).val();
			end[i] = $('.timeend').eq(i).val();
		} 
		
		$.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/timeSave.do",
			data : { "start" : start, "end" : end,"date":date , 
					"day" : day, "num" : num, "personal" : classper},
			dataType: "text",
			traditional : true,
			success:function(data, status){
				if(data == 1){
					alert('저장성공');
					x();
				} else {
				 	alert('시간을 입력해주세요!');
				}
			},
			error:function(data, status){
				alert('error');
			}
		});
	};
	function close1() {
		console.log("close");
		$("#box").css("display", "none");
        $("#calander_backcolor").css("display", "none");
	}
	function main(){
		location.href='${pageContext.request.contextPath}/main.do';
	}
</script>
</body>
</html>
