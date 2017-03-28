layui.use(['element','code','layer','form'], function(){
	var  element = layui.element(),
	$ = layui.jquery,
	layer = layui.layer,
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
		var apitype = $("#test-apiType").val();
		$.ajax({
			  url: "/api/test-run.htm",
			  data:{url:$("#urlStr").val(),apiType:apitype},
			  type:"post",
			  dataType: "json",
			  success: function(data){
				  if(apitype == 2){
					  $("#test-img").attr("src","/temp/"+data+".gif");
				  }else{
					  try {
						  var jsonObj = JSON.parse(data);
						  if(jsonObj.head.rtnCode != "000000"){
							  
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
		  $.ajax({
			  url: "/api/auto-login.htm",
			  data:{projectId:projectId},
			  type:"post",
			  dataType: "json",
			  success: function(data){
				  layer.alert(data, {icon: 6});
			  }
		});
	});
  //…
});