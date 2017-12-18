<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<title>编辑会员</title>
<link href="/management/css/ui-dialog.css" rel="stylesheet" type="text/css">
<link href="/management/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" charset="utf-8" src="/management/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/WdatePicker.js"></script>
<link href="/management/js/skin/WdatePicker.css" rel="stylesheet" type="text/css">
<script type="text/javascript" charset="utf-8" src="/management/js/dialog-plus-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/webuploader.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/uploader.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/laymain.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/common.js"></script>
<script type="text/javascript">
$(function () {
    //初始化表单验证
    $("#form1").initValidform();
    //初始化上传控件
    $(".upload-img").InitUploader({ filesize: "10240", sendurl: "/management/tools/upload", swf: "/management/js/uploader.swf", filetypes: "jpg,png" });

    // blur事件
    $("input.upload-path").blur(function(){
      if (null != $(this).val())
      {
        var newLi = "";
        if (""!=$(this).val())
        { 
          newLi = $('<ul><li>'
                    + '<div class="img-box">'
                    + '<img src="' + $(this).val() + '" />'
                    + '</div>'
                    + '</li></ul>');
        }
       
                      
        $(this).siblings(".photo-list").html(newLi);
      }
    });
    
    // 触发blur事件
    $('input.upload-path').trigger("blur");
    
    $("#changeBtnId").click(function () { changeUper(); });   //修改门店
    
  	//修改门店
    function changeUper() {
        var winDialog = top.dialog({
            title: '选择上级',
            url: '/management/user/edit/changeStore?uid=' + $("#userId").val(),
            width: 750,
            data: window //传入当前窗口
        }).showModal();
    }
});
function sendAjaxUrl(winObj, postData, sendUrl) {
    $.ajax({
        type: "post",
        url: sendUrl,
        data: postData,
        dataType: "json",
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            top.dialog({
                title: '提示',
                content: '尝试发送失败，错误信息：' + errorThrown,
                okValue: '确定',
                ok: function () { }
            }).showModal(winObj);
        },
        success: function (data, textStatus) {
            if (data.status == 1) {
                winObj.close().remove();
                var d = dialog({ content: data.msg }).show();
                setTimeout(function () {
                    d.close().remove();
                    location.reload(); //刷新页面
                }, 2000);
            } else {
                top.dialog({
                    title: '提示',
                    content: '错误提示：' + data.msg,
                    okValue: '确定',
                    ok: function () { }
                }).showModal(winObj);
            }
        }
    });
}
</script>
</head>

<body class="mainbody">
<form method="post" action="/management/user/save" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="">
<input type="hidden" id="userId" name="id" value="<#if user??><#if !action?? || action!="Copy">${user.id?c}</#if></#if>">
</div>
<!--导航栏-->
<div class="location">
  <a href="/management/user/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/management/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="/management/user/list"><span>会员管理</span></a>
  <i class="arrow"></i>
  <span>编辑会员</span>
</div>
<div class="line10"></div>
<!--/导航栏-->

<!--内容-->
<div id="floatHead" class="content-tab-wrap">
  <div class="content-tab">
    <div class="content-tab-ul-wrap">
      <div class="tab-title"><span>基本资料</span><i></i></div><ul>
        <li><a class="selected" href="javascript:;">基本资料</a></li>
        <li><a href="javascript:;">账户信息</a></li>
        <li><a href="javascript:;">分销信息</a></li>
      </ul>
    </div>
  </div>
</div>

<div class="tab-content">
  <#--
  <dl>
    <dt>所属组别</dt>
    <dd>
      <div class="rule-single-select single-select">
        <select name="ddlGroupId" id="ddlGroupId" datatype="*" errormsg="请选择组别" sucmsg=" " style="display: none;">
	<option value="">请选择组别...</option>
	<option selected="selected" value="1">注册会员</option>
	<option value="2">高级会员</option>
	<option value="3">中级会员</option>
	<option value="4">钻石会员</option>

</select>
      </div>
    </dd>
  </dl>
  -->
  <dl>
    <dt>用户状态</dt>
    <dd>
      <div class="rule-multi-radio">
        <span style="display: none;">
          <input type="radio" name="status" value="1" <#if user?? && user.status?? && user.status==1>checked="checked"</#if>><label>正常</label>
          <input type="radio" name="status" value="2" <#if user?? && user.status?? && user.status==2>checked="checked"</#if>><label>待审核</label>
          <input type="radio" name="status" value="3" <#if user?? && user.status?? && user.status==3>checked="checked"</#if>><label>禁用</label>
        </span>
      </div>
    </dd>
  </dl>
  <dl>
    <dt>用户名</dt>
    <dd>
      <input name="username" type="text" value="<#if user??>${user.username!}" readonly="readonly"<#else>"</#if> ajaxurl="/management/user/check<#if user??>?id=${user.id?c}</#if>" class="input normal" datatype="*2-100" sucmsg=" "> 
      <span class="Validform_checktip">*登录的用户名</span></dd>
  </dl> 
  <dl>
    <dt>性别</dt>
    <dd>
      <div class="rule-multi-radio">
        <span style="display: none;">
          <input type="radio" name="sex" value="1" <#if user?? && user.sex?? && user.sex==1>checked="checked"</#if>><label>男</label>
          <input type="radio" name="sex" value="2" <#if user?? && user.sex?? && user.sex==2>checked="checked"</#if>><label>女</label>
          <input type="radio" name="sex" value="3" <#if user?? && user.sex?? && user.sex==3>checked="checked"</#if>><label>保密</label>
        </span>
      </div>
    </dd>
  </dl>
  <dl>
    <dt>真实姓名</dt>
    <dd><input name="realName" type="text" class="input normal" value="<#if user??>${user.realName!''}</#if>"></dd>
  </dl>
  <dl>
    <dt>昵称</dt>
    <dd><input name="nickname" type="text" class="input normal" value="<#if user??>${user.nickname!''}</#if>"></dd>
  </dl>
  <dl>
    <dt>头像</dt>
    <dd>
      <input name="headImage" type="text" value="<#if user??>${user.headImage!''}</#if>" class="input normal upload-path">
      <div class="upload-box upload-img"></div>
      <div class="photo-list"></div>
    </dd>
  </dl>
  <dl>
    <dt>邮箱地址</dt>
    <dd><input name="email" type="text" class="input normal" value="<#if user??>${user.email!}</#if>"></dd>
  </dl>
  <dl>
    <dt>生日日期</dt>
    <dd>
      <div class="date-input"><i></i><input name="birthday" type="text" id="txtBirthday" class="input rule-date-input" onfocus="WdatePicker({dateFmt:&#39;yyyy-MM-dd&#39;})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" " value="<#if user?? && user.birthday??>${user.birthday?string("yyyy-MM-dd")}</#if>"></div>
    </dd>
  </dl>
  
