<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>Cinema manager - Auditoriums Service</title>
<#include "../part/common.ftl">
</head>
<body>
<#include "../part/header.ftl">

<div class="content">
<#if auditorium??>
    <h2>Found auditorium</h2>
    <p><b>Id: </b> ${auditorium.id}</p>
    <p><b>Name: </b> ${auditorium.name}</p>
    <p><b>Size (seats): </b> ${auditorium.numberOfSeats}</p>
    <p><b>VIP seats: </b>
        <#list auditorium.vipSeats as vipSeat>
            <span>${vipSeat}</span>
        </#list>
    </p>
</#if>

<#if auditoriums??>
    <h2>All auditoriums</h2>
    <table class="beauty">
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Size (seats)</th>
            <th>VIP seats</th>
        </tr>
        <#list auditoriums as auditorium>
            <tr>
                <td><a href="?id=${auditorium.id}">${auditorium.id}</a></td>
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
</#if>
</div>

<#include "../part/footer.ftl">
</body>
</html>