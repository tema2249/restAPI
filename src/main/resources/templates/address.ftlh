<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h1>Ваши адреса</h1>
    <table class="table table-hover">
    <tr>
        <th>Адрес</th>
        <th>Удалить</th>
        <th>Сделать основным</th>
    </tr>
    <#list addresses as address>
    <tr>
        <th><#if address.main == true><b></#if>${address.fullAddress}<#if address.main == true></b> (основной)</#if></th>
        <th><#if (address.pickUpPoint!=true)>
            <form action="/address/delete" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <input type="hidden" value="${address.id}" name="idAddress">
                <input type="submit" value="удалить">
            </form>
            </#if>
        </th>
        <th>
            <#if (address.main == false) && (address.pickUpPoint!=true)>
            <form action="/address/setMain" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <input type="hidden" value="${address.id}" name="idAddress">
                <input type="submit" value="сделать основным">
            </form>
            </#if>
        </th>
    </tr>
    <#else>
        <h3>Вы ещё не создавали адресов!!!</h3>
    </#list>
    <h3>Новый адрес</h3>
    <form action="/address/create" method="post">
        Полный адрес: <input type="text" name="fullAddress" size="100" /><br><br>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Добавить">
    </form>
</@t.template>