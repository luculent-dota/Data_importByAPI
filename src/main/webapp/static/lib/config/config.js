layui.config({
	base: contextPath+'/static/common/js/',
	version:new Date().getTime()
}).use(['layer', 'form','msg'], function(){
  var form = layui.form(),
  $ = layui.jquery,
  msg = layui.msg,
  layer = layui.layer;
  
  form.on('submit(go)', function(res) {
		$.ajax({
			  url: contextPath+"/config/save.htm",
			  data: res.field,
			  type:"post",
			  dataType: "json",
			  success: function(data){
				  msg.result(data);
			  }
		});
		return false;
  });
  //各种基于事件的操作，下面会有进一步介绍
});