<#import "blocks/template.ftlh" as t>
<@t.template user>
<h1>Оформление заказа</h1>
    <p><b>Ваше имя: ${user.name}</b></p>
    <p><b>Ваш телефон: ${user.phoneNumber}</b></p>
    <p><b>Ваша почта: ${user.email}</b></p>
    <p><b>Адрес заказа:</b>
    <form action="/order/create/" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
    <div class="form-group">
    <select class="form-select" id="exampleSelect1" name="fullAddress">
        <#list addresses as address>
            <option value="${address.fullAddress}">${address.fullAddress}</option>
        </#list>
    </select>
    </div>
    <input type="submit" value="оформить"/>
    </form>
    </p>
</@t.template>