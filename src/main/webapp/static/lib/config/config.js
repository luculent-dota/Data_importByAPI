layui.use(['layer', 'form'], function(){
  var form = layui.form(),
  $ = layui.jquery,
  layer = layui.layer;
  
  form.on('submit(go)', function(res) {
		$.ajax({
			  url: contextPath+"/config/save.htm",
			  data: res.field,
			  type:"post",
			  dataType: "json",
			  success: function(data){
				  if(data.status =='500'){
					  layer.msg(data.msg, {icon: 5});
				  }else{
					  layer.msg(data.msg, {icon: 1});
				  }
				 
			  }
		});
		return false;
  });
  //各种基于事件的操作，下面会有进一步介绍
});