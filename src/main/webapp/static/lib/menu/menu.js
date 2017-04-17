var ztreeObj = null;
var form = null;
var param_tr_one = "<tr>"+
		"<td><input name='params_name' lay-verify='title' autocomplete='off' placeholder='参数名称' class='layui-input' type='text'></td>"+
		"<td><input name='params_detail' lay-verify='title' autocomplete='off' placeholder='参数说明' class='layui-input' type='text'></td>"+
		"<td><input type='checkbox' name='params_required' lay-skin='primary'  checked></td>"+
		"<td><input name='params_defaultValue' lay-verify='title' autocomplete='off' placeholder='默认值' class='layui-input' type='text'></td>"+
		"<td><select name='params_paramType'><option value=''>选择类型</option><option value='CONSTANT'>定值</option><option value='INTERVAL'>区间</option>"+
         "<option value='BASIS'>基础值</option><option value='PAGE'>页码</option></select></td>"+
		"<td><input name='params_dataSource' lay-verify='title' autocomplete='off' placeholder='数据来源' class='layui-input' type='text'></td>"+
		"<td><input name='params_remarks' lay-verify='title' autocomplete='off' placeholder='备注' class='layui-input' type='text'></td>"+
		"<td>"+
		    "<div class='layui-btn-group'>"+
			  "<a class='layui-btn layui-btn-primary layui-btn-small add_tr'>"+
			    "<i class='layui-icon'>&#xe654;</i>"+
			  "</a>"+
			"</div>"+
		"</td>"+
		"</tr>";
var param_tr = "<tr>"+
	      "<td><input name='params_name' lay-verify='title' autocomplete='off' placeholder='参数名称' class='layui-input' type='text'></td>"+
	      "<td><input name='params_detail' lay-verify='title' autocomplete='off' placeholder='参数说明' class='layui-input' type='text'></td>"+
	      "<td><input type='checkbox' name='params_required' lay-skin='primary'  checked></td>"+
	      "<td><input name='params_defaultValue' lay-verify='title' autocomplete='off' placeholder='默认值' class='layui-input' type='text'></td>"+
	      "<td><select name='params_paramType'><option value=''>选择类型</option><option value='CONSTANT'>定值</option><option value='INTERVAL'>区间</option>"+
	         "<option value='BASIS'>基础值</option><option value='PAGE'>页码</option></select></td>"+
	      "<td><input name='params_dataSource' lay-verify='title' autocomplete='off' placeholder='数据来源' class='layui-input' type='text'></td>"+
	      "<td><input name='params_remarks' lay-verify='title' autocomplete='off' placeholder='备注' class='layui-input' type='text'></td>"+
	      "<td>"+
		      "<div class='layui-btn-group'>"+
			  "<a class='layui-btn layui-btn-primary layui-btn-small del_tr'>"+
			    "<i class='layui-icon'>&#xe640;</i>"+
			  "</a>"+
			  "<a class='layui-btn layui-btn-primary layui-btn-small add_tr'>"+
			    "<i class='layui-icon'>&#xe654;</i>"+
			  "</a>"+
			"</div>"+
		 "</td>"+
	    "</tr>";
layui.config({
	base: contextPath+'/static/common/js/',
	version:new Date().getTime()
}).use(['laytpl','form','utils','msg'], function(){
		var $ = layui.jquery,
		 laytpl = layui.laytpl,
		form = layui.form(),
		msg = layui.msg,
		utils = layui.utils;
		initMenuTree(form,laytpl,utils);
		
		form.on('submit(go1)', function(res) {
			$.ajax({
				  url: contextPath+"/menu/project-save.htm",
				  data: res.field,
				  type:"post",
				  dataType: "json",
				  success: function(data){
					  msg.result(data,initMenuTree(form,laytpl,utils,msg));
				  }
			});
			return false;
	  });
		form.on('submit(go2)', function(res) {
		
			var paramList =new Array();
			if($(".table-params").is(':visible')){
				var paramObj = new Object();
				$('.table-params td').each(function(index,element){
					console.log($(element).children().attr("name"));
					switch($(element).children().attr("name")){
						case 'params_name':
							paramObj = new Object();
							paramObj.name = $(element).children().val();
						break;
						case 'params_detail':
							paramObj.detail = $(element).children().val();
							break;
						case 'params_required':
							if($(element).children().next().hasClass("layui-form-checked")){
								paramObj.required = 1;
							}else{
								paramObj.required = 0;
							}
							break;
						case 'params_defaultValue':
							paramObj.defaultValue = $(element).children().val();
							break;
						case 'params_paramType':
							paramObj.paramType = $(element).children().val();
							break;
						case 'params_dataSource':
							paramObj.dataSource = $(element).children().val();
							break;
						case 'params_remarks':
							paramObj.remarks = $(element).children().val();
							paramList.push(paramObj);
							break;
					};
				});
			}
			res.field.paramList = paramList;
			$.ajax({
				  url: contextPath+"/menu/api-save.htm",
				  data: JSON.stringify(res.field),
				  type:"post",
				  dataType: "json",
				  contentType:"application/json",
				  success: function(data){
					  msg.result(data,initMenuTree(form,laytpl,utils,msg));
				  }
			});
			return false;
	  });
	form.on('radio(apiType)', function(data){
		if($(data.elem).attr("title") == "验证码"){
			$(".table-params tbody").empty();
			$(".table-params").hide();
		}else{
			$(".table-params tbody").empty().append(param_tr_one);
			form.render();
			$(".table-params").show();
	}
			
	 }); 
	 $(".del_tr").live("click",function(){
		 $(this).parent().parent().parent().remove();
	 });
	 $(".add_tr").live("click",function(){
		 $(".table-params tbody").append(param_tr);
		 form.render();
	 });
});  

