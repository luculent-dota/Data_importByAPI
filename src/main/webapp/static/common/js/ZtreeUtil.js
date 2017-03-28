var ZtreeUtil = function(){
	var VIRTUAL_ROOT_NODE_ID = 'VirtualBaseTree';
	var setting = {
    		data:{
    			simpleData:{
    				enable:true,
    				idKey:"id",
    				pIdKey:"pId",
    				rootPId:null
    				}
    		}
	};
	var rMenu = null;
	function initRmenu(uiconfigs,treeId){
		if(uiconfigs.context.length !=0){
			var content = "<div id=\"rMenu\" class=\"tree-context tree-default-context tree-classic-context\">";
			$.each(uiconfigs.context,function(index,element){
				content +="<a id="+element.id+" href=\"javascript:;\"  style=\"display:none;\">"+element.label+"</a>";
			});
			content +="</div>"
			$("#rMenu").remove();
			$("#"+treeId).after(content);
			rMenu = $("#rMenu");
			var zTreeObj = $.fn.zTree.getZTreeObj(treeId);
			$.each(uiconfigs.context,function(index,element){
				if(typeof element.action == 'function'){
					document.getElementById(element.id).onclick = function(){
						 hideRMenu();
						 element.action(zTreeObj.getSelectedNodes()[0],zTreeObj);
					};
				}
			
			});
		}
	}
	//显示右击菜单
	function showRMenu(uiconfigs,treeId,treeNode, x, y) {
		rMenu.find("a").hide();
		if(treeNode == null){
			return false;
		}
		var showTemp = false;
		var zTreeObj = $.fn.zTree.getZTreeObj(treeId);
		
		$.each(uiconfigs.context,function(index,element){
			if(typeof element.visible == 'function'){
				if(element.visible(treeNode,zTreeObj)){
					zTreeObj.selectNode(treeNode);
					$("#"+element.id).show();
					showTemp = true;
				}
			}
		});
		
		if(showTemp){
			rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
			$("body").bind("mousedown", onBodyMouseDown);
		}
	
	}
	
	//隐藏右击菜单
	function hideRMenu() {
		if (rMenu) {
			rMenu.css({"visibility": "hidden"});
			$("body").unbind("mousedown", onBodyMouseDown);
		}
	}
	//鼠标点击别处
	function onBodyMouseDown(event){
		if(rMenu){
			if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
				rMenu.css({"visibility" : "hidden"});
			}
		}
		
	}
	return {
		initTree:function(treeId,data,callbackFunc,rulesconfigs,uiconfigs){
			if(!callbackFunc) callbackFunc = {};
			if(!rulesconfigs) rulesconfigs= {};
			setting=rulesconfigs;
			if(!uiconfigs ||typeof uiconfigs == 'undefined'  ) {
				uiconfigs={};
				uiconfigs.context = [];
			}
			setting.callback = callbackFunc;
			if(uiconfigs.context.length !=0 && typeof callbackFunc.onRightClick == 'undefined' ){
				setting.callback.onRightClick = function(event, treeId, treeNode){
					showRMenu(uiconfigs,treeId,treeNode, event.clientX,event.clientY);
				}
			}
//			if(typeof setting.callback.onRightClick != 'undefined'){
//				initRmenu(uiconfigs);
//			}
			$.fn.zTree.init($("#"+treeId), setting, data);
			
			var zTreeObj = $.fn.zTree.getZTreeObj(treeId);
			zTreeObj.expandNode(zTreeObj.getNodeByParam("id", VIRTUAL_ROOT_NODE_ID, null), true, false, true);
			
			initRmenu(uiconfigs,treeId);
			
		},
		getZtreeObj:function(treeId){
			return $.fn.zTree.getZTreeObj(treeId);
		},
		getBaseNode:function(treeId){
			return $.fn.zTree.getZTreeObj(treeId).getNodeByParam("id", VIRTUAL_ROOT_NODE_ID, null);
		},
		getNodeById:function(treeId,id){
			return $.fn.zTree.getZTreeObj(treeId).getNodeByParam("id", id, null);
		},
		removeNodeRefreshTree:function(treeId,node){
			if(node.isParent){
				$.fn.zTree.getZTreeObj(treeId).removeChildNodes(node);
			}
			$.fn.zTree.getZTreeObj(treeId).removeNode(node);
			
		}
	}
}();