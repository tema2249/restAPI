<#import "blocks/template.ftlh" as t>
<@t.template user>
<h1>Страница товара</h1>
<b>Название:</b> ${nomenclature.title}<br>
<#list images as img>
    <img src="/images/${img.id}" height="80px">
</#list><br>
<b>Цена:</b>${nomenclature.price}<br>
<#if user.isManeger()>
<form action="/nomenclature/delete/${nomenclature.id}" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Удалить товар"/>
</form>
</#if>
</@t.template>