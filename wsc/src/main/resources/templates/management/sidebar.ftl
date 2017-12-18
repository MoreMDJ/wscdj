<#if root_menu_list?? && root_menu_list?size gt 0>
<#list root_menu_list as item>
<div class="list-group" style="display: <#if item_index==0>block<#else>none</#if>;">
  <h1 title="${item.title!''}"><img src="${item.iconUri!''}" /></h1>
  <div class="list-wrap">
    <h2>
      ${item.title!''}<i></i>
    </h2>
    <#if (("root_"+item_index+"_menu_list")?eval)??>
    <ul>
      <#list ("root_"+item_index+"_menu_list")?eval as childItem>
      <li>
        <a navid="${childItem.id!''}" href="${childItem.linkUri!''}" target="mainframe"><span>${childItem.title!''}</span></a>
        <#if (("root_"+item_index + "_" + childItem_index +"_menu_list")?eval)??>
        <ul>
          <#list ("root_"+item_index + "_" + childItem_index +"_menu_list")?eval as grandChildItem>
          <li>
            <a navid="${grandChildItem.id!''}" href="${grandChildItem.linkUri!''}" target="mainframe"> <span>${grandChildItem.title!''}</span></a>
          </li>
          </#list>
        </ul>
        </#if>
      </li>
      </#list>
    </ul>
    </#if>
  </div>
</div>
</#list>
</#if>