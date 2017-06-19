layui.config({
	base: contextPath+'/static/common/js/',
	version:new Date().getTime()
}).use(['element','laytpl','code','layer','form','msg'], function(){
	var  element = layui.element(),
	$ = layui.jquery,
	laytpl = layui.laytpl,
	layer = layui.layer,
	msg = layui.msg,
	form = layui.form();
	layui.code();
	
	//历史tab ajax 加载
	element.on('tab(apitab)', function(data){
		 if(data.index == 3){
			 $.ajax({url: contextPath+"/api/run-history.htm",data:{apiId:$("#apiId").val()},type:"post",dataType: "json",
				  success: function(list){
					  if(list && list.length !=0){
						  var data={};
						  data.list = list;
						  var getTpl = recordList.innerHTML;
						  laytpl(getTpl).render(data, function(html){
							  $("#recordDiv").empty().append(html);
							  element.init();
							  $(".retry").click(function(){
								  var recordId = $(this).attr("id");
								  msg.confirm("提示：【参数不正确】的参数会自动过滤 <br>是否继续执行？",function(){
									  $.ajax({
										  url: contextPath+"/api/params-check.htm",
										  data:{apiId:$("#apiId").val(),recordId:recordId},
										  type:"post",
										  dataType: "json",
										  success: function(res){
											  msg.result(res,function(){
												  layer.open({
													  type: 1,
													  skin: 'layui-layer-rim', //加上边框
													  area: ['420px', '220px'], //宽高
													  title:'预删除的语句',
													  content: $("#sentenceDiv").html(),
													  btn: ['确定', '跳过'],
													  yes: function(index, layero){
													    var sql = $(".layui-layer-content .layui-layer-textarea").val();
													    paramsRetry(recordId,sql,$,msg);
													  },
													  btn2: function(index, layero){
														  paramsRetry(recordId,'',$,msg);
													  }
												  });
											  });
										  }
									});
								  })
								  
							  })
								
						  });
					  }
				  }
			});
		 }
	});
	
	//参数选择
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
	//参数设置
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
		if(oneSearch !=-1){
			var ss =urlStr.substring(oneSearch,twoSearch);
			urlStr = urlStr.replace(ss, paramStr);
			$("#urlStr").val(urlStr);
		}
	});
	//参数显示隐藏
	$(".params-button").on('click',function(){
		$(".table-params").toggle();
	});
	//接口测试
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
						  if(data.length ===32){
							  $("#test-img").attr("src",contextPath+"/temp/"+data+".gif");
						  }else{
							  $(".runtext").text(data);
						  }
					  }else{
						  try {
							  var jsonObj = JSON.parse(data);
							  if(jsonObj.head.rtnCode === "900003" && $(".test-fail").length !=0){
								  $(".test-fail").show();
							  }
							  Process(data);
						  
						  }
						  catch(e) {
							  $(".runtext").text(data);
					      }
				  }
				  
				 
			  }
		});
	});
	//一键登陆
	$(".test-fail a").on('click',function(){
		  var projectId = $('#test-projectId').val();
		  var layIndex =msg.loading();
		  $.ajax({
			  url: contextPath+"/api/auto-login.htm",
			  data:{projectId:projectId},
			  type:"post",
			  dataType: "json",
			  success: function(data){
				  msg.close(layIndex);
				  if(data.success){
					  $(".test-fail").hide();
				  }
				  msg.alertData(data);
			  }
		});
	});
	
	
	form.on('submit(nowRun)', function(res) {
		var disable = $(res.elem).hasClass("layui-btn-disabled");
		if(!disable){
			$(res.elem).addClass("layui-btn-disabled");
		}else{
			msg.error("任务正在执行，请等待该任务完成...");
			return false;
		}
		$.ajax({
			  url: contextPath+"/api/now-run.htm",
			  data: JSON.stringify(res.field),
			  type:"post",
			  dataType: "json",
			  contentType:"application/json",
			  success: function(data){
				  msg.result(data);
			  }
		});
		return false;
	});
	
	form.on('submit(jobRun)', function(res) {
		layer.open({
			title: '配置cron表达式',
		    area: ['870px', '750px'],
		    type: 2, 
		    content: contextPath+"/api/cron-config.htm",
		    btn: ['保存', '取消'],
			  yes: function(index, layero){
				  var body = layer.getChildFrame('body', index);
				  res.field.CRON01EXPRESSION=body.find('#cron').val();
				  layer.close(index);
				  $.ajax({
					  url: contextPath+"/api/save-job.htm",
					  data: JSON.stringify(res.field),
					  type:"post",
					  dataType: "json",
					  contentType:"application/json",
					  success: function(data){
						  msg.result(data,function(){
							  $(".addJob").hide();
							  console.log(data);
							  var getTpl = jobTable.innerHTML;
							  laytpl(getTpl).render(data.obj, function(html){
								  $("#jobContent").empty().append(html);
							  });
							  
						  });
					  }
				});
				  
			  },
			  btn2: function(index, layero){
				  
			  }
		    
		}); 
		return false;
	});
	
	$("#jobContent").on('click','.cock a',function(){
		  var that = $(this);
		  var status = that.attr("key");
		  var jobId = $("#schedulerJobId").val();
		  $.ajax({
			  url: contextPath+"/api/change-job-status.htm",
			  data:{jobId:jobId,status:status},
			  type:"post",
			  dataType: "json",
			  success: function(data){
				  msg.result(data,function(){
					  if(status == 0){
						  that.attr("key",1);
						  that.text("停止");
					  }else{
						  that.attr("key",0);
						  that.text("启动");
					  }
				  });
			  }
		});
	});
	
	$("#jobContent").on('click',".removeJob",function(){
		  var jobId = $("#schedulerJobId").val();
		  $.ajax({
			  url: contextPath+"/api/remove-job.htm",
			  data:{jobId:jobId},
			  type:"post",
			  dataType: "json",
			  success: function(data){
				  msg.result(data,function(){
					  $("#jobContent").empty();
					  $(".addJob").show();
				  });
			  }
		});
	});

  //…
});
function paramsRetry(recordId,sql,$,msg){
	 $.ajax({
		  url: contextPath+"/api/params-retry.htm",
		  data:{recordId:recordId,deleteSql:sql},
		  type:"post",
		  dataType: "json",
		  success: function(res){
			  msg.result(res);
		  }
	 });
}