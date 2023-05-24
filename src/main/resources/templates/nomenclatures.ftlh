<#import "blocks/template.ftlh" as t>
<@t.template user>
<h1>Список товаров</h1>
<form action="/nomenclatures" method="get">
    Поиск <input type="text" name="title"><br>
    <input type="submit" value="Поиск"/>
</form>
<#list nomenclatures as nomenclature>
    <div>
        <p><b>${nomenclature.title}</b> ${nomenclature.price} руб | <a href="/nomenclature/${nomenclature.id}">Подробнее</a>
        <#if user.login?? && user.isManeger() == false>
            <form action="/cart/add/${nomenclature.id}" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <input type="number" name="quantity" value="1"><br>
                <input type="submit" value="Добавить в корзину">
            </form>
        </#if>
        </p>
    </div>
    <#else>
    <h3>Товаров нет</h3>
</#list>
<hr>
<#if user.isManeger()>
<h3>Создать новый товар</h3>
<form action="/nomenclature/create" method="post" enctype="multipart/form-data">
    Название: <input type="text" name="title"/><br><br>
    Цена: <input type="number" name="price"/><br><br>
    Фото 1: <input type="file" name="file1"><br>
    Фото 2:<input type="file" name="file2"><br>
    Фото 3:<input type="file" name="file3"><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Добавить товар">
</form>
</#if>
</@t.template>