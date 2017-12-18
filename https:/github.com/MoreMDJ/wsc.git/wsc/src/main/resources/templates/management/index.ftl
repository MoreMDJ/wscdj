<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<title>后台管理中心</title>
<link href="/management/css/ui-dialog.css" rel="stylesheet" type="text/css">
<link href="/management/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" charset="utf-8" src="/management/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/jquery.nicescroll.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/dialog-plus-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/layindex.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/common.js"></script>
<script type="text/javascript">
//页面加载完成时
$(function () {    
    //检测IE
    if ('undefined' == typeof(document.body.style.maxHeight)){
        window.location.href = '/management/tools/ie6update';
    }
    
   /*   setInterval("remind()", 60000);  */
});

//下单自动提醒
function remind(){    
    $.ajax({
          type: "post",
          url: "/management/remind",
          dataType: "json",
          success: function (res) {   
              if (res.error == 0)
              {
                  alert('有新订单，请注意查看');
              }
          }
      }); 
}
</script>
</head>

<body class="indexbody">
<form method="post" action="/management/index" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="">
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="">
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
            
            <#if sessionShopId??>
            window.open("/management/shop/charge?amount=" + amountFloat);
            <#elseif sessionSupplierId??>
            window.open("/management/supplier/charge?amount=" + amountFloat);
            </#if>
            
            return true;
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
            
            
            <#if sessionShopId??>
            window.open("/management/shop/withdraw?amount=" + amountFloat);
            <#elseif sessionSupplierId??>
            window.open("/management/supplier/withdraw?amount=" + amountFloat);
            </#if>
            
            return true;
        },
        cancelValue: '取消',
        cancel: function () { }
    }).showModal();
}
</script>

  <!--全局菜单-->
  <a class="btn-paograms" onclick="togglePopMenu();"></a>
  <div id="pop-menu" class="pop-menu">
    <div class="pop-box" style="width: 610px;">
      <h1 class="title"><i></i>导航菜单</h1>
      <i class="close" onclick="togglePopMenu();">关闭</i>
      <div class="list-box"></div>
    </div>
    <i class="arrow">箭头</i>
  </div>
  <!--/全局菜单-->

  <div class="main-top">
    <a class="icon-menu"></a>
    <div id="main-nav" class="main-nav">
    </div>
    <div class="nav-right">
      <div class="info">
        <div style="float: left;">
          <#if sessionShopId?? || sessionSupplierId??>
            <a href="/management/center" target="mainframe" style="text-decoration: none; cursor: pointer; color: rgb(25, 145, 31); margin-right: 10px;">我的钱包</a>
          </#if>
        </div>
        <i></i>
        <span>
          您好，<#if Manager??>${Manager.username!}<#else>admin</#if><#if Manager??>${Manager.roleTitle!}<#else>超级管理员</#if> 
          <br>0.0</br>
        </span>
      </div>
      <div class="option">
        <i></i>
        <div class="drop-wrap">
          <div class="arrow"></div>
          <ul class="item">
            <#--
            <li>
              <a href="/" target="_blank">预览网站</a>
            </li>
            -->
            <#if sessionShopId?? || sessionSupplierId??>
            <li>
              <a href="/management/manager/paypassword" target="mainframe">设置支付密码</a>
            </li>
            <li>
              <a href="/management/manager/password" target="mainframe">修改密码</a>
            </li>
            </#if>
            <li>
              <a href="/management/center" target="mainframe">我的钱包</a>
            </li>
            <li>
              <a id="lbtnExit" href="/management/logout">注销登录</a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
  
  <div class="main-left">
    <h1 class="logo"></h1>
    <div id="sidebar-nav" class="sidebar-nav"></div>
  </div>
  
  <div class="main-container">
    <iframe id="mainframe" name="mainframe" frameborder="0" src="/management/center"></iframe>
  </div>

</form>
</body>
</html>