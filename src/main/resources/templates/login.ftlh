<#import "blocks/template.ftlh" as t>
<@t.template user>
<#if user.login??>
<form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input class="btn btn-light my-2 my-sm-0" type="submit" value="Выйти"/>
</form>
<#else>
<h4>Авторизация</h4>
<form action="/login" method="post">
    Логин: <input type="text" name="username"><br><br>
    Пароль: <input type="password" name="password"><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Вход"/>
</form>
</#if>
<a href="/registration">Зарегистрироваться</a>
</@t.template>
