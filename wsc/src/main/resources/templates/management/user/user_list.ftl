<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<title>会员管理</title>
<link href="/management/css/ui-dialog.css" rel="stylesheet" type="text/css">
<link href="/management/css/style.css" rel="stylesheet" type="text/css">
<link href="/management/css/pagination.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/management/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="/management/js/dialog-plus-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/laymain.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/common.js"></script>
<script type="text/javascript" src="/management/js/WdatePicker.js"></script>
<script type="text/javascript">
//发送短信
function PostSMS(mobile) {
    var mobiles = "";
    if (arguments.length == 1) { //如果有传入值
        mobiles = mobile;
    } else {
        lenNum = $(".checkall input:checked").length;
        $(".checkall input:checked").each(function (i) {
            if ($(this).parent().siblings('input[name="hidMobile"]').val() != "") {
                mobiles += $(this).parent().siblings('input[name="hidMobile"]').val();
                if (i < lenNum - 1) {
                    mobiles += ',';
                }
            }
        });
    }
    if (mobiles == "") {
        top.dialog({
            title: '提示',
            content: '对不起，手机号码不能为空！',
            okValue: '确定',
            ok: function () { }
        }).showModal();
        return false;
    }
    var smsdialog = parent.dialog({
        title: '发送手机短信',
        content: '<textarea id="txtSmsContent" name="txtSmsContent" rows="2" cols="20" class="input"></textarea>',
        okValue: '确定',
        ok: function () {
            var remark = $("#txtSmsContent", parent.document).val();
            if (remark == "") {
                top.dialog({
                    title: '提示',
                    content: '对不起，请输入手机短信内容！',
                    okValue: '确定',
                    ok: function () { }
                }).showModal(smsdialog);
                return false;
            }
            var postData = { "mobiles": mobiles, "content": remark };
            //发送AJAX请求
            $.ajax({
                type: "post",
                url: "../../tools/admin_ajax.ashx?action=sms_message_post",
                data: postData,
                dataType: "json",
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    top.dialog({
                        title: '提示',
                        content: '尝试发送失败，错误信息：' + errorThrown,
                        okValue: '确定',
                        ok: function () { }
                    }).showModal(smsdialog);
                },
                success: function (data, textStatus) {
                    if (data.status == 1) {
                        smsdialog.close().remove();
                        var d = top.dialog({ content: data.msg }).show();
                        setTimeout(function () {
                            d.close().remove();
                            location.reload();
                        }, 2000);
                    } else {
                        top.dialog({
                            title: '提示',
                            content: '错误提示：' + data.msg,
                            okValue: '确定',
                            ok: function () { }
                        }).showModal(smsdialog);
                    }
                }
            });
            return false;
        },
        cancelValue: '取消',
        cancel: function () { }
    }).showModal();
}
</script>
</head>

<body class="mainbody">
<form method="post" action="/management/user/list" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="EVENTTARGET" id="__EVENTTARGET" value="">
<input type="hidden" name="EVENTARGUMENT" id="__EVENTARGUMENT" value="">
<input type="hidden" name="LASTFOCUS" id="__LASTFOCUS" value="">
<input type="hidden" name="VIEWSTATE" id="__VIEWSTATE" value="" >
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
function DownLoadUserSpread()
{
	var begain = $("#begain").val();
    var end = $("#end").val();
    var diyCode = $("#diyCode").val();
    var city = $("#cityName").val();
    window.open("/management/download?startDate="+ begain + "&endDate=" + end+"&type=1");
}
//]]>
</script>
<!--导航栏-->
<div class="location">
  <a href="javascript:history.back(-1);" class="back"><i></i><span>返回上一页</span></a>
  <a href="/management/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <span>会员管理</span>
  <i class="arrow"></i>
  <span>会员列表</span>
</div>
<!--/导航栏-->

