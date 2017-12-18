<#if doctor_list??>
	<#list doctor_list as doc>
	<a href="/wx/doctor/${doc.id?c}">
	<div class="h20"></div>
	<div class="doctor_list1">
        <div class="doctor_list1_l">
            <img src="${doc.headImgUrl!''}">
            <#if doc.isOnline>
            <label class="on_line">在线</label>
            <#else>
            <label class="off_line">离线</label>
            </#if>
        </div>

        <div class="doctor_list1_r">
            <dl>
                <dt>${doc.name!''}<span></span></dt>
                <dd><span>${doc.hospital!''}</span><span></span></dd>
                <dd>擅长：<i>${doc.speciality!''}</i></dd>
                <dd>
                    <div class="doctor_list1_r_num">服务人数<span>${doc.servedNo!'0'}</span></div>
                    <div class="doctor_list1_r_numnow">当前咨询人数<span>${doc.serveNo!'0'}</span></div>
                </dd>
            </dl>

        </div>
    </div>
    </a>
	</#list>
	</#if>