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
        <h1>Events</h1>
    </div>
<#if event??>
    <p><b>Id: </b> ${event.id}</p>
    <p><b>Name: </b> ${event.name}</p>
    <#assign airDates = event.airDates />
    <p><b>Air Dates: </b> ${airDates?join(", ")}</p>
    <p><b>Base Price: </b> ${event.basePrice}</p>
    <p><b>Rating: </b> <#if event.rating??> ${event.rating} <#else> - </#if></p>
    <a href="event.ftl" title="Back to event list" class="btn btn-default">Back to event list</a>
<#elseif events??>
    <form class="form-horizontal" method="GET" action="/event" enctype="multipart/form-data">
        <div class="form-group">
            <label for="file" class="col-sm-2">Find by name: </label>
            <div class="col-sm-3">
                <input type="text" class="form-control" name="name"/>
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
            <th>Air Dates</th>
            <th>Base Price</th>
            <th>Rating</th>
        </tr>
        <#list events as event>
            <tr>
                <#assign airDates = event.airDates />
                <td scope="row"><a href="?id=${event.id}">${event.id}</a></td>
                <td>${event.name}</td>
                <td> ${airDates?join(", ")}</td>
                <td>${event.basePrice}</td>
                <td><#if event.rating??> ${event.rating} <#else> - </#if></td>
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