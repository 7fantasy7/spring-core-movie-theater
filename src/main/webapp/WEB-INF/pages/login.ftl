<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Login</title>
<#include "part/common.ftl">
</head>
<body>
<div class="container">
    <div class="page-header">
        <h1>Login</h1>
    </div>
    <div>
        <form class="form-horizontal" action="/j_spring_security_check" method="post">
            <div class="form-group">
                <label for="username">E-mail</label>
                <input type="text" class="form-control" id="username" name="j_username">
                <label for="password">Password</label><input type="password" class="form-control" id="password"
                                                             name="j_password"><br/>
                <label for="remember-me">Remember me</label><input type="checkbox" name="remember-me"><br/>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input class="form-control btn btn-default" type="submit" value="Login!">
            </div>
        </form>
    <#if RequestParameters.message??>
        <div class="alert alert-danger">
        ${RequestParameters.message}
        </div>
    </#if>
    </div>
</div>

<#include "part/footer.ftl">
</body>
</html>