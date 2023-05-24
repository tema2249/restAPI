<#import "blocks/template.ftlh" as t>
<@t.template user>
<h4>Профиль пользователя</h4>
<table>
    <tr>
        <td>Логин:</td>
        <td>${currentUser.login}</td>
    </tr>
    <tr>
        <td>Email:</td>
        <td>${currentUser.email}</td>
    </tr>
    <tr>
        <td>Имя пользователя:</td>
        <td>${currentUser.name}</td>
    </tr>
    <tr>
        <td>Роль:</td>
        <td>${currentUser.roles[0]}</td>
    </tr>
    <tr>
        <td>Номер телефона:</td>
        <td>${currentUser.phoneNumber}</td>
    </tr>
</table>
</@t.template>