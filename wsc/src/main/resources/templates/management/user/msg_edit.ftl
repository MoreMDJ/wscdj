<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<title>短信通知</title>
<link href="/management/css/ui-dialog.css" rel="stylesheet" type="text/css" />
<link href="/management/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/management/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="/management/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/WdatePicker.js"></script>

<script type="text/javascript" src="/management/js/dialog-plus-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/laymain.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/common.js"></script>
<script type="text/javascript">
    $(function () {
        //初始化表单验证
        $("#form1").initValidform();
      	//初始化编辑器
        var editor = KindEditor.create('.editor', {
            width: '100%',
            height: '350px',
            filterMode: false, //默认不过滤HTML
            resizeType: 1,
            uploadJson: '/management/tools/upload?action=EditorFile&IsWater=1',
            fileManagerJson: '/management/tools/upload?action=ManagerFile',
            allowFileManager: true
       	});
    });
</script>
</head>

<body class="mainbody">
<form id="form1" action="/management/user/msg/save" method="post">

<div class="aspNetHidden">
<input type="hidden" name="EVENTTARGET" id="EVENTTARGET" value="">
<input type="hidden" name="EVENTARGUMENT" id="EVENTARGUMENT" value="">
<input type="hidden" name="ASTFOCUS" id="LASTFOCUS" value="">
<input type="hidden" name="VIEWSTATE" id="VIEWSTATE" value="" >
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
  <a href="../center.aspx" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="user_list.aspx"><span>会员管理</span></a>
  <i class="arrow"></i>
  <span>短信通知</span>
</div>
<div class="line10"></div>
<!--/导航栏-->

<!--内容-->
<div id="floatHead" class="content-tab-wrap">
  <div class="content-tab">
    <div class="content-tab-ul-wrap">
      <ul>
        <li><a class="selected" href="javascript:;">批量短信通知</a></li>
      </ul>
    </div>
  </div>
</div>

<div class="tab-content">
			<dl>
				<dt>消息类型</dt>
				<dd>
					<div class="rule-multi-radio">
						<input id="smsType" type="radio" name="type" value="1" checked="checked" />
						<label>消息通知</label>
						<input type="radio" name="type" value="2" />
						<label >在线客服</label>
						<input type="radio" name="type" value="3" />
						<label >物流助手</label>
						<input type="radio" name="type" value="4" />
						<label >积分通知</label>
						
					</div>
				</dd>
			</dl>
			<dl>
				<dt>标题</dt>
				<dd>
					<input name="title" type="text" maxLenth = 100 class="input normal" datatype="*0-100" sucmsg=" ">
						<span class="Validform_checktip">*信息标题</span>
				</dd>
			</dl>
			<dl>
				<dt>摘要</dt>
				<dd>
					<input name="brief" type="text" maxLenth = 250 class="input normal" datatype="*5-100" sucmsg=" ">
						<span class="Validform_checktip">*信息摘要</span>
				</dd>
			</dl>
			<dl>
				<dt>详情链接</dt>
				<dd>
					<input name="linkUrl" type="text" maxLenth = 250 class="input normal" datatype="*0-250" sucmsg=" ">
						<span class="Validform_checktip">*信息详情链接</span>
				</dd>
			</dl>
			<dl>
				<dt>发布时间</dt>
				<dd>
					<input name="sendTime"
					value="" type="text" class="input rule-date-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/"
					errormsg="请选择正确的日期" sucmsg=" ">
					<span class="Validform_checktip">消息发送时间</span>
				</dd>
			</dl>
			<dl>
				<dt>消息内容</dt>
				<dd>
					<div class="ke-container ke-container-default" style="width: 100%;">
						<textarea name="content" id="contentDetail" class="editor"></textarea>
				</dd>
				<#--<dd>
					<textarea name="content" rows="2" cols="20" id="txtSmsContent1" class="input" datatype="*" tip="长短信按62个字符每条短信扣取" nullmsg="请填写短信内容！"
						onkeydown="checktxt(this, 'txtTip');"
						onkeyup="checktxt(this, 'txtTip');"
						style="padding: 0; width: 98%; height: 150px;"></textarea>
					<div class="clear"></div>
					<span id="txtTip"></span>
					<span class="Validform_checktip">请填写短信内容！</span>
				</dd>-->
			</dl>
			<dl id="div_mobiles">
				<dt>手机号码</dt>
				<dd>
					<textarea name="mobileNumbers" rows="2" cols="20"
						id="txtMobileNumbers1" class="input"
						datatype="/^\s*$|((^1\d{10})(,1\d{10})*$)+/" tip="以英文“,”逗号分隔开,为空代表发送给所有会员"
						nullmsg="请填写手机号码，多个手机号以“,”号分隔开！"
						errormsg="手机号必须是以1开头的11位数字，多个手机号以“,”号分隔开！"
						style="padding: 0; width: 98%; height: 150px;"></textarea>
					<div class="clear"></div>
					<span class="Validform_checktip">请填写手机号码，多个手机号以“,”号分隔开！->>>为空代表发送给所有会员<<<-</span>
				</dd>
			</dl>
</div>
<!--/内容-->

<!--工具栏-->
<div class="page-footer">
  <div class="btn-wrap">
    <input type="submit" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn">
    <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
  </div>
</div>
<!--/工具栏-->

</form>
</body>
</html>