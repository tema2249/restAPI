<#import "blocks/template.ftlh" as t>
<@t.template user>
<h1>Заказ N${order.id} от ${order.dateOfCreated.toLocalDate()}</h1>
    <p>Покупатель: ${order.name} <br>  ${order.fullAddress} <br> Статус заказа: ${order.status.getName()}</p>
<a href="/user/${order.user.id}">Профиль пользователя</a>
    <table class="table table-hover">
        <tr>
            <td>Название</td>
            <td>Количество</td>
            <td>Цена за 1 шт.</td>
            <td>Цена</td>
        </tr>
    <#list order.products as product>
        <tr>
        <th>${product.title}</th>
        <th>${product.quantity}</th>
        <th>${product.price} </th>
        <th>${product.price * product.quantity} руб.</th>
        </tr>
    </#list>
        <tr>
            <th></th>
            <th></th>
            <th>Итого:</th>
            <th>${order.price} руб.</th>
        </tr>
    </table>

    <#if user.isManeger()>
        <div class="container-fluid">
    <form action="/order/accept" method="post">
        <input type="hidden" name="id" value="${order.id}">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input class="btn btn-info" type="submit" value="Принять заказ">
    </form>
        <form action="/order/sent" method="post">
            <input type="hidden" name="id" value="${order.id}">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input class="btn btn-success" type="submit" value="Отправить заказ">
    </form>
        <form action="/order/cancelled" method="post">
            <input type="hidden" name="id" value="${order.id}">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input class="btn btn-danger" type="submit" value="Аннулировать заказ">
        </form>
        </div>
    </#if>


</@t.template>