function initMenuTree(form,laytpl,utils,msg){
	console.log(msg);
	 $.get(contextPath+"/menu/tree.htm",  function(trees){
		 ZtreeUtil.initTree("ztree",trees,{
				onClick:function(event, treeId, treeNode){
					if(treeNode.type == "project"){
						$(".project-div fieldset legend").text("编辑项目");
						$(".project-div input[name='id']").val(treeNode.id);
						utils.clearForm($(".project-div .layui-form"));
						$.ajax({url: contextPath+"/menu/project-get.htm",data:{id:treeNode.id},type:"post",dataType: "json",async:false,
							  success: function(data){
								  utils.setForm($(".project-div .layui-form"),data);
								  form.render('radio');
							  }
						});
	                	$(".project-div").show();
	                	$(".api-div").hide();
	                	
	                	
					}else if(treeNode.type=="api"){
						$(".api-div fieldset legend").text("编辑接口");
						$(".api-div input[name='projectId']").val("");
						$(".api-div input[name='projectId']").val(treeNode.id);
						utils.clearForm($(".api-div .layui-form"));
						$.ajax({url: contextPath+"/menu/api-get.htm",data:{id:treeNode.id},type:"post",dataType: "json",async:false,
							  success: function(data){
								  utils.setForm($(".api-div .layui-form"),data);
								  if(data.apiType == 2){
									  $(".table-params tbody").empty();
									  $(".table-params").hide();
								  }else{
									  if(data.paramList.length!=0){
										  var getTpl = paramList.innerHTML;
										  laytpl(getTpl).render(data, function(html){
										      $(".table-params tbody").empty().append(html);
										      $(".table-params").show();
										      $.each(data.paramList,function(i,e){
										    	  $(".table-params tbody tr td select[name='params_paramType']").eq(i).val(e.paramType);
										      });
										      
										     form.render();
										  });
									  }
								  }
								  form.render();
							  }
						});
	                	$(".api-div").show();
	                	$(".project-div").hide();
					}
				},
				beforeClick:function(treeId, treeNode, clickFlag){
					if(treeNode.type == "project" || treeNode.type=="api"){
						return true;
					}else{
						return false;
					}
				}
				
			},{
				data:{
					key:{
						name: "text",
		    			url: null
					}
				}
			},{
				context:[{
	                id: "addProject",
	                label: "新增项目",
	                visible:function(NODE,TREE_OBJ){
	                	if(NODE.type=="base"){
	                		return true;
	                	}else{
	                		return false;
	                	}
	                },
	                action: function(NODE, TREE_OBJ){
	                	$(".project-div fieldset legend").text("新增项目");
	                	utils.clearForm($(".project-div .layui-form"));
	                	$(".project-div").show();
	                	$(".api-div").hide();
	                	form.render('radio');
	                }
	            }, {
	                id: "addApi",
	                label: "新增接口",
	                visible: function(NODE,TREE_OBJ){
	                	if(NODE.type=="project"){
	                		return true;
	                	}else{
	                		return false;
	                	}
	                },
	                action: function(NODE, TREE_OBJ){
	                	$(".project-div").hide();
	                	$(".api-div fieldset legend").text("新增接口");
	                	utils.clearForm($(".api-div .layui-form"));
	                	$("#askType_div input").eq(0).prop('checked', true);
	                	$("#apiType_div input").eq(2).prop('checked', true);
	                	form.render('radio');
	                	$(".table-params tbody").empty().append(param_tr_one);
	                	$(".table-params").show();
	        			form.render();
	                	$(".api-div input[name='projectId']").val(NODE.id);
	                	$(".api-div").show();
	                	
	                }
	            },{
	                id: "delProject",
	                label: "删除项目",
	                visible: function(NODE,TREE_OBJ){
	                	if(NODE.type=="project"){
	                		return true;
	                	}else{
	                		return false;
	                	}
	                },
	                action: function(NODE, TREE_OBJ){
	                	var message = "";
	                	if(NODE.isParent){
	                		message ="该项目存在接口<br>"
	                	}
	                	msg.confirm(message+'确认删除项目【'+NODE.text+'】吗？',function(index){
	                		$.get(contextPath+"/menu/project-del.htm", {id:NODE.id}, function(data){
	                			msg.result(data,initMenuTree(form,laytpl,utils,msg));
		                	},"json");
	                	});
	                }
	            },{
	                id: "delApi",
	                label: "删除接口",
	                visible: function(NODE,TREE_OBJ){
	                	if(NODE.type=="api"){
	                		return true;
	                	}else{
	                		return false;
	                	}
	                },
	                action: function(NODE, TREE_OBJ){
	                	msg.confirm('确认删除接口【'+NODE.text+'】吗？',function(index){
	                		$.get(contextPath+"/menu/api-del.htm", {id:NODE.id}, function(data){
	                			msg.result(data,initMenuTree(form,laytpl,utils,msg));
		                	},"json");
	                	});
	                }
	            }]
			});
			ztreeObj = ZtreeUtil.getZtreeObj('ztree');
	 },"json");
	
}