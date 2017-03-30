layui.config({
	base: contextPath+'/static/common/js/',
	version:new Date().getTime()
}).use(['element','code','layer','form','msg'], function(){
	var  element = layui.element(),
	$ = layui.jquery,
	layer = layui.layer,
	msg = layui.msg,
	form = layui.form();
	layui.code();
	
	form.on('checkbox(param_check)', function(data){
		var name =$(data.elem).parent().prev().prev().find("input").val();
		var value = $(data.elem).parent().prev().find("input").val();
		var paramStr = "&"+name+"="+value;
		var urlStr = $("#urlStr").val();
		if(!data.elem.checked){
			urlStr = urlStr.replace(paramStr, "");
			$("#urlStr").val(urlStr);
		}else{
			$("#urlStr").val(urlStr+paramStr);
		}
	});
	
	$('input[name="param_value"]').bind('input propertychange', function() { 
		 //进行相关操作 
		var name =$(this).parent().prev().find("input").val();
		var value = $(this).val();
		var paramStr = "&"+name+"="+value;
		var urlStr = $("#urlStr").val();
		var oneSearch = urlStr.indexOf("&"+name);
		var twoSearch = urlStr.indexOf("&",oneSearch+1);
		if(twoSearch === -1){
			twoSearch = urlStr.length;
		}
		var ss =urlStr.substring(oneSearch,twoSearch);
		urlStr = urlStr.replace(ss, paramStr);
		$("#urlStr").val(urlStr);
	});
	$(".params-button").on('click',function(){
		$(".table-params").toggle();
	});
	$(".test-run").on('click',function(){
		var layIndex =msg.load();
		var apitype = $("#test-apiType").val();
		$.ajax({
			  url: contextPath+"/api/test-run.htm",
			  data:{url:$("#urlStr").val(),apiType:apitype},
			  type:"post",
			  dataType: "json",
			  success: function(data){
				  msg.close(layIndex);
				  if(apitype == 2){
					  $("#test-img").attr("src","/temp/"+data+".gif");
				  }else{
					  try {
						  var jsonObj = JSON.parse(data);
						  if(jsonObj.head.rtnCode === "900003" && $(".test-fail").length !=0){
							  $(".test-fail").show();
						  }
						  $(".layui-textarea").val(data);
					  }catch(e) {
						  $(".layui-textarea").val(data);
				      }
				  }
				 
			  }
		});
	});
	$(".test-fail a").on('click',function(){
		  var projectId = $('#test-projectId').val();
		  var layIndex =msg.loading();
		  $.ajax({
			  url: contextPath+"/api/auto-login.htm",
			  data:{projectId:projectId},
			  type:"post",
			  dataType: "json",
			  success: function(data){
				  $(".test-fail").hide();
				  msg.close(layIndex);
				  msg.alertData(data);
			  }
		});
	});
  //…
});