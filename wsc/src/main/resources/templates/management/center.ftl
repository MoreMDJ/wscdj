<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport"
	content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<title>管理首页</title>
<link href="/management/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" charset="utf-8"
	src="/management/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="/management/js/layindex.js"></script>
<script type="text/javascript" charset="utf-8"
	src="/management/js/common.js"></script>
</head>
<script>
<#if shop??>
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
        content: '请输入金额与密码：<br /><input id="amount" type="text" value="" placeholder="提现金额" style="height: 26px; margin-top: 10px;"/><br /><input id="payPassword" type="password" value="" placeholder="支付密码" style="height: 26px; margin-top: 10px;"/>',
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
            
            var payPassword = $("#payPassword", window.parent.document).val();
            
            if ("" == payPassword)
            {
                alert("请输入支付密码");
                return false;
            }
            
            if ("${payPassword!}" != payPassword)
            {
                alert("支付密码错误");
                return false;
            }
            
            window.location.href="/management/shop/withdraw?amount=" + amountFloat;
            
            return false;
        },
        cancelValue: '取消',
        cancel: function () { }
    }).showModal();
}
<#elseif supplier??>
function showWithdrawDialog() {
    var winDialog = top.dialog({
            title: '提示',
            content: '请输入提现金额：<br /><input id="amount" type="text" value="" placeholder="提现金额" style="height: 26px; margin-top: 10px;"/><br /><input id="payPassword" type="password" value="" placeholder="支付密码" style="height: 26px; margin-top: 10px;"/>',
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
                
                if (amountFloat > ${total_deposit!0})
                {
                    alert("提现金额必须小于余额");
                    return false;
                }
                
                var payPassword = $("#payPassword", window.parent.document).val();
            
                if ("" == payPassword)
                {
                    alert("请输入支付密码");
                    return false;
                }
                
                if ("${payPassword!}" != payPassword)
                {
                    alert("支付密码错误");
                    return false;
                }
                
                window.location.href="/management/supplier/withdraw?amount=" + amountFloat;
                
                return false;
            },
            cancelValue: '取消',
            cancel: function () { }
        }).showModal();
}
</#if>
</script>
<body class="mainbody">
	<form method="post" action="center.html" id="form1">
		<div class="aspNetHidden">
			<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="" />
		</div>

		<!--导航栏-->
		<div class="location">
			<a href="javascript:history.back(-1);" class="back"><i></i><span>返回上一页</span></a>
			<a class="home"><i></i><span>首页</span></a> <i class="arrow"></i> <span>管理中心</span>
		</div>
		<!--/导航栏-->

		<!--内容-->
		<div class="line10"></div>
		<div class="nlist-1">
			<#--
			<ul>
				<li>本次登录IP：106.117.64.123</li>
				<li>上次登录IP：118.205.180.209</li>
				<li>上次登录时间：2016-2-20 7:51:41</li>
			</ul>
			-->
		</div>
		<div class="line10"></div>

		<div class="nlist-2">
			<h3>
				<i></i>管理系统信息
			</h3>
			<ul>
				<li>站点名称：真心善美管理后台</li>
				<li>站点名称：${site_name!''}</li>
				<li>公司名称：${company_name!''}</li>
				<li>网站域名：${server_name!("")}</li>
				<li>安装目录：/</li>
				<li>网站管理目录：managment</li>
				<li>附件上传目录：upload</li>
				<li>服务器名称：${server_name!("")}</li>
				<li>服务器IP：${server_ip!("")}</li>
				<li>Java版本：${java_version!("")}</li>
				<li>操作系统：${os_name!("sss")}</li>
				<li>服务器端口：${server_port?c!("")}</li>
				<li>目录物理路径：${java_home!("")}</li>
			</ul>
		</div>
		<div class="line20"></div>

		<!--/内容-->

	</form>


</body>
</html>