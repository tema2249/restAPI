<#import "blocks/template.ftlh" as t>
<@t.template user>
<h4>Регистрация</h4>
<form action="/registration" method="post">
    Логин: <input type="text" name="login"><br><br>
    Эл. почта: <input type="email" name="email"><br><br>
    Человеческое имя: <input type="text" name="name"><br><br>
    Номер телефона: <input type="text" name="phoneNumber"><br><br>
    Пароль: <input type="password" name="password"><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Зарегистрироваться"/>
</form>
</@t.template>