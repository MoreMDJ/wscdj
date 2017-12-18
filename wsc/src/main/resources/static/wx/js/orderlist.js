function searchOrders(f){
	var url = basePath+"/mobile/order/search";
	var loadData = "";
	$(window).off("scroll", scrollHandler);
	var fliter = $("#sc_fliterType").val();
	if(fliter==5){
		url = basePath+"/mobile/order/searchreturn";
	}
	if(f){
		loadData = $("#searchform").serializeArray();
		$("#results").loading().load(url,loadData);
	}else{
		loadData = $("#listform").serializeArray();
		$.get(url, loadData, function(html){
	  		  $(html).appendTo("#results");
	  	});
	}
}

var scrollHandler = function(){
    if ($(document).height() - $(this).scrollTop() - $(this).height()<100){
    	searchOrders(false);
    } 
};

/**
 * 申请退款
 */
function applyRefund(){
	var amount = $("#returnAmount").val();
	if(amount==""||amount==0){
		alert("请填写退款金额！");
		return ;
	}
	$("#refundForm").submit();
}
/**
 * 建议投诉
 */
function applyComplaint(){
	var complaint = $("#complaint").val();
	if(complaint==""||complaint.length<10){
		alert("请正确填写投诉内容：10至100个文字！");
		return ;
	}
	$("#complaintForm").submit();
}
/**
 * 录入物流单号
 */
function refundtract(){
	var trackingNo = $("#trackingNo").val();
	if(trackingNo==""||trackingNo.length<3){
		alert("请正确填写物流单号！");
		return ;
	}
	var url = basePath+"/mobile/order/savetract";
	var postData = $("#tractForm").serializeArray();
	$.post(url,postData,refundtractCallback,'text');
	
}
/**
 * 录入物流单号返回处理函数
 */
function refundtractCallback(data){
	var result = eval("("+data+")");
	if(result.code==1){
		alert("物流单号录入成功。");
		window.history.go(-1);
	}else{
		alert(result.msg);
	}
}
/**
 * 确认收货
 */
function receiptOrder(orderid){
	var url = basePath+"/mobile/order/receiptorder";
	var postData = {"orderId":orderid};
	$.post(url,postData,receiptOrderCallback,'text');
}
/**
 * 确认收货返回函数
 */
function receiptOrderCallback(data){
	var result = eval("("+data+")");
	if(result.code==1){
		alert("操作成功。");
		searchOrders(true);
	}else{
		alert(result.msg);
	}
}

/**
 * 取消订单
 */
function cancelOrder(){
	if(confirm("确定要取消订单？")){
		wait();
		var url = basePath+"/mobile/order/ordercancel";
		var postData = $("#cancelForm").serializeArray();
		$.post(url,postData,cancelOrderCallback,'text');
	}	
}
/**
 * 录入物流单号返回处理函数
 */
function cancelOrderCallback(data){
	close(1);
	var result = eval("("+data+")");
	alert(result.msg);
	if(result.code==1){
		window.location.href=basePath+"/mobile/order/list";
	}
}
/**
 * 查询物流信息
 * @param f
 */
function searchpostinfo(com,no){
	var url = basePath+"/mobile/order/searchpostinfo";
	var loadData = {"trackingNo":no,"trackingCom":com};
	$("#postinfo").loading().load(url,loadData);
}