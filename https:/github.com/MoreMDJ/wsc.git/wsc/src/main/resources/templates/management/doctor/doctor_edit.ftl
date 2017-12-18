<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<title>编辑内容</title>
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
<script type="text/javascript">
function checkDiy(object){
    if ($(object).val() == "全选") {
        $(object).val("取消");
       
        var checkbox1 = $(".productIdRadio:enabled").first();
        var labels = checkbox1.siblings("label");
        var MDJnumber = 0;
        labels.each(function(){
            var val = $(this).html();
            var indexNum =$(this).index();
            $(".rule-multi-checkbox").find(':checkbox').eq(MDJnumber++).prop("checked",true);
            $("a:contains('"+val+"')").addClass("selected");
            
        });
    } else {
        $(object).val("全选");
        var checkbox1 = $(".productIdRadio:enabled").first();
        var labels = checkbox1.siblings("label");
        var MDJnumber = 0;
        labels.each(function(){
            var val = $(this).html();
            var indexNum =$(this).index();
            $(".rule-multi-checkbox").find(':checkbox').eq(MDJnumber++).prop("checked",false);
            $("a:contains('"+val+"')").removeClass("selected");
        });
    }
}
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
        var editorMini = KindEditor.create('.editor-mini', {
            width: '100%',
            height: '250px',
            filterMode: false, //默认不过滤HTML
            resizeType: 1,
            uploadJson: '/management/tools/upload?action=EditorFile&IsWater=1',
            items: [
				'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
				'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
				'insertunorderedlist', '|', 'emoticons', 'image', 'link']
        });

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
        
        //初始化上传控件
        $(".upload-img").InitUploader({ filesize: "10240", sendurl: "/management/tools/upload", swf: "/management/js/uploader.swf", filetypes: "gif,jpg,png,bmp,rar,zip,doc,xls,txt" });
        $(".upload-video").InitUploader({ filesize: "102400", sendurl: "/management/tools/upload", swf: "/management/js/uploader.swf", filetypes: "flv,mp4,avi" });
        $(".upload-album").InitUploader({ btntext: "批量上传", multiple: true, water: true, thumbnail: true, filesize: "10240", sendurl: "/management/tools/upload", swf: "../../scripts/webuploader/uploader.swf" });
    });
</script>
<link href="/management/js/themes/default/default.css" rel="stylesheet"></head>

<body class="mainbody">
<form method="post" action="/management/doctor/save" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="VIEWSTATE" id="VIEWSTATE" value="">
<input type="hidden" name="id" value="<#if doctor??><#if !action?? || action!="Copy">${doctor.id?c}</#if></#if>">
</div>
<!--导航栏-->
<div class="location">
  <a href="/management/doctor/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/management/center.aspx" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="/management/doctor/list"><span>内容管理</span></a>
  <i class="arrow"></i>
  <span>编辑内容</span>
</div>
<div class="line10"></div>
<!--/导航栏-->

<!--内容-->
<div id="floatHead" class="content-tab-wrap" style="height: 42px;">
  <div class="content-tab">
    <div class="content-tab-ul-wrap">
      <div class="tab-title"><span>基本信息</span><i></i></div><ul>
        <li><a class="selected" href="javascript:;">基本信息</a></li>
      </ul>
    </div>
  </div>
</div>

