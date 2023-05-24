<#import "blocks/template.ftlh" as t>
<@t.template user>
<h4>Смена пароля ${currentUser.login}</h4>
<form action="/admin/user/edit/password" method="post">
    <input type="text" name="password">
    <input type="hidden" value="${currentUser.id}" name="userId">
    <input type="hidden" value="${_csrf.token}" name="_csrf">
    <button type="submit">Сохранить</button>

</form>
</body>
</@t.template>