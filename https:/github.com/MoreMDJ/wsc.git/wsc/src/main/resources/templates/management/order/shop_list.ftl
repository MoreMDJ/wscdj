<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<title>内容管理</title>
<link href="/management/css/ui-dialog.css" rel="stylesheet" type="text/css">
<link href="/management/css/style.css" rel="stylesheet" type="text/css">
<link href="/management/css/pagination.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/management/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="/management/js/jquery.lazyload.min.js"></script>
<script type="text/javascript" src="/management/js/dialog-plus-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/laymain.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/common.js"></script>
<script type="text/javascript">
$(function () {
    //图片延迟加载
    $(".pic img").lazyload({ effect: "fadeIn" });
    //点击图片链接
    $(".pic img").click(function () {
        var linkUrl = $(this).parent().parent().find(".foot a").attr("href");
        if (linkUrl != "") {
            location.href = linkUrl; //跳转到修改页面
        }
    });
});
</script>
</head>

<body class="mainbody">
<form method="post" action="/management/pay/shop/list" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="EVENTTARGET" id="EVENTTARGET" value="">
<input type="hidden" name="EVENTARGUMENT" id="EVENTARGUMENT" value="">
<input type="hidden" name="VIEWSTATE" id="VIEWSTATE" value="">
</div>

<script type="text/javascript">
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
</script>
<#--
<script src="/management/js/WebResource.axd" type="text/javascript"></script>
-->
<!--导航栏-->
<div class="location">
  <a href="javascript:history.back(-1);" class="back"><i></i><span>返回上一页</span></a>
  <a href="/management/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <span>内容列表</span>
</div>
<!--/导航栏-->

<!--工具栏-->
<div id="floatHead" class="toolbar-wrap" style="height: 52px;">
  <div class="toolbar">
    <div class="box-wrap">
      <a class="menu-btn"></a>
      <div class="l-list">
        <#if !sessionShopId?? && !sessionSupplierId??>
          <ul class="icon-list">
              <li><a class="add" href="/management/pay/shop/edit"><i></i><span>新增</span></a></li>
              <li><a id="btnSave" class="save" href="javascript:__doPostBack('btnSave','')"><i></i><span>保存</span></a></li>
              <li><a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a></li>
              <li><a onclick="return ExePostBack('btnDelete');" id="btnDelete" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除</span></a></li>
          </ul>
          <div class="menu-list">
            <div style="position: relative;
    display: inline-block;
    padding: 5px 5px 5px 5px;
    vertical-align: middle;
    min-width: 40px;
    line-height: 20px;
    height: 20px;
    border: solid 1px #eee;
    text-decoration: none;
    background: #fff;
    white-space: nowrap;
    word-break: break-all;">
              <select id="s_province" name="province" onchange="javascript:changeArea();"></select>
              <select id="s_city" name="city" onchange="javascript:changeArea();"></select>
              <select id="s_county" name="district" onchange="javascript:changeArea();"></select>
              <script class="resources library" src="/management/js/area.js" type="text/javascript"></script>
              <script type="text/javascript">_init_area('${province!}', '${city!}', '${district!}');</script>
            </div> 
            <div class="rule-single-select single-select">
              <select name="categoryId" onchange="javascript:setTimeout('__doPostBack(\'ddlCategoryId\',\'\')', 0)" id="ddlCategoryId" style="display: none;">
              	<option value="">所有类别</option>
            	  <option value="0" <#if categoryId?? && categoryId==0>selected="selected"</#if>>超市</option>
                <option value="1" <#if categoryId?? && categoryId==1>selected="selected"</#if>>服务商</option>
              </select>
            </div>
          </div>
        </#if>
      </div>
      <#if shop_page?? && shop_page.totalElements?? && shop_page.totalElements gt 10>
      <div class="r-list">
        <input name="keywords" onkeydown="if(event.keyCode==13){__doPostBack('btnSearch','')}" type="text" value="${keywords!''}" class="keyword">
        <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('btnSearch','')">查询</a>
      </div>
      </#if>
    </div>
  </div>
</div>
<!--/工具栏-->

<!--列表-->
<div class="table-container">
  <!--文字列表-->
  
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
    <tbody><tr class="odd_bg">
      <th width="6%">选择</th>
      <th width="6%" align="center">编号</th>
      <th align="left">名称</th>
      <th width="8%" align="center">省</th>
      <th width="8%" align="center">市</th>
      <th width="8%" align="center">区</th>
      <th width="6%" align="center">类型</th>
      <th width="6%" align="center">状态</th>
      <th align="left" width="65">排序</th>
      <th width="10%">操作</th>
    </tr>
    
    <#if shop_page??>
      <#list shop_page.content as item>
      <tr>
        <td align="center">
          <span class="checkall" style="vertical-align:middle;">
            <input id="listChkId" type="checkbox" name="listChkIdx" value="${item_index}" >
          </span>
          <input type="hidden" name="listId" id="listId" value="${item.id?c}">
        </td>
        <td align="center">${item.id?c}</td>
        <td><a href="/management/pay/shop/edit?id=${item.id?c}">${item.title!''}</a></td>
        <td align="center"><#if item.province?? && item.province!="省份">${item.province!}</#if></td>
        <td align="center"><#if item.city?? && item.city!="地级市">${item.city!}</#if></td>
        <td align="center"><#if item.district?? && item.district!="市、县级市">${item.district!}</#if></td>
        <td align="center"><#if item.categoryId??><#if item.categoryId==0>超市<#elseif item.categoryId==1>服务商</#if></#if></td>
        <td align="center"><#if item.statusId??><#if item.statusId==0>开启<#elseif item.statusId==1>关闭</#if></#if></td>
        <td><input name="sortId" type="text" value="${item.sortId!''}" class="sort" onkeydown="return checkNumber(event);"></td>
        <td align="center">
          <#--
          <a href="/management/pay/shop/edit?action=Copy&id=${item.id?c}">复制</a>
          -->
          <a href="/management/pay/shop/edit?id=${item.id?c}">修改</a>
        </td>
      </tr>
      </#list>
    </#if>
  
  </tbody></table>
  
  <!--/文字列表-->

  <!--图片列表-->
  
  <!--/图片列表-->
</div>
<!--/列表-->

<!--内容底部-->
<#assign PAGE_DATA=shop_page />
<#include "/management/page_footer.ftl" />
<!--/内容底部-->

</form>


</body></html>