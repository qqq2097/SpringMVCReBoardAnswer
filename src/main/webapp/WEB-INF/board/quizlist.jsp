<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://fonts.googleapis.com/css2?family=Dongle:wght@300&family=Gamja+Flower&family=Hi+Melody&family=Titillium+Web:wght@200&display=swap" rel="stylesheet">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<title>Insert title here</title>
</head>
<body>

<div style="width: 800px; text-align: right; margin: 50px 50px; font-size: 2em;" >
<span class="glyphicon glyphicon-th-list list1" 
style="cursor: pointer;"></span>
<span class="glyphicon glyphicon-th-large list2"
 style="cursor: pointer;"></span>
</div>

<div class="resultlist" style="margin-top: 30px; margin-left: 50px;">

</div>

<script type="text/javascript">
$("span.list1").click(function () {
	//$(".resultlist").html("출력");
	$("span.list1").css("color", "red");
	$("span.list2").css("color", "black");
	
	$.ajax ({
		type: "get",
		dataType: "json",
		url: "list1", //controller 주소
		success: function (data) {
			var tag = "";
			tag+="<table class='table table-bordered' style='width: 700px;'>"
			$.each(data, function(i,elt){
				tag+="<tr height='100'><td>";
				tag+="<h3><b>"+elt.subject+"</b></h3>";
				tag+=elt.content+"<br>";
				
				if(elt.photo!='no'){
					tag+="<span style='float: right'> ";
					tag+="<img width='100' height='100' src='../photo/"+elt.photo+"'><br>";
					tag+="</span>";
				}
				
				tag+="작성자: "+elt.writer;
				tag+="</td></tr>";
				
			});
			
			tag+="</table>";
			$(".resultlist").html(tag);
			
		}
		
		
	});
	
});

$("span.list2").click(function () {
	//$(".resultlist").html("출력2");
	$("span.list2").css("color", "red");
	$("span.list1").css("color", "black");
	
	$.ajax({
		type: "get",
		dataType: "json",
		url: "list1",
		success: function (data) {
			var tag ="";
			tag+="<table class='table table-bordered' style='width=800px;'>";
			tag+="<tr>";
			var n =0;
			$.each(data, function (i, elt) {
				if(elt.photo!='no'){
					n++;
					tag+="<td width='200'>";
					tag+="<img width='200' height='200' src='../photo/"+elt.photo+" '><br>";
					tag+="작성자:"+elt.writer;
					tag+="</td>";
					if(n%4==0){
						tag+="</tr>";
					}
				}
			});
			
			tag+="</table>";
			$(".resultlist").html(tag);
			
		}
	});
	
	
	
});


</script>

</body>
</html>