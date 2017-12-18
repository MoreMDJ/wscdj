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
<form method="post" action="/management/ad/list" id="form1">
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
        <ul class="icon-list">
          <li><a class="add" href="/management/ad/edit"><i></i><span>新增</span></a></li>
          <li><a id="btnSave" class="save" href="javascript:__doPostBack('btnSave','')"><i></i><span>保存</span></a></li>
          <li><a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a></li>
          <li><a onclick="return ExePostBack('btnDelete');" id="btnDelete" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除</span></a></li>
        </ul>
        <div class="menu-list">
          <div class="rule-single-select single-select">
            <select name="categoryId" onchange="javascript:setTimeout('__doPostBack(\'ddlCategoryId\',\'\')', 0)" id="ddlCategoryId" style="display: none;">
            	<option value="">所有类别</option>
            	<#if category_list??>
                <#list category_list as item>
                  <option value="${item.id?c}" <#if param.categoryId?? && param.categoryId==item.id>selected="selected"</#if>>${item.title!''}</option>
                </#list>
              </#if>
            </select>
          </div>
        </div>
      </div>
      <div class="r-list">
        <input name="keywords" type="text" value="${param.keywords!''}" onkeydown="if(event.keyCode==13){__doPostBack('btnSearch','')}" class="keyword">
        <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('btnSearch','')">查询</a>
      </div>
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
      <th align="left">标题</th>
      <th align="left" width="20%">所属类别</th>
      <th align="left" width="10%">状态</th>
      <th align="left" width="65">排序</th>
      <#--
      <th align="left" width="120">属性</th>
      -->
      <th width="10%">操作</th>
    </tr>
    
    <#if ad_page??>
      <#list ad_page.content as item>
      <tr>
        <td align="center">
          <span class="checkall" style="vertical-align:middle;">
            <input id="listChkId" type="checkbox" name="listChkIdx" value="${item_index}" >
          </span>
          <input type="hidden" name="listId" id="listId" value="${item.id?c}">
        </td>
        <td align="center">${item.id?c}</td>
        <td><a href="/management/ad/edit?id=${item.id?c}">${item.title!''}</a></td>
        <td>${item.categoryTitle!''}</td>
        <td><#if item.statusId??><#if item.statusId==1>不显示<#else>显示</#if></#if></td>
        <td><input name="sortId" type="text" value="${item.sortId!''}" class="sort" onkeydown="return checkNumber(event);"></td>
        <#--
        <td>
          <div class="btn-tools">
            <a id="rptList1_lbtnIsMsg_0" title="取消评论" class="msg selected" href="javascript:__doPostBack('rptList1$ctl01$lbtnIsMsg','')"></a>
            <a id="rptList1_lbtnIsTop_0" title="设置置顶" class="top" href="javascript:__doPostBack('rptList1$ctl01$lbtnIsTop','')"></a>
            <a id="rptList1_lbtnIsRed_0" title="设置推荐" class="red" href="javascript:__doPostBack('rptList1$ctl01$lbtnIsRed','')"></a>
            <a id="rptList1_lbtnIsHot_0" title="设置热门" class="hot" href="javascript:__doPostBack('rptList1$ctl01$lbtnIsHot','')"></a>
            <a id="rptList1_lbtnIsSlide_0" title="取消幻灯片" class="pic selected" href="javascript:__doPostBack('rptList1$ctl01$lbtnIsSlide','')"></a>
          </div>
        </td>
        -->
        <td align="center">
          <a href="/management/ad/edit?action=Copy&id=${item.id?c}">复制</a>
          <a href="/management/ad/edit?id=${item.id?c}">修改</a>
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
<#assign PAGE_DATA=ad_page />
<#include "/management/page_footer.ftl" />
<!--/内容底部-->

</form>


</body></html>