<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<title>订单管理</title>
<link href="/management/css/ui-dialog.css" rel="stylesheet" type="text/css">
<link href="/management/css/style.css" rel="stylesheet" type="text/css">
<link href="/management/css/pagination.css" rel="stylesheet" type="text/css">
<script type="text/javascript" charset="utf-8" src="/management/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/dialog-plus-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/laymain.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/common.js"></script>
</head>

<body class="mainbody">
<form method="post" action="/management/order/list?typeId=${param.typeId!''}" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="EVENTTARGET" id="EVENTTARGET" value="">
<input type="hidden" name="EVENTARGUMENT" id="EVENTARGUMENT" value="">
<input type="hidden" name="VIEWSTATE" id="VIEWSTATE" value="${param.VIEWSTATE!}">
</div>

<script type="text/javascript">
//<![CDATA[
var theForm = document.forms['form1'];
if (!theForm) {
    theForm = document.form1;
}
function __doPostBack(eventTarget, eventArgument) {
    if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
        theForm.EVENTTARGET.value = eventTarget;
        theForm.EVENTARGUMENT.value = eventArgument;
        theForm.submit();
    }
}
//]]>
</script>
<!--导航栏-->
<div class="location">
  <a href="javascript:history.back(-1);" class="back"><i></i><span>返回上一页</span></a>
  <a href="/management/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <span>订单列表</span>
</div>
<!--/导航栏-->

<!--工具栏-->
<div id="floatHead" class="toolbar-wrap">
  <div class="toolbar">
    <div class="box-wrap">
      <a class="menu-btn"></a>
      <div class="l-list">
        <ul class="icon-list">
          <li><a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a></li>
          <li><a onclick="return ExePostBack('btnDelete','只允许删除已取消订单，是否继续？');" id="btnDelete" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除</span></a></li>
        </ul>
        <div class="menu-list">
          <div class="rule-single-select single-select">
            <select name="statusId" onchange="javascript:setTimeout('__doPostBack(\'ddlStatus\',\'\')', 0)" style="display: none;">
            	<option value="">所有状态</option>
            	<#if param.typeId?? && param.typeId == '1'>
            	<option value="1" <#if param.statusId?? && param.statusId==1>selected="selected"</#if>>待确认</option>
            	</#if>
            	<option value="2" <#if param.statusId?? && param.statusId==2>selected="selected"</#if>>待付款</option>
            	<option value="3" <#if param.statusId?? && param.statusId==3>selected="selected"</#if>>待发货</option>
            	<option value="4" <#if param.statusId?? && param.statusId==4>selected="selected"</#if>>待收货</option>
            	<option value="5" <#if param.statusId?? && param.statusId==5>selected="selected"</#if>>待评价</option>
            	<option value="6" <#if param.statusId?? && param.statusId==6>selected="selected"</#if>>已完成</option>
            	<option value="7" <#if param.statusId?? && param.statusId==7>selected="selected"</#if>>已取消</option>
            </select>
          </div>
        </div>
      </div>
      <div class="r-list">
        <input name="keywords" onkeydown="if(event.keyCode==13){__doPostBack('btnSearch','')}" type="text" value="${param.keywords!''}" class="keyword">
        <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('btnSearch','')">查询</a>
      </div>
    </div>
  </div>
</div>
<!--/工具栏-->

<!--列表-->
<div class="table-container">

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
  <tbody><tr class="odd_bg">
    <th width="8%">选择</th>
    <th align="left">订单号</th>
    <th align="left" width="12%">会员账号</th>
    <th align="left" width="12%">配送方式</th>
    <th align="left" width="10%">支付方式</th>
    <th width="8%">订单状态</th>
    <th width="10%">总金额</th>
    <th align="left" width="16%">下单时间</th>
    <th width="8%">操作</th>
  </tr>

  <#if order_page??>
    <#list order_page.content as item>
    <tr>
      <td align="center">
        <span class="checkall" style="vertical-align:middle;">
          <input id="listChkId" type="checkbox" name="listChkIdx" value="${item_index}" >
        </span>
        <input type="hidden" name="listId" id="listId" value="${item.id?c}">
      </td>
      <td><a href="/management/order/edit?id=${item.id?c}">${item.orderNo!""}</a></td>
      <td>${item.username!""}</td>
      <td>${item.expressTitle!""}</td>
      <td>${item.paymentTitle!""}</td>
      <#--<td align="center"><#if item.statusId==1>待确认<#elseif item.statusId==2>待付款<#elseif item.statusId==3>待发货<#elseif item.statusId==4>待收货<#elseif item.statusId==5>待评价<#elseif item.statusId==6>已完成<#elseif item.statusId==7>已取消</#if></td>-->
      <td align="center">${item.statusTitle!''}</td>
      <td align="center"><#if item.orderAmount??>${item.orderAmount?string("0.00")}</#if></td>
      <td>${item.orderTime!""}</td>
      <td align="center"><a href="/management/order/edit?id=${item.id?c}">详细</a></td>
    </tr>
    </#list>
  </#if>
</tbody></table>

</div>
<!--/列表-->

<!--内容底部-->
<#assign PAGE_DATA=order_page />
<#include "/management/page_footer.ftl" />
<!--/内容底部-->

</form>


</body></html>