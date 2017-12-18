<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<title>内容管理</title>
<link href="/management/css/ui-dialog.css" rel="stylesheet" type="text/css">
<link href="/management/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/management/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="/management/js/dialog-plus-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/laymain.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/common.js"></script>
</head>

<body class="mainbody">
<form method="post" action="/management/navigation/list?action=View" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="">
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="">
</div>

<script type="text/javascript">
var theForm = document.forms['form1'];
if (!theForm) {
    theForm = document.form1;
}
function __doPostBack(eventTarget, eventArgument) {
    if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
        theForm.__EVENTTARGET.value = eventTarget;
        theForm.__EVENTARGUMENT.value = eventArgument;
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
          <li><a class="add" href="/management/navigation/edit"><i></i><span>新增</span></a></li>
          <li><a id="btnSave" class="save" href="javascript:__doPostBack('btnSave','')"><i></i><span>保存</span></a></li>
          <li><a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a></li>
          <li><a onclick="return ExePostBack('btnDelete');" id="btnDelete" class="del" href="javascript:return ExePostBack('btnDelete','本操作会删除本导航及下属子导航，是否继续？');"><i></i><span>删除</span></a></li>
        </ul>
      </div>
    </div>
  </div>
</div>
<!--/工具栏-->

<!--列表-->
<div class="table-container">
  <!--文字列表-->
  
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
    <tbody>
    <tr class="odd_bg">
      <th width="6%">选择</th>
      <th align="left" width="12%">调用名称</th>
      <th align="left">标题</th>
      <th width="8%">显示</th>
      <th align="left" width="65">排序</th>
      <th width="12%">操作</th>
    </tr>
    <#if menu_list??>
      <#list menu_list as item>
        <tr>
          <td align="center">
            <span class="checkall" style="vertical-align:middle;">
              <input id="listChkId" type="checkbox" name="listChkIdx" value="${item_index}" >
            </span>
            <input type="hidden" name="listId" id="listId" value="${item.id?c}">
          </td>
          <td>${item.name!''}</td>
          <td style="white-space:nowrap;word-break:break-all;overflow:hidden;">
            <#if item.layerId??>
              <#if item.layerId gt 1>
                <span style="display:inline-block;width:${(item.layerId-2)*24}px;"></span>
                <span class="folder-line"></span>
              </#if>
            </#if>
            </span><span class="folder-open"></span>
            <a href="/management/navigation/edit?id=${item.id?c}">${item.title!''}</a>
          </td>
          <td align="center"><#if item.isEnable??><#if item.isEnable==true>√<#else>x</#if><#else>×</#if></td>
          <td><input name="sortId" type="text" value="${item.sortId!''}" class="sort" onkeydown="return checkNumber(event);"></td>
          <td align="center" style="white-space:nowrap;word-break:break-all;overflow:hidden;">
            <a href="/management/navigation/edit?parentId=${item.id?c}">添加子类</a>
            <a href="/management/navigation/edit?id=${item.id?c}">修改</a>
          </td>
        </tr>
      </#list>
    </#if>
    <#if navigation_page??>
      <#list navigation_page.content as item>
      <tr>
        <td align="center">
          <span class="checkall" style="vertical-align:middle;">
            <input id="listChkId" type="checkbox" name="listChkIdx" value="${item_index}" >
          </span>
          <input type="hidden" name="listId" id="listId" value="${item.id?c}">
        </td>
        <td align="center">${item.id?c}</td>
        <td><a href="/management/navigation/edit?id=${item.id?c}">${item.title!''}</a></td>
        <td align="center"><#if item.imgUri??><img src="${item.imgUri}" width="70" height="30" /></#if></td>
        <td align="center"><#if item.statusId??><#if item.statusId==0>显示<#elseif item.statusId==1>不显示</#if></#if></td>
        <td align="center">${item.goodsCategoryTitle!''}</td>
        <td><input name="sortId" type="text" value="${item.sortId!''}" class="sort" onkeydown="return checkNumber(event);"></td>
        <td align="center">
          <a href="/management/navigation/edit?action=Copy&id=${item.id?c}">复制</a>
          <a href="/management/navigation/edit?id=${item.id?c}">修改</a>
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
<!--/内容底部-->

</form>


</body></html>