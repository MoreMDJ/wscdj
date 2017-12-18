<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<title>订单发货窗口</title>
<link href="/management/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" charset="utf-8" src="/management/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/common.js"></script>

<script type="text/javascript">
    //窗口API
    var api = top.dialog.get(window); //获取窗体对象
    var W = api.data; //获取父对象
  //页面加载完成执行
    $(function () {
        //设置按钮及事件
        api.button([{
            value: '确定',
            callback: function () {
                submitForm();
            },
            autofocus: true
        }, {
            value: '取消',
            callback: function () { }
        }]);
    });
    
    $(function () {
        $(".itemzengpin_select").click(function () {

          	//组合参数
            var postData = {
                "uid": $("#uidId").val(), 
                "pid": $(this).attr("pid"),
            };
          	//发送AJAX请求
            W.sendAjaxUrl(api, postData, "/management/user/edit/changeStore/save");
        });
    });
</script>
</head>

<body>
<form name="form1" method="post" action="" id="form1">
<div>
<input type="hidden" name="EVENTTARGET" id="__EVENTTARGET" value="${param.EVENTTARGET!""}">
<input type="hidden" name="EVENTARGUMENT" id="__EVENTARGUMENT" value="${param.EVENTARGUMENT!""}">
<input type="hidden" name="VIEWSTATE" id="__VIEWSTATE" value="${param.VIEWSTATE!""}">
<#if uid??><input type="hidden" id="uidId" name="uid" value="${uid?c}"></#if>
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
<!--工具栏-->
<div class="toolbar-wrap">
  <div id="floatHead" class="toolbar" style="position: static; top: 312px;">
    <div class="r-list">
      <input name="keywords" type="text" class="keyword" value="${param.keywords!""}">
      <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('btnSearch','')">查询</a>      
    </div>
  </div>
</div>
<!--/工具栏-->

<!--文字列表-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
<tbody>
        <tr class="odd_bg">
            <th align="left">账号</th>
            <th align="left" width="12%">手机号</th>
            <th align="left" width="12%">真实名字</th>
            <th align="left" width="12%">昵称</th> 
        </tr>
        
        <#if user_page??>
            <#list user_page.content as user>
                <tr>
                	<td><a class="itemzengpin_select" style="cursor:pointer;" pid="${user.id?c}">${user.username!""}</a></td>
                    <td>${user.mobile!"无"}</td>
                    <td>${user.realName!'无'}</td>
                    <td>${user.nickname!""}</td>
                </tr>
            </#list>
        </#if>
</tbody>
</table>

<!--/文字列表-->

<!--内容底部-->
<#assign PAGE_DATA=user_page />
<#include "/management/page_footer.ftl" />
<!--/内容底部-->
</form>

</body>
</html>