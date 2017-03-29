/** index.js By Beginner Emain:zheng_jinfan@126.com HomePage:http://www.zhengjinfan.cn */

var tab;
layui.config({
	base: contextPath+'/static/common/js/',
	version:new Date().getTime()
}).use(['element','form','layer', 'navbar', 'tab'], function() {
	var element = layui.element(),
		$ = layui.jquery,
		form = layui.form(),
		layer = layui.layer,
		navbar = layui.navbar();
		tab = layui.tab({
			elem: '.admin-nav-card' //设置选项卡容器
				,
			//maxSetting: {
			//	max: 5,
			//	tipMsg: '只能开5个哇，不能再开了。真的。'
			//},
			contextMenu:true
		});
	//iframe自适应
	$(window).on('resize', function() {
		var $content = $('.admin-nav-card .layui-tab-content');
		$content.height($(this).height() - 147);
		$content.find('iframe').each(function() {
			$(this).height($content.height());
		});
	}).resize();

	
	$('.admin-side-toggle').on('click', function() {
		var sideWidth = $('#admin-side').width();
		if(sideWidth === 200) {
			$('#admin-body').animate({
				left: '0'
			}); //admin-footer
			$('#admin-footer').animate({
				left: '0'
			});
			$('#admin-side').animate({
				width: '0'
			});
		} else {
			$('#admin-body').animate({
				left: '200px'
			});
			$('#admin-footer').animate({
				left: '200px'
			});
			$('#admin-side').animate({
				width: '200px'
			});
		}
	});
	$('.admin-side-full').on('click', function() {
		var docElm = document.documentElement;
		//W3C  
		if(docElm.requestFullscreen) {
			docElm.requestFullscreen();
		}
		//FireFox  
		else if(docElm.mozRequestFullScreen) {
			docElm.mozRequestFullScreen();
		}
		//Chrome等  
		else if(docElm.webkitRequestFullScreen) {
			docElm.webkitRequestFullScreen();
		}
		//IE11
		else if(elem.msRequestFullscreen) {
			elem.msRequestFullscreen();
		}
		layer.msg('按Esc即可退出全屏');
	});
	
	$('#project_list dd').on('click',function(){
		tab.removeAll();
		var projectId = $(this).find("a").attr("key");
		$.get(contextPath+"/api/menu-json.htm", {"projectId":projectId},function(config){
			//设置navbar
			navbar.set({
				spreadOne: true,
				elem: '#admin-navbar-side',
				cached: true,
				data: config
				/*cached:true,
				url: 'datas/nav.json'*/
			});
			//渲染navbar
			navbar.render();
			//监听点击事件
			navbar.on('click(side)', function(data) {
				tab.tabAdd(data.field);
			});
			$("#admin-navbar-side dl dd").eq(0).click();
		},"json");
		
	});
	$('#project_list dd:first').click();
	
	$('#setupPage').on('click', function() {
		tab.removeAll();
		//设置navbar
		navbar.set({
			spreadOne: true,
			elem: '#admin-navbar-side',
			cached: true,
			data: configs
			/*cached:true,
			url: 'datas/nav.json'*/
		});
		//渲染navbar
		navbar.render();
		//监听点击事件
		navbar.on('click(side)', function(data) {
			
			tab.tabAdd(data.field);
		});
		$("#admin-navbar-side dl dd").eq(0).click();
	});
		
});