<!--工具栏-->
<div id="floatHead" class="toolbar-wrap" style="height: 52px;">
  <div class="toolbar">
    <div class="box-wrap">
      <a class="menu-btn"></a>
      <div class="l-list">
        <ul class="icon-list">
          <li><a class="add" href="/management/user/edit"><i></i><span>新增</span></a></li>
          <#--
          <li><a class="msg" href="javascript:;" onclick="PostSMS();"><i></i><span>短信</span></a></li>
          -->
          <li><a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a></li>
          <li><a onclick="return ExePostBack('btnDelete');" id="btnDelete" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除</span></a></li>
          <li>
          	<div class="odiv">
				<span class="span1">开始时间：</span> <input name="startDate"
					id="begain" type="text" value="${orderStartTime!"" }" class="input date"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})"
					datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/"
					errormsg="请选择正确的日期" sucmsg=" " />
					<input type="hidden" name="oldOrderStartTime" id="oldOrderStartTime" value="${oldOrderStartTime!"" }" /> 
			</div>
			<div class="odiv">
				<span class="span1">结束时间：</span> <input name="endDate"
					id="end" type="text" value="${orderEndTime!"" }" class="input date"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})"
					datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/"
					errormsg="请选择正确的日期" sucmsg=" " />
					<input type="hidden" name="oldOrderEndTime" id="oldOrderEndTime" value="${oldOrderEndTime!"" }" /> 
			</div>
          	<a class="all" href="javascript:DownLoadUserSpread();"><i></i><span>下载</span></a>
          </li>
        </ul>
        <div class="menu-list">
          <div class="rule-single-select single-select">
            <select name="statusId" onchange="javascript:setTimeout('__doPostBack(\'btnGroupId\',\'\')', 0)" id="ddlGroupId" style="display: none;">
	             <option value="">所有会员组</option>
	             <#if user_level_list??>
  	             <#list user_level_list as item>
  	               <option <#if levelId?? && item.id==levelId>selected="selected"</#if> value="${item.id?c}">${item.title!}</option>
  	             </#list>
	             </#if>
	             <option <#if param?? && param.statusId?? && param.statusId==1>selected="selected"</#if> value="1">门店</option>
	             <option <#if param?? && param.statusId?? && param.statusId==2>selected="selected"</#if> value="2">店员</option>
	             <option <#if param?? && param.statusId?? && param.statusId==3>selected="selected"</#if> value="3">会员</option>
            </select>
          </div>
          <div id="allshop" class="single-select">
            <div class="rule-single-select">
              <select name="shopId" onchange="javascript:setTimeout('__doPostBack(\'shopId\',\'\')', 0)" id="ddlGroupId" style="display: none;">
                 <option value="">所有超市</option>
              </select>
            </div>
          </div>
        </div>
        
      </div>
      <div class="r-list">
        <input name="keywords" onkeydown="if(event.keyCode==13){__doPostBack('btnSearch','')}" type="text" class="keyword" value="${param.keywords!}">
        <a class="btn-search" href="javascript:__doPostBack('btnSearch','')">查询</a>
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
    <th align="left" colspan="2">用户名</th>
    <th align="left" width="12%">会员组</th>
    <th align="left" width="12%">归属超市</th>
    <th align="left" width="12%">上次登录时间</th>
    <th width="8%">状态</th>
    <th width="8%">操作</th>
  </tr>

  <#if user_page??>
    <#list user_page.content as item>
      <tr>
        <td align="center">
          <span class="checkall" style="vertical-align:middle;">
            <input id="listChkId" type="checkbox" name="listChkIdx" value="${item_index}" >
          </span>
          <input type="hidden" name="listId" id="listId" value="${item.id?c}">
        </td>
        <td width="64">
          <a href="/management/user/edit?id=${item.id?c}">
            <#if item.headImage?? && ""!=item.headImage>
              <img style="display: block; width: 64px; height: 64px;backgroud: none;" src="${item.headImage!''}"/>
            <#else>
              <b class="user-avatar"></b>
            </#if>
          </a>
        </td>
        <td>
          <div class="user-box">
            <h4><b>${item.mobile!''}</b> (昵称：${item.nickname!''})</h4>
            <i>注册时间：${item.initDate!''}</i>
            <span>
              <#--
              <a class="amount" href="http://demo.dtcms.net/admin/users/amount_log.aspx?keywords=huta123" title="消费记录">余额</a>
              <a class="card" href="http://demo.dtcms.net/admin/users/recharge_list.aspx?keywords=huta123" title="充值记录">充值</a>
              <a class="point" href="http://demo.dtcms.net/admin/users/point_log.aspx?keywords=huta123" title="积分记录">积分</a>
              <a class="msg" href="http://demo.dtcms.net/admin/users/message_list.aspx?keywords=huta123" title="消息记录">短消息</a>
              -->
            </span>
          </div>
        </td>
        <td>${item.userLevelTitle!}</td>
        <td>${item.shopTitle!}</td>
        <td>${item.lastLoginTime!'未登录过'}</td>
        <td align="center"><#if item.status?? && item.status==1>正常<#elseif item.status?? && item.status==2>待审核<#elseif item.status?? && item.status==3>禁用</#if></td>
        <td align="center"><a href="/management/user/edit?id=${item.id?c}">修改</a></td>
      </tr>
    </#list>
  </#if>
  
</tbody></table>

</div>
<!--/列表-->

<!--内容底部-->
<#assign PAGE_DATA=user_page />
<#include "/management/page_footer.ftl" />
<!--/内容底部-->
</form>

</body></html>