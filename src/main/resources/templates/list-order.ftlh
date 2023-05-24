<#import "blocks/template.ftlh" as t>
<@t.template user>
<h1>Список заказов</h1>
    <#list orders as order>
        <div class="list-group">
            <h5 class="mb-1"><b>Заказ №${order.id} от ${order.dateOfCreated.toLocalDate()} </b></h5>
            <h5>Статус заказа:${order.status.getName()}</h5>
            <p>(
        <#list order.products as product>
            ${product.title},
        </#list>
            )</p>
            <a href="/order/${order.id}">Подробнее</a>
            <small>Итого: ${order.price} руб.</small>
        </div>
    <#else>
        <h3>Заказов нет</h3>
    </#list>
</@t.template>
