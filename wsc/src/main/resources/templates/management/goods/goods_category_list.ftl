<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<title>后台导航管理</title>
<link href="/management/css/ui-dialog.css" rel="stylesheet" type="text/css">
<link href="/management/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/management/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="/management/js/dialog-plus-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/laymain.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/common.js"></script>
</head>
<body class="mainbody">
<form method="post" action="/management/goods/category/list" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="EVENTTARGET" id="__EVENTTARGET" value="">
<input type="hidden" name="EVENTARGUMENT" id="__EVENTARGUMENT" value="">
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
  <a href="/management/center.aspx" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <span>内容类别</span>
</div>
<!--/导航栏-->

<!--工具栏-->
<div id="floatHead" class="toolbar-wrap">
  <div class="toolbar">
    <div class="box-wrap">
      <a class="menu-btn"></a>
      <div class="l-list">
        <ul class="icon-list">
          <li><a class="add" href="/management/goods/category/edit"><i></i><span>新增</span></a></li>
          <li><a id="btnSave" class="save" href="javascript:__doPostBack('btnSave','')"><i></i><span>保存</span></a></li>
          <li><a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a></li>
          <li><a onclick="return ExePostBack('btnDelete','本操作会删除本类别及下属子类别，是否继续？');" id="btnDelete" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除</span></a></li>
        </ul>
      </div>
    </div>
  </div>
</div>
<!--/工具栏-->

<!--列表-->
<div class="table-container">
  
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
    <tbody>
    <tr class="odd_bg">
      <th width="6%">选择</th>
      <th align="left" width="6%">编号</th>
      <th align="left">类别名称</th>
      <th align="left" width="12%">排序</th>
      <th width="12%">操作</th>
    </tr>
    <#if category_list??>
      <#list category_list as item>
        <tr>
          <td align="center">
            <span class="checkall" style="vertical-align:middle;">
              <input id="listChkId" type="checkbox" name="listChkIdx" value="${item_index}" >
            </span>
            <input type="hidden" name="listId" id="listId" value="${item.id?c}">
          </td>
          <td>${item.id?c}</td>
          <td>
            <#if item.layerId?? && item.layerId gt 1><span style="display:inline-block;width:${(item.layerId-2)*20}px;"></span><span class="folder-line"></span></#if>
            <span class="folder-open"></span>
            <a href="/management/goods/category/edit?id=${item.id?c}">${item.title!''}</a>
          </td>
          <td><input name="sortId" type="text" value="${item.sortId!''}" class="sort" onkeydown="return checkNumber(event);"></td>
          <td align="center">
            <a href="/management/goods/category/edit?parentId=${item.id?c}">添加子类</a>
            <a href="/management/goods/category/edit?id=${item.id?c}">修改</a>
          </td>
        </tr>
      </#list>
    </#if>
  </tbody>
  </table>
  
</div>
<!--/列表-->

</form>
</body>
</html>