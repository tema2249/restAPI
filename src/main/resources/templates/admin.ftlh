<#import "blocks/template.ftlh" as t>
<@t.template user>
<h1 align="center">Панель администратора</h1>
<table class="table table-hover">
    <tr>
    <th>Логин</th>
    <th>Email</th>
    <th>Номер телефона</th>
    <th>Активность</th>
    <th>Роль</th>
    <th>Бан</th>
    <th>Смена роли</th>
    <th>Смена пароля</th>
    <th>Подробная информация</th>
    </tr>
<#list users as userList>
    <tr>
        <th>${userList.login}</th>
        <th>${userList.email}</th>
        <th>${userList.phoneNumber}</th>
        <th><#if userList.active>true<#else>false</#if></th>
        <th><#list userList.roles as role>${role}</#list></th>
        <th>
            <form action="/admin/user/ban/${userList.id}" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <input type="submit" value="Бан">
            </form>
        </th>
        <th><a href="/admin/user/edit/${userList.id}">Изменить роль</a> </th>
        <th><a href="/admin/user/edit/password/${userList.id}">Изменить пароль</a> </th>
        <th>
            <a href="/user/${userList.id}">Профиль</a>
        </th>
    </tr>
<#else>
    <h3>Пользователей нет</h3>
</#list>
</table>
</@t.template>