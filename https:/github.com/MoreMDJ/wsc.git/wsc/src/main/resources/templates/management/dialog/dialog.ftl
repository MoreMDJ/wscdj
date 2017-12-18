<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<title>修改收货信息窗口</title>
<link href="/management/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" charset="utf-8" src="/management/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/PCASClass.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/common.js"></script>
<script type="text/javascript">
    var api = top.dialog.get(window); //获取窗体对象
    var W = api.data;
    //页面加载完成执行
    $(function () {
        //设置按钮及事件
        api.button([{
            value: '确定',
            callback: function () {
                submitForm();
            },
            autofocus: true
        }, {
            value: '取消',
            callback: function () { }
        }]);
        //初始化省市区
        var mypcas = new PCAS("txtProvince,所属省份", "txtCity,所属城市", "txtArea,所属地区");
        var areaArr = $("#spanArea", W.document).text().split(",");
        if (areaArr.length == 3) {
            mypcas.SetValue(areaArr[0], areaArr[1], areaArr[2]);
        }
        $("#txtAcceptName").val($("#spanAcceptName", W.document).text());
        $("#txtAddress").val($("#spanAddress", W.document).text());
        $("#txtPostCode").val($("#spanPostCode", W.document).text());
        $("#txtMobile").val($("#spanMobile", W.document).text());
        $("#txtTelphone").val($("#spanTelphone", W.document).text());
        $("#txtEmail").val($("#spanEmail", W.document).text());
    });

    //提交表单处理
    function submitForm() {
        var currDocument = $(document); //当前文档
        //验证表单
        if ($("#txtAcceptName").val() == "") {
            top.dialog({
                title: '提示',
                content: '请填写收货人姓名！',
                okValue: '确定',
                ok: function () { },
                onclose: function () {
                    $("#txtAcceptName", currDocument).focus();
                }
            }).showModal(api);
            return false;
        }
        if ($("#txtArea").val() == "") {
            top.dialog({
                title: '提示',
                content: '请选择所在地区！',
                okValue: '确定',
                ok: function () { },
                onclose: function () {
                    $("#txtProvince", currDocument).focus();
                }
            }).showModal(api);
            return false;
        }
        if ($("#txtAddress").val() == "") {
            top.dialog({
                title: '提示',
                content: '请填写详细的送货地址！',
                okValue: '确定',
                ok: function () { },
                onclose: function () {
                    $("#txtAddress", currDocument).focus();
                }
            }).showModal(api);
            return false;
        }
        if ($("#txtMobile").val() == "" && $("#txtTelphone").val() == "") {
            top.dialog({
                title: '提示',
                content: '联系手机或电话至少填写一项！',
                okValue: '确定',
                ok: function () { },
                onclose: function () {
                    $("#txtMobile", currDocument).focus();
                }
            }).showModal(api);
            return false;
        }
        //下一步，AJAX提交表单
        var postData = {
            "order_no": $("#spanOrderNo", W.document).text(), "edit_type": "edit_accept_info",
            "accept_name": $("#txtAcceptName").val(), "province": $("#txtProvince").val(),
            "city": $("#txtCity").val(), "area": $("#txtArea").val(), "address": $("#txtAddress").val(),
            "post_code": $("#txtPostCode").val(), "mobile": $("#txtMobile").val(), "telphone": $("#txtTelphone").val(),
            "email": $("#txtEmail").val()
        };
        //发送AJAX请求
        W.sendAjaxUrl(api, postData, "../../tools/admin_ajax.ashx?action=edit_order_status");
        return false;
    }

</script>
</head>

<body class="mainbody">
<table width="" border="0" cellspacing="0" cellpadding="0" class="ltable" style="
    min-width: 400px;
    height: 169px;
    overflow-y: scroll;
    display: block;">
<div class="table-container">
    <tbody>
    <tr class="odd_bg">
      <th align="left" width=269px>标题</th>
      <th width="6%">选择</th>
    </tr>
    <#if category_list??>
      <#list category_list as item>
        <tr>
          <td style="white-space:nowrap;word-break:break-all;overflow:hidden;">
            <#if item.layerId??>
              <#if item.layerId gt 1>
                <span style="display:inline-block;width:${(item.layerId-2)*24}px;"></span>
                <span class="folder-line"></span>
              </#if>
            </#if>
            </span><span class="folder-open"></span>
            <a href="/management/navigation/edit?id=${item.id?c}">${item.title!''}</a>
          </td>
          <td align="center">
            <span class="checkall" style="vertical-align:middle;">
              <input id="listChkId" <#if item.layerId != 3>disabled="disabled"</#if> type="checkbox" name="listChkIdx" value="${item_index}" >
            </span>
            <input type="hidden" name="listId" id="listId" value="${item.id?c}">
          </td>
        </tr>
      </#list>
    </#if>
  </tbody>
</div>
</table>
</body>
</html>