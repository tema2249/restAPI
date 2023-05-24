<#import "blocks/template.ftlh" as t>
<@t.template user>
<h4>Редактирование роли ${currentUser.login}</h4>
<form action="/admin/user/edit/" method="post">
    <#list roles as role>
        <div>
            <label><input type="checkbox" name="${role}" ${currentUser.roles?seq_contains(role)?string("checked", "")}>${role}</label>
        </div>

    </#list>
    <input type="hidden" value="${currentUser.id}" name="userId">
    <input type="hidden" value="${_csrf.token}" name="_csrf">
    <button type="submit">Сохранить</button>

</form>
</body>
</@t.template>