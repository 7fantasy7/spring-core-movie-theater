<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Users Service</title>
<#include "part/common.ftl">
</head>
<body>
<#include "part/header.ftl">

<div class="container">
    <div class="page-header">
        <h1>Users</h1>
    </div>
<#if user??>
    <p><b>Id: </b> ${user.id}</p>
    <p><b>First name: </b> ${user.firstName}</p>
    <p><b>Last name: </b> ${user.lastName}</p>
    <p><b>E-mail: </b> ${user.email}</p>
    <p><b>Birth day: </b> <#if user.birthDay??> ${user.birthDay} <#else> - </#if></p>
    <p><b>Tickets count: </b> <#if user.ticketsCount??> ${user.ticketsCount} <#else> 0 </#if></p>
    <a href="user.ftl" title="Back to users list" class="btn btn-default">Back to users list</a>
<#elseif users??>
    <form class="form-horizontal" method="GET" action="/user" enctype="multipart/form-data">
        <div class="form-group">
            <label for="file" class="col-sm-2">Find by email: </label>
            <div class="col-sm-3">
                <input type="text" class="form-control" name="email"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <input type="submit" value="Find" class="btn btn-default"/>
            </div>
        </div>
    </form>
    <br>
    <table class="table">
        <tr>
            <th>Id</th>
            <th>First name</th>
            <th>Last name</th>
            <th>E-mail</th>
            <th>BirthDay</th>
            <th>Tickets count</th>
        </tr>
        <#list users as user>
            <tr>
                <td scope="row"><a href="?id=${user.id}">${user.id}</a></td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.email}</td>
                <td><#if user.birthDay??> ${user.birthDay} <#else> - </#if></td>
                <td><#if user.ticketsCount??> ${user.ticketsCount} <#else> 0 </#if></td>
            </tr>
        </#list>
    </table>
<#else>
    <p>No results found</p>
    <a href="user.ftl" title="Back to users list" class="btn btn-default">Back to users list</a>
</#if>
</div>

<#include "part/footer.ftl">
</body>
</html>