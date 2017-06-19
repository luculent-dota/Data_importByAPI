layui.config({
	base: contextPath+'/static/common/js/',
	version:new Date().getTime()
}).use(['layer', 'form'], function() {
	var layer = layui.layer,
		$ = layui.jquery,
		form = layui.form();
	
	form.on('submit(login)',function(res){
		$.ajax({
			  url: contextPath+"/login",
			  data: res.field,
			  type: 'POST',
			  dataType: "json",
			  success: function(data){
				  if(data.success && data.status === "200"){
					  window.location.href = contextPath + '/main/index';
				  }else{
					  layer.msg(data.msg);
				  }
			  }
		});
		return false;
	});
	$("#captcha").on("click",function(){
		var $this = $(this);
        var url = $this.data("src") + new Date().getTime();
        $this.attr("src", url);
	});
});