<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Auditoriums Service</title>
<#include "part/common.ftl">
</head>
<body>
<#include "part/header.ftl">

<div class="container">
    <div class="page-header">
        <h1>Auditoriums</h1>
    </div>
<#if auditorium??>
    <p><b>Id: </b> ${auditorium.id}</p>
    <p><b>Name: </b> ${auditorium.name}</p>
    <p><b>Size (seats): </b> ${auditorium.numberOfSeats}</p>
    <p><b>VIP seats: </b>
        <#list auditorium.vipSeats as vipSeat>
            <span>${vipSeat}</span>
        </#list>
    </p>
    <a href="auditorium.ftl" title="Back to auditoriums list" class="btn btn-default">Back to auditoriums list</a>
<#elseif auditoriums??>
    <form class="form-horizontal" method="GET" action="/auditorium" enctype="multipart/form-data">
        <div class="form-group">
            <label for="file" class="col-sm-2">Find by name: </label>
            <div class="col-sm-3">
                <input type="text" class="form-control" name="name"/><br/>
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
            <th>Name</th>
            <th>Size (seats)</th>
            <th>VIP seats</th>
        </tr>
        <#list auditoriums as auditorium>
            <tr>
                <td scope="row"><a href="?id=${auditorium.id}">${auditorium.id}</a></td>
                <td><a href="?name=${auditorium.name}">${auditorium.name}</a></td>
                <td>${auditorium.numberOfSeats}</td>
                <td>
                    <#list auditorium.vipSeats as vipSeat>
                        <span>${vipSeat}</span>
                    </#list>
                </td>
            </tr>
        </#list>
    </table>
<#else>
    <p>No results found</p>
    <a href="auditorium.ftl" title="Back to auditoriums list" class="btn btn-default">Back to auditoriums list</a>
</#if>
</div>

<#include "part/footer.ftl">
</body>
</html>