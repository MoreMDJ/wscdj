<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>忘记密码</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="initial-scale=1,maximum-scale=1,minimum-scale=1">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<!-- css -->
<link href="/wx/css/common.css" rel="stylesheet" type="text/css" />
<link href="/wx/css/main.css" rel="stylesheet" type="text/css" />
<link href="/wx/css/swiper.min.css" rel="stylesheet" type="text/css" />
<link href="/wx/css/my_swiper.css" rel="stylesheet" type="text/css" />
<!-- js -->
<script src="/wx/js/jquery-2.1.0.min.js" charset="utf-8" type="text/javascript"></script>
<script type="text/javascript" src="/wx/js/common.js"></script>
<script type="text/javascript">
$(function (){
  
    $("#btnSmsCode").click(function()
	{
        
        var mob = $("#mobileId").val();
        var re = /^1\d{10}$/;
        
        if (!re.test(mob))
        {
            warning("请输入正确的手机号");
            return;
        }
     //   $("#btnSmsCode").attr("disabled","disabled"); 
        
        $.ajax({  
            url : "/wx/login/code",  
            async : true,  
            type : 'POST',  
            data : {"mobile": mob},  
            success : function(data) {  
                
                if(data.status == 'y')
                {  
                    warning("验证码发送成功");
                    t1 = setInterval(tip, 1000);  
                }
                else
                {
                    warning(data.info);
                    $("#btnSmsCode").removeAttr("disabled");
                }
            },  
            error : function(XMLHttpRequest, textStatus,  
                    errorThrown) {  
                warning("error");
            }  
  
        });
    });
});

var seed=60;    //60秒  
var t1=null; 

function tip() 
{  
    seed--;  
    if (seed < 1) 
    {  
        enableBtn();  
        seed = 60;  
        $("#btnSmsCode").val('获取验证码');  
        var t2 = clearInterval(t1);  
    } else {  
        $("#btnSmsCode").val(seed + "s后获取");  
    }  
} 

function enableBtn()
{  
    $("#btnSmsCode").removeAttr("disabled");   
}

function codeCheck()
{
	 var mob = $("#mobileId").val();
	 var code = $("#codeId").val();
     var re = /^1\d{10}$/;
     
     if (!re.test(mob))
     {
         warning("请输入正确的手机号");
         return;
     }
	$.ajax({  
        url : "/wx/login/codeCheck",  
        async : true,  
        type : 'POST',  
        data : {"mobile": mob,"code":code},  
        success : function(data) {  
            
            if(data.status == 'y')
            {
                window.location.href="/wx/login/passwordNew?code=" + data.info;
            }
            else
            {
                warning(data.info);
                $("#btnSmsCode").removeAttr("disabled");
            }
        },  
        error : function(XMLHttpRequest, textStatus,  
                errorThrown) {  
            warning("error");
        }  

    });
}

function check()
{
	var mobile = $("#mobileId").val();
	var password = $("#passwordId").val();
	if(mobile=null || mobile=="" || mobile==undefined)
		{
			warning("请输入手机号");
			document.getElementById("mobileId").focus()
			return false;
		}
		
		if(!/^1\d{10}$/.test(mobile))
		{
			warning("请输入正确的手机号");
			document.getElementById("mobileId").focus()
			return false;
		}
		
	if(password=null || password=="" || password==undefined)
	{
		warning("请输入密码");
		document.getElementById("paswordId").focus()
		return false;
	}
}
</script>
</head>
<body class="bgwhite">
<div class="top_size"></div>
<div class="clear"></div>
<#-- 引入警告提示样式 -->
<#include "/common/common_warn.ftl">
<#-- 引入等待提示样式 -->
<#include "/common/common_wait.ftl">

<!-- header -->
<section class="white_header">
    <a href="javascript:history.go(-1)" class="hleft headleft header"><img src="/wx/images/leftback.png"> </a>
    <div class="htitle header">忘记密码</div>
    <a class="hright headright header"></a>
</section>
<!-- header end -->



<!-- pwdtextlinebox -->
<section class="pwdtextlinebox">
    <form method="post">
    <span>
        <input type="tel" onkeyup="onlyInt(this)" onafterpaste="onlyInt(this)" name="mobile" id="mobileId" placeholder="手机号">
    </span>
    <span>
        <input type="text" name="code" id="codeId" placeholder="验证码" class="login_pwd" style="width: 50%;">
        <input type="button" id="btnSmsCode" class="code"  value="获取验证码"></input>
    </span>
        <!-- 条件符合后，登录样式用subyes，未符合用sub -->
        <input type="button" onclick="codeCheck()" value="验 证" class="sub">
    </form>
</section>
<!-- pwdtextlinebox end -->






</body>
</html>
