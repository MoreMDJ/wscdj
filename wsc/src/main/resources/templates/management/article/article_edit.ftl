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
<form method="post" action="/management/article/save" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="">
<input type="hidden" name="id" value="<#if article??><#if !action?? || action!="Copy">${article.id?c}</#if></#if>">
</div>
<!--导航栏-->
<div class="location">
  <a href="/management/article/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/management/center.aspx" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="/management/article/list"><span>内容管理</span></a>
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
        <li><a href="javascript:;" class="">详细描述</a></li>
        <li><a href="javascript:;" class="">SEO选项</a></li>
      </ul>
    </div>
  </div>
</div>

<div class="tab-content" style="display: block;">
  <dl>
    <dt>所属类别</dt>
    <dd>
      <div class="rule-single-select">
        <select name="categoryId" datatype="*" sucmsg=" " nullmsg="请选择！" class="Validform_error" style="display: none;">
  	      <option value="">请选择类别...</option>
        	<#if category_list??>
            <#list category_list as item>
              <option value="${item.id?c}" <#if article?? && article.categoryId?? && article.categoryId==item.id>selected="selected"</#if>><#if item.layerId!=1><#list 2..item.layerId as idx>　</#list>├ </#if>${item.title!''}</option>
            </#list>
          </#if>
        </select>
      </div>
    </dd>
  </dl>
  <dl>
    <dt>显示状态</dt>
    <dd>
      <div class="rule-multi-radio">
        <span style="display: none;">
          <input type="radio" name="statusId" value="1" <#if article?? && article.statusId==1>checked="checked"</#if>><label>正常</label>
          <input type="radio" name="statusId" value="2" <#if !article?? || article.statusId==2>checked="checked"</#if>><label>待审核</label>
          <input type="radio" name="statusId" value="3" <#if article?? && article.statusId==3>checked="checked"</#if>><label>不显示</label>
        </span>
      </div>
    </dd>
  </dl>
  <dl>
    <dt>跳转类型</dt>
    <dd>
      <div class="rule-multi-radio">
        <span style="display: none;">
          <input type="radio" name="type" value="0" <#if !article?? || !article.type?? || article.type==0>checked="checked"</#if>><label>文章</label>
          <input type="radio" name="type" value="1" <#if article?? && article.type?? && article.type==1>checked="checked"</#if>><label>链接</label>
        </span>
      </div>
    </dd>
  </dl>
  <#--
  <dl>
    <dt>推荐类型</dt>
    <dd>
      <div class="rule-multi-checkbox">
        <span style="display: none;">
          <input type="checkbox" name="recommendId" value="1"><label>允许评论</label>
          <input type="checkbox" name="recommendId" value="1"><label>置顶</label>
          <input type="checkbox" name="recommendId" value="1"><label>推荐</label>
      </div>
    </dd>
  </dl>
  -->
  <dl>
    <dt>内容标题</dt>
    <dd>
      <input name="title" type="text" value="<#if article??>${article.title!''}</#if>" class="input normal" datatype="*2-100" sucmsg=" ">
      <span class="Validform_checktip">*标题最多100个字符</span>
    </dd>
  </dl>
  <dl>
    <dt>Tags标签</dt>
    <dd>
      <input name="tags" type="text" value="<#if article??>${article.tags!''}</#if>" class="input normal" datatype="*0-500" sucmsg=" ">
      <span class="Validform_checktip">多个可用英文逗号分隔开，如：a,b</span>
    </dd>
  </dl>
  <dl>
    <dt>封面图片</dt>
    <dd>
      <input name="coverImgUri" id="coverImgUri" type="text" value="<#if article??>${article.coverImgUri!''}</#if>" class="input normal upload-path">
      <div class="upload-box upload-img"></div>
      <div class="photo-list"></div>
    </dd>
  </dl>
  <dl>
    <dt>排序数字</dt>
    <dd>
      <input name="sortId" type="text" value="<#if article??>${article.sortId!'99'}<#else>99</#if>" class="input small" datatype="n" sucmsg=" ">
      <span class="Validform_checktip">*数字，越小越向前</span>
    </dd>
  </dl>
  <dl>
    <dt>浏览次数</dt>
    <dd>
      <input name="viewCount" type="text" value="<#if article??>${article.viewCount!''}<#else>0</#if>" class="input small" datatype="n" sucmsg=" ">
      <span class="Validform_checktip">点击浏览该信息自动+1</span>
    </dd>
  </dl>
  <dl>
    <dt>发布时间</dt>
    <dd>
      <div class="date-input"><i></i><input name="createTime" value="<#if article?? && article.createTime??>${article.createTime?string("yyyy-MM-dd HH:mm:ss")}</#if>" type="text" class="input rule-date-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" "></div>
      <span class="Validform_checktip">不选择默认当前发布时间</span>
    </dd>
  </dl>
</div>



<div class="tab-content" style="display: none;">
  <dl>
    <dt>URL链接</dt>
    <dd>
      <input name="linkUri" type="text" maxlength="255" value="<#if article??>${article.linkUri!''}</#if>" class="input normal">
      <span class="Validform_checktip">填写后直接跳转到该网址</span>
    </dd>
  </dl>
  <dl>
    <dt><span>信息来源</span></dt>
    <dd>
      <input name="source" type="text" value="<#if article??>${article.source!''}</#if>" class="input normal">
      <span class="Validform_checktip">非必填，最多50个字符</span>
    </dd>
  </dl>
  <dl id="div_author">
    <dt><span id="div_author_title">文章作者</span></dt>
    <dd>
      <input name="author" type="text" value="<#if article??>${article.author!''}</#if>" class="input normal" datatype="s0-50" sucmsg=" ">
      <span class="Validform_checktip">非必填，最多50个字符</span>
    </dd>
  </dl>
  <dl>
    <dt>内容摘要</dt>
    <dd>
      <textarea name="brief" rows="2" cols="20" class="input" datatype="*0-255" sucmsg=" "><#if article??>${article.brief!''}</#if></textarea>
      <span class="Validform_checktip">不填写则自动截取内容前255字符</span>
    </dd>
  </dl>
  <dl>
    <dt>内容描述</dt>
    <dd>
      <div class="ke-container ke-container-default" style="width: 100%;">
        <textarea name="content" id="txtContent" class="editor"><#if article??>${article.content!''}</#if></textarea>
    </dd>
  </dl>
</div>

<div class="tab-content" style="display: none;">
  <dl>
    <dt>SEO标题</dt>
    <dd>
      <input name="seoTitle" type="text" maxlength="255" value="<#if article??>${article.seoTitle!''}</#if>" class="input normal" datatype="*0-100" sucmsg=" ">
      <span class="Validform_checktip">255个字符以内</span>
    </dd>
  </dl>
  <dl>
    <dt>SEO关健字</dt>
    <dd>
      <textarea name="seoKeywords" rows="2" cols="20" class="input" datatype="*0-255" sucmsg=" "><#if article??>${article.seoKeywords!''}</#if></textarea>
      <span class="Validform_checktip">以“,”逗号区分开，255个字符以内</span>
    </dd>
  </dl>
  <dl>
    <dt>SEO描述</dt>
    <dd>
      <textarea name="seoDescription" rows="2" cols="20" class="input" datatype="*0-255" sucmsg=" "><#if article??>${article.seoDescription!''}</#if></textarea>
      <span class="Validform_checktip">255个字符以内</span>
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