</div>

<div class="tab-content" style="display:none;">
  <dl>
    <dt>设置<#if user??>新</#if>密码</dt>
    <dd><input name="password" type="password" value="" class="input normal" datatype="*6-20" ignore="ignore" nullmsg="请设置密码" errormsg="密码范围在6-20位之间" sucmsg=" " value=""> <span class="Validform_checktip">*<#if user??>新密码将覆盖原密码，至少6位<#else>为空则启用默认密码：123456</#if></span></dd>
  </dl>
  <dl>
    <dt>确认密码</dt>
    <dd><input name="rePassword" type="password" value="" class="input normal" datatype="*" ignore="ignore" recheck="password" nullmsg="请再输入一次密码" errormsg="两次输入的密码不一致" sucmsg=" " value=""> <span class="Validform_checktip">*再次输入密码</span></dd>
  </dl>
  <#if user??>
  <dl>
    <dt>注册时间</dt>
    <dd><span id="lblRegTime"><#if user??>${user.initDate!''}</#if></span></dd>
  </dl>
  <dl>
    <dt>最近登录时间</dt>
    <dd><span id="lblLastTime"><#if user??>${user.lastLoginTime!'未登录'}</#if></span></dd>
  </dl>
  </#if>
</div>
<div class="tab-content" style="display:none;">
  <#if relations??>
  <input type="hidden" name="relationsid" value="${relations.id?c}">
  <#if user??>
  	<dl>
    <dt>上级</dt>
    <dd>
      <span style="padding-right: 10px;">${super_username!'无'}</span>
      <a id="changeBtnId" class="icon-btn add"><i></i><span>更换</span></a>
    </dd>
  </dl>
  <dl>
    <dt>二维码类型</dt>
    <dd>
      <#if user.uType?? && user.uType==2>永久<#else>临时</#if>
    </dd>
  </dl>
  <dl>
    <dt>用户类型</dt>
    <dd>
      <div class="rule-multi-radio">
        <span style="display: none;">
          <input type="radio" name="uRole" value="1" <#if user.uRole?? && user.uRole==1>checked="checked"</#if>><label>门店</label>
          <input type="radio" name="uRole" value="2" <#if user.uRole?? && user.uRole==2>checked="checked"</#if>><label>店员</label>
          <input type="radio" name="uRole" value="3" <#if !user.uRole?? || user.uRole==3>checked="checked"</#if>><label>会员</label>
        </span>
      </div>
    </dd>
  </dl>
  <#if user.uType?? && user.uType==2>
  <dl>
   <dt>二维码</dt>
   <ul><li><div class="img-box"><img src="/management/user/spreadimg/${user.id?c}"></div></li></ul>
  </dl>
  </#if>
  </#if>
  <dl>
    <dt>分销</dt>
    <dd>
      <div class="rule-multi-radio">
        <span style="display: none;">
          <input type="radio" name="relationsenable" value="1" <#if relations.enable?? && relations.enable==true>checked="checked"</#if>><label>启用</label>
          <input type="radio" name="relationsenable" value="0" <#if !relations.enable?? || relations.enable==false>checked="checked"</#if>><label>禁用</label>
        </span>
      </div>
    </dd>
  </dl>
  <dl>
  <dt>分销会员数量</dt>
  <dd><#if relation_number?? && relation_number gt 0>${relation_number} |  <a href="/management/user/distribution?uid=${relations.uid?c}">点击此处</a><#else>0</#if></dd>
  </dl>
  <#else>
  <#if user??>
  <dl>
    <dt>开启分销</dt>
    <dd>
      <div class="rule-multi-checkbox">
        <span style="display: none;">
          <input type="checkbox" name="relationsopen" value="1" ><label>开启</label>
        </span>
      </div>
    </dd>
  </dl>
  <#else>
  <dl>
    <dt>分销二维码</dt>
    <dd>
      <div class="rule-multi-checkbox">
        <span style="display: none;">
          <input type="checkbox" name="UTpye" value="1" ><label>永久</label>
        </span>
      </div>
    </dd>
  </dl>
  </#if>
  </#if>
</div>
<!--/内容-->

<!--工具栏-->
<div class="page-footer">
  <div class="btn-wrap" style="position: fixed;">
    <input type="submit" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn">
    <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
  </div>
</div>
<!--/工具栏-->

</form>


</body></html>