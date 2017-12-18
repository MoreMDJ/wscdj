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
      //权限全选
        $("input[name='checkAll']").click(function () {
            if ($(this).prop("checked") == true) {
                $(this).parent().siblings("td").find("input[type='checkbox']").prop("checked", true);
            } else {
                $(this).parent().siblings("td").find("input[type='checkbox']").prop("checked", false);
            }
        });
    });
</script>
<link href="/management/js/themes/default/default.css" rel="stylesheet"></head>

<body class="mainbody">
<form method="post" action="/management/manager/role/save" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="">
<input type="hidden" name="id" value="<#if role??>${role.id?c}</#if>">
</div>
<!--导航栏-->
<div class="location">
  <a href="/management/manager/role/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/management/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="/management/manager/role/list"><span>内容管理</span></a>
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
    <dt>所属类别</dt>
    <dd>
      <div class="rule-single-select">
        <select name="categoryId" datatype="*" sucmsg=" " nullmsg="请选择！" class="Validform_error" style="display: none;">
  	      <option value="">请选择类别...</option>
  	      <option value="0" <#if role?? && role.categoryId?? && role.categoryId==0>selected="selected"</#if>>超级管理员</option>
  	      <option value="1" <#if role?? && role.categoryId?? && role.categoryId==1>selected="selected"</#if>>管理员</option>
        </select>
      </div>
    </dd>
  </dl>
  <dl>
    <dt>角色名称</dt>
    <dd>
      <input name="title" type="text" value="<#if role??>${role.title!''}</#if>" class="input normal" datatype="*2-100" sucmsg=" ">
      <span class="Validform_checktip">*标题最多100个字符</span>
    </dd>
  </dl>
  <dl>
    <dt>排序数字</dt>
    <dd>
      <input name="sortId" type="text" value="<#if role??>${role.sortId!'99'}<#else>99</#if>" class="input small" datatype="n" sucmsg=" ">
      <span class="Validform_checktip">*数字，越小越向前</span>
    </dd>
  </dl>
  <dl>
    <dt>管理权限</dt>
    <dd>
      <table border="0" cellspacing="0" cellpadding="0" class="border-table" width="98%">
        <thead>
          <tr>
            <th width="30%">导航名称</th>
            <th>权限分配</th>
            <th width="10%">全选</th>
          </tr>
        </thead>
        <tbody>
          <#if navigation_menu_list??>
            <#list navigation_menu_list as item>
              <tr>
                <td style="white-space:nowrap;word-break:break-all;overflow:hidden;">
                  <#if item.layerId??>
                    <#if item.layerId gt 1>
                      <span style="display:inline-block;width:${(item.layerId-2)*24}px;"></span>
                      <span class="folder-line"></span>
                    </#if>
                  </#if>
                  <input type="hidden" name="permissions[${item_index}].navName" value="${item.name!''}">
                  <span class="folder-open"></span>
                  ${item.title!''}
                </td>
                <td>
                  <span class="cbllist">
                    <#if item.actionType??>
	                    <#list item.actionType?split(",") as action>
	                    	<input type="hidden" name="permissions[${item_index}].id" value="<#if role?? && role.permissions?? && (role.permissions)?size gt item_index  && role.permissions[item_index]??>${role.permissions[item_index].id?c}</#if>">
		                    <#if action=="Show">
		                    <input type="checkbox" name="menuIds" value="${item.id?c}" <#if !role?? || role.permission?contains("["+item.id+"]")>checked="checked"</#if>
		                    <label"> 显示 </label>
		                    <#else>
		                    <input type="checkbox" name="permissions[${item_index}].actionType" value="${action!''}" <#if role?? && role.permissions?? && (role.permissions)?size gt item_index  && role.permissions[item_index].actionType?? && role.permissions[item_index].actionType?contains(action) >checked="checked"</#if>>
		                    <label>${actionType[action]}</label>
		                    </#if>
	                    </#list>
                    </#if>
                  </span>
                </td>
                <td align="center">
                <input name="checkAll" type="checkbox">
                </td>
              </tr>
            </#list>
          </#if>
        </tbody>
      </table>
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