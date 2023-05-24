<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h1>Корзина</h1>
    <#list carts as cart>
        <div>
            <p>
                <b>${cart.nomenclature.title}</b> ${cart.nomenclature.price} руб x ${cart.quantity} шт. = ${cart.nomenclature.price * cart.quantity} руб. </p>
            <form action="/cart/delete/" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <input type="hidden" name="idCart" value="${cart.id}">
                <input type="submit" value="Удалить из корзины"/>
            </form>
        </div>
    <#else>
        <h3>Товаров в корзине нет</h3>
    </#list>
    <p><b>Итого: ${price} руб.</b></p>
    <#if (price == "0,00")>
    <#else>
        <button onclick="window.location.href = '/order/create'">Оформить заказ</button>
    </#if>

</@t.template>