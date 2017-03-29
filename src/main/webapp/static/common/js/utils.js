layui.define(function(exports) {
	"use strict";

	var $ = layui.jquery;
		
	var utils={
			//url加时间戳
			date2Url : function(url) {
				if(url.indexOf('?') >= 0) {
					return url + "&time=" + new Date().getTime();
				}else{
					return url + "?time=" + new Date().getTime();
				}
			},
			/**
			 * 判断checkbox是否选中
	 		 * @param {Object} ck，可以是object或者jquery selector字符串
			 */
			isChecked: function(ck) {
				if(typeof ck == 'string') {
					ck = jQuery(ck);
				}
				return ck.prop('checked');
			},
			//设置checkbox选中
			setChecked:function(ck) {
				if(typeof ck == 'string') {
					ck = jQuery(ck);
				}
				
				ck.prop('checked', true);
			},
			//清除表单
			clearForm:function (objE){//objE为form表单 
				if(typeof objE == 'string') {
					objE = $(objE);
				}
				objE.find(':input').each(  
		            function(){  
		                switch(this.type){  
		                    case 'password':  
		                    case 'select-multiple':  
		                    case 'select-one':  
		                    case 'text':
		                    case 'hidden':	
		                    case 'textarea':  
		                        $(this).val('');  
		                        break;  
		                    case 'checkbox':  
		                    case 'radio':  
		                        this.checked = false;  
		                }  
		            }     
		        ); 
		        
		    },
		    //设置表单
		    setForm:function(objE,data){
		    	if(typeof objE == 'string') {
					objE = $(objE);
				}
		    	for(var key in data){
		    		objE.find(':input').each(  
		    	            function(){  
		    	                switch(this.type){  
		    	                    case 'password':  
		    	                    case 'select-multiple':  
		    	                    case 'select-one':  
		    	                    case 'text':
		    	                    case 'hidden':	
		    	                    case 'textarea':  
		    	                        if($(this).attr("name") == key){
		    	                        	$(this).val(data[key]);
		    	                        }  
		    	                        break;  
		    	                    case 'checkbox':  
		    	                    case 'radio':
		    	                    	if($(this).attr("name") == key && $(this).val()== data[key]){
		    	                    		this.checked = true;
		    	                        }
		    	                          
		    	                }  
		    	            }     
		    	        ); 
		    	}
		    }
	};
	
	exports('utils', utils);
});