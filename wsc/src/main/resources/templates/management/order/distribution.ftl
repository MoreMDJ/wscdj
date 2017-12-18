<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<title>分销设置</title>
<link href="/management/css/ui-dialog.css" rel="stylesheet" type="text/css">
<link href="/management/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" charset="utf-8" src="/management/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/WdatePicker.js"></script>
<link href="/management/js/skin/WdatePicker.css" rel="stylesheet" type="text/css">
<script type="text/javascript" charset="utf-8" src="/management/js/dialog-plus-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/webuploader.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/uploader.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/laymain.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/PCASClass.js"></script>
<script type="text/javascript">
$(function () {
    //初始化表单验证
    $("#form1").initValidform();

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
});
    
function showChargeDialog() {
    var winDialog = top.dialog({
            title: '提示',
            content: '请输入充值金额：<br /><input id="amount" type="text" value="" placeholder="充值金额" style="height: 26px; margin-top: 10px;"/>',
            okValue: '充值',
            ok: function () {
                var amount = $("#amount", window.parent.document).val();
                
                if (null == amount || "" == amount)
                {
                    alert("请输入充值金额");
                    return false;
                }
                
                var amountFloat = parseFloat(amount);
                
                if (amountFloat <= 0)
                {
                    alert("充值金额必须大于0");
                    return false;
                }
                
                window.location.href="/management/shop/charge?amount=" + amountFloat;
                
                return false;
            },
            cancelValue: '取消',
            cancel: function () { }
        }).showModal();
}

function showWithdrawDialog() {
    var winDialog = top.dialog({
            title: '提示',
            content: '请输入提现金额：<br /><input id="amount" type="text" value="" placeholder="提现金额" style="height: 26px; margin-top: 10px;"/>',
            okValue: '申请提现',
            ok: function () {
                var amount = $("#amount", window.parent.document).val();
                
                if (null == amount || "" == amount)
                {
                    alert("请输入提现金额");
                    return false;
                }
                
                var amountFloat = parseFloat(amount);
                
                if (amountFloat <= 0)
                {
                    alert("提现金额必须大于0");
                    return false;
                }
                
                if (amountFloat > <#if total_deposit??>${total_deposit?string("0.00")}<#else>0</#if>)
                {
                    alert("提现金额必须小于余额");
                    return false;
                }
                
                window.location.href="/management/shop/withdraw?amount=" + amountFloat;
                
                return false;
            },
            cancelValue: '取消',
            cancel: function () { }
        }).showModal();
}
</script>
<link href="/management/js/themes/default/default.css" rel="stylesheet"></head>

<body class="mainbody">
<form method="post" action="/management/order/distribution" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="VIEWSTATE" id="__VIEWSTATE" value="">
<input type="hidden" name="id" value="<#if shop??><#if !action?? || action!="Copy">${shop.id?c}</#if></#if>">
</div>
<!--导航栏-->
<div class="location">
  <a href="/management/center.aspx" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="/management/shop/list"><span>内容管理</span></a>
  <i class="arrow"></i>
  <span>编辑内容</span>
</div>
<div class="line10"></div>
<!--/导航栏-->

<!--内容-->
<div id="floatHead" class="content-tab-wrap" style="height: 42px;">
  <div class="content-tab">
    <div class="content-tab-ul-wrap">
      <div class="tab-title"><span>分销设置</span><i></i></div><ul>
        <li><a class="selected" href="javascript:;">分销设置</a></li>
      </ul>
    </div>
  </div>
</div>

<div class="tab-content" style="display: block;">
  <dl>
    <dt><span>一级分销</span></dt>
    <dd>
      <input name="levelone" type="text" value="<#if level_one??>${level_one}<#else>0</#if>" class="input small" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" ">
      <span class="Validform_checktip">*百分比 输入 整数</span>
    </dd>
  </dl>
  <dl>
    <dt><span>二级分销</span></dt>
    <dd>
      <input name="leveltwo" type="text" value="<#if level_two??>${level_two}<#else>0</#if>" class="input small" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" ">
      <span class="Validform_checktip">*百分比 输入 整数</span>
    </dd>
  </dl>
  <dl>
    <dt><span>三级分销</span></dt>
    <dd>
      <input name="levelthree" type="text" value="<#if level_three??>${level_three}<#else>0</#if>" class="input small" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" ">
      <span class="Validform_checktip">*百分比 输入 整数</span>
    </dd>
  </dl>
  <dl>
    <dt><span>门店分销</span></dt>
    <dd>
      <input name="leveloneOne" type="text" value="<#if level_one_one??>${level_one_one}<#else>0</#if>" class="input small" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" ">
      <span class="Validform_checktip">*百分比 输入 整数</span>
    </dd>
  </dl>
 </div>
<!--/内容-->

<!--工具栏-->
<div class="page-footer">
  <div class="btn-wrap" style="position: static;">
    <input type="submit" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn">
    <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
  </div>
</div>
<!--/工具栏-->

</form>

</body></html>