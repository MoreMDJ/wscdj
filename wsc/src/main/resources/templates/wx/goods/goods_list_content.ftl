<#if goods??>
	<#list goods.content as goods>
	<a href="/wx/goods/${goods.id?c}" title="">
		<dl>
			<dt>
				<img src="${goods.imgUrl!''}">
			</dt>
			<dd class="goods_list_box_ddtitle">${goods.title!''}</dd>
			<dd>
				<span class="goods_list_box_activity colpurple">满减</span>
				<span class="goods_list_box_activity colpurple">秒杀</span>
			</dd>
			<dd>
				<b class="colred fs26">${goods.salePrice?string('currency') }</b> <#if goods.marketPrice??><label class="col99918">${goods.marketPrice?string('currency')}</label></#if>
			</dd>
		</dl>
	</a>
</#list>
</#if>