<div class="tab-content" style="display: block;">
  <dl>
    <dt>姓名</dt>
    <dd>
      <input name="username" type="hidden" value="<#if doctor??>${doctor.username!''}</#if>">
      <input name="password" type="hidden" value="<#if doctor??>${doctor.password!''}</#if>">
      <input name="status" type="hidden" value="<#if doctor??>${doctor.status!''}</#if>">
      <input name="name" type="text" value="<#if doctor??>${doctor.name!''}</#if>" class="input normal" datatype="*2-20" sucmsg=" ">
      <span class="Validform_checktip">*必填、最多20个字符</span>
    </dd>
  </dl>
  <dl>
    <dt>头像</dt>
    <dd>
      <input name="headImgUrl" type="text" value="<#if doctor??>${doctor.headImgUrl!''}</#if>" class="input normal upload-path">
      <div class="upload-box upload-img"></div>
      <div class="photo-list"></div>
    </dd>
  </dl>
  <dl>
    <dt>医院</dt>
    <dd>
      <input name="hospital" type="text" value="<#if doctor??>${doctor.hospital!''}</#if>" class="input normal" datatype="*0-100" sucmsg=" ">
      <span class="Validform_checktip">曾经或现就职医院</span>
    </dd>
  </dl>
  <dl>
    <dt>在线状态</dt>
    <dd>
      <div class="rule-multi-radio">
        <span style="display: none;">
          <input type="radio" name="isOnline" value="0" <#if doctor?? || !doctor.isOnline?? || doctor.isOnline==false>checked="checked"</#if>><label>离线</label>
          <input type="radio" name="isOnline" value="1" <#if doctor?? && doctor.isOnline?? && doctor.isOnline==true>checked="checked"</#if>><label>在线</label>
        </span>
      </div>
    </dd>
  </dl>
  <dl>
    <dt>医生推荐</dt>
    <dd>
      <div class="rule-multi-radio">
        <span style="display: none;">
          <input type="radio" name="type" value="0" <#if doctor?? || !doctor.type?? || doctor.type==0>checked="checked"</#if>><label>不推荐</label>
          <input type="radio" name="type" value="1" <#if doctor?? && doctor.type?? && doctor.type==1>checked="checked"</#if>><label>推荐</label>
        </span>
      </div>
    </dd>
  </dl>
  <dl>
    <dt>科室选择</dt>
    <dd>
      <div>
        <input type="button" value="全选" class="" onclick="checkDiy(this);">
        <#-- <input type="button" value="反全选" onclick="UncheckDiy(this);">-->
      </div>
      <div class="rule-multi-checkbox">
        <span style="display: none;">
        <#if docnavi??>
        <#list docnavi as navi>
        	<input type="checkbox" class="productIdRadio" name="cat" value="${navi.title!''}" <#if doctor?? && doctor.cat?? && doctor.cat?contains("${navi.title!''}")>checked="checked"</#if>><label>${navi.title!''}</label>
        </#list>
        </#if>
          <#-- <input type="checkbox" class="productIdRadio" name="cat" value="全科" <#if doctor?? && doctor.cat?? && doctor.cat?contains("全科")>checked="checked"</#if>><label>全科</label>
          <input type="checkbox" class="productIdRadio" name="cat" value="妇科" <#if doctor?? && doctor.cat?? && doctor.cat?contains("妇科")>checked="checked"</#if>><label>妇科</label>
          <input type="checkbox" class="productIdRadio" name="cat" value="儿科" <#if doctor?? && doctor.cat?? && doctor.cat?contains("儿科")>checked="checked"</#if>><label>儿科</label>
          <input type="checkbox" class="productIdRadio" name="cat" value="皮肤科" <#if doctor?? && doctor.cat?? && doctor.cat?contains("皮肤科")>checked="checked"</#if>><label>皮肤科</label>
          <input type="checkbox" class="productIdRadio" name="cat" value="内科" <#if doctor?? && doctor.cat?? && doctor.cat?contains("内科")>checked="checked"</#if>><label>内科</label>
          <input type="checkbox" class="productIdRadio" name="cat" value="外科" <#if doctor?? && doctor.cat?? && doctor.cat?contains("外科")>checked="checked"</#if>><label>外科</label>
          <input type="checkbox" class="productIdRadio" name="cat" value="消化科" <#if doctor?? && doctor.cat?? && doctor.cat?contains("消化科")>checked="checked"</#if>><label>消化科</label>
          <input type="checkbox" class="productIdRadio" name="cat" value="牙科" <#if doctor?? && doctor.cat?? && doctor.cat?contains("牙科")>checked="checked"</#if>><label>牙科</label>
          <input type="checkbox" class="productIdRadio" name="cat" value="眼科" <#if doctor?? && doctor.cat?? && doctor.cat?contains("眼科")>checked="checked"</#if>><label>眼科</label>
          <input type="checkbox" class="productIdRadio" name="cat" value="耳鼻喉" <#if doctor?? && doctor.cat?? && doctor.cat?contains("耳鼻喉")>checked="checked"</#if>><label>耳鼻喉</label>
          <input type="checkbox" class="productIdRadio" name="cat" value="中西医结合" <#if doctor?? && doctor.cat?? && doctor.cat?contains("中西医结合")>checked="checked"</#if>><label>中西医结合</label>
          <input type="checkbox" class="productIdRadio" name="cat" value="中医科" <#if doctor?? && doctor.cat?? && doctor.cat?contains("中医科")>checked="checked"</#if>><label>中医科</label>
          <input type="checkbox" class="productIdRadio" name="cat" value="男科" <#if doctor?? && doctor.cat?? && doctor.cat?contains("男科")>checked="checked"</#if>><label>男科</label> -->
        </span>
      </div>
    </dd>
  </dl>
  <dl>
    <dt>关联QQ</dt>
    <dd>
      <input name="qq" type="text" value="<#if doctor??>${doctor.qq!''}</#if>" class="input normal" datatype="*5-20" sucmsg=" ">
      <span class="Validform_checktip">*必填、最多20个字符</span>
    </dd>
  </dl>
  <dl>
    <dt>已服务人数设置</dt>
    <dd>
      <input name="servedNo" type="text" value="<#if doctor??>${doctor.servedNo?c}</#if>" class="input normal" datatype="*1-20" sucmsg=" ">
      <span class="Validform_checktip">最多20个字符</span>
    </dd>
  </dl>
  <dl>
    <dt>当前服务人数</dt>
    <dd>
      <input name="serveNo" type="text" value="<#if doctor??>${doctor.serveNo?c}</#if>" class="input normal" datatype="*1-20" sucmsg=" ">
      <span class="Validform_checktip">最多20个字符</span>
    </dd>
  </dl>
  <dl>
    <dt>症状疾病搜索关键词</dt>
    <dd>
       <textarea name="diseaseKwd" rows="2" cols="20" class="input" datatype="*2-200" sucmsg=" "><#if doctor??>${doctor.diseaseKwd!''}</#if></textarea>
      <span class="Validform_checktip">请输入规则依次输入症状、疾病的关键词，规则：每两个关键词之间用;隔开，列入：感冒：风寒</span>
    </dd>
  </dl>
  <dl>
    <dt>擅长</dt>
    <dd>
      <textarea name="speciality" rows="2" cols="20" class="input" datatype="*2-200" sucmsg=" "><#if doctor??>${doctor.speciality!''}</#if></textarea>
      <span class="Validform_checktip">*必填、最多200个字符</span>
    </dd>
  </dl>
  <dl>
    <dt>简介</dt>
    <dd>
      <textarea name="school" rows="2" cols="20" class="input" datatype="*2-200" sucmsg=" "><#if doctor??>${doctor.school!''}</#if></textarea>
      <span class="Validform_checktip">*必填、最多200个字符</span>
    </dd>
  </dl>
  <dl>
    <dt>从业</dt>
    <dd>
      <textarea name="research" rows="2" cols="20" class="input" datatype="*2-200" sucmsg=" "><#if doctor??>${doctor.research!''}</#if></textarea>
      <span class="Validform_checktip">*必填、最多200个字符</span>
    </dd>
  </dl>
  <dl>
    <dt>在线时间</dt>
    <dd>
      <input name="motto" type="text" value="<#if doctor??>${doctor.motto!''}</#if>" class="input normal" datatype="*1-200" sucmsg=" ">
      <span class="Validform_checktip">*必填、最多200个字符</span>
    </dd>
  </dl>
  <dl>
    <dt>资质及证书上传</dt>
    <dd>
      <div class="ke-container ke-container-default" style="width: 100%;">
        <textarea name="cer" id="txtContent" class="editor"><#if doctor??>${doctor.cer!''}</#if></textarea>
    </dd>
  </dl>
  <dl>
    <dt>排序数字</dt>
    <dd>
      <input name="sort" type="text" value="<#if doctor??>${doctor.sort!'99'}<#else>99</#if>" class="input small" datatype="n" sucmsg=" ">
      <span class="Validform_checktip">*数字，越小越向前</span>
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