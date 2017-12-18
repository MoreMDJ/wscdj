<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN">
<title>医生首页---${doctor.name!''}</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<!--[if IE]>
   <script src="js/html5.js"></script>
<![endif]-->


<link href="/wx/css/pc_common.css" rel="stylesheet" type="text/css" />

<link href="/wx/css/pc_doctor.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/wx/js/jquery-2.1.0.min.js"></script>
<!--<script type="text/javascript" src="/wx/js/common.js"></script>-->
<script type="text/javascript">

$(function (){
	<#if msg??>
	setTimeout(warning("${msg!''}"),10);
	</#if>
  
    $("#btnSmsCode").click(function()
	{
        
        var mob = $("#mobileId").val();
        var re = /^\d{1,}$/;
        
        if (!re.test(mob))
        {
            alert("请输入正确数字");
            return;
        }
        $("#btnSmsCode").attr("disabled","disabled"); 
        
        $.ajax({  
            url : "/wx/doctor/number",  
            async : true,  
            type : 'POST',  
            data : {"number": mob},  
            success : function(data) {  
                
                if(data.error == 1)
                {  
                    alert("修改成功");
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
                alert(textStatus);
            }  
  
        });
    });
});

var seed=300;    //60秒  
var t1=null; 

function tip() 
{  
    seed--;  
    if (seed < 1) 
    {  
        enableBtn();  
        seed = 300;  
        $("#btnSmsCode").val('确定');  
        var t2 = clearInterval(t1);  
    } else {  
        $("#btnSmsCode").val(seed + "s后确定");  
    }  
} 

function enableBtn()
{  
    $("#btnSmsCode").removeAttr("disabled");   
}

function changeStatus()
{
	$("#form1").submit();
}
</script>

</head>
<form id="form1" action="/wx/doctor/status" method="post">
<body style=" background-image: url(/wx/images/pc_doctor/login-bg.png);">

<div class="m_bg_top"><img src="/wx/images/pc_doctor/bg_top.png"></div>

  <section class="m_container">

    <div class="m_container_left">
      <div class="m_c_left_1">
        <img src="${doctor.headImgUrl!''}">
        <select id="onlineId" name="number" onchange="javascript:changeStatus();">
          <option value="1" <#if doctor.isOnline==true>selected="selected"</#if> >在线</option>
          <option value="0" <#if doctor.isOnline==false>selected="selected"</#if> >离线</option>
        </select>
      </div>

      <div class="m_c_left_2">
        <dl>
          <dt>擅长介绍</dt>
          <dd>${doctor.speciality!''}</dd>
        </dl>
      </div>

      <div class="m_c_left_2">
        <dl>
          <dt>医学教育背景</dt>
          <dd>${doctor.school!''}</dd>
        </dl>
      </div>

      <div class="m_c_left_2">
        <dl>
          <dt>学术研究成果、获奖情况</dt>
          <dd>${doctor.research!''}</dd>
        </dl>
      </div>

      <div class="m_c_left_2">
        <dl>
          <dt>座右铭</dt>
          <dd>${doctor.motto!''}</dd>
        </dl>
      </div>

    </div>
    <!-- m_container_left end -->

	<div class="m_container_right">
		<div class="m_container_right_1">
			<label>${doctor.name!''}</label>
			<span>${doctor.cat!''}</span>
			<span>${doctor.hospital!''}</span>
		</div>
		
		<div class="m_container_right_2">
			<div class="m_container_right_2_left">
				<p><span>当前咨询人数</span></p>
				<p><input id="mobileId" type="tel"  <#if number??>value="${number?c}"</#if>/></p>
				<p><input type="button" id="btnSmsCode" value="确定" class="m_btn1"></p>
				
				<label>
将心比心，用我的爱心:诚心:细心，换您的舒心:放心:安心。
医生对我胜如亲人，能设身处地为患者着想，牺牲自己的休息时间及时为我解除了病痛，令我太感动了……
				</label>
			</div>
			
			<div class="m_container_right_2_right">
				${doctor.cer!'没有相关证书'}
			</div>
		</div>
		
	</div>
	<!-- m_container_right end -->



  </section>
 

  
 
</body>
</form>
</html>
