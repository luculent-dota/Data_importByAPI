layui.define(['layer'], function(exports) {
	"use strict";

	var $ = layui.jquery,
		layer = layui.layer;
	var msg={
			//提示成功信息
			success:function(msg){
				layer.msg(msg, {icon: 1}); 
			},
			//提示错误信息
			error:function(msg){
				layer.msg(msg, {icon: 2}); 
			},
			//加载信息
			waiting:function(msg){
				msg = (typeof msg == 'undefined')? '加载中' : msg;
				layer.msg(msg, {icon: 16});
			},
			//提示加载信息
			load:function(){
				var index = layer.load();
				return index;
			},
			loading:function(){
				var index = layer.load(2, {
					  shade: 0.3 //0.1透明度的白色背景
				});
				return index;
			},
			//关闭窗口,提示信息用
			close:function(index){
				layer.close(index);
			},
			//确认窗口
			confirm:function(msg,options){
				if(typeof options == 'function'){
					layer.confirm(msg, {icon: 3}, options);
				}else{
					layer.confirm(msg, {icon: 3});
				}
				
			},
			//alert信息
			alert:function(msg){
				layer.alert(msg);
			},
			//msg信息
			msg:function(msg,timeout){
				timeout = (typeof timeout == 'undefined')? 3000 : timeout*1000;
				layer.msg(msg,{time: timeout}); 
			}
	};
	
	exports('msg', msg);
});