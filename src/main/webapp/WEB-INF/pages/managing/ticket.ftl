<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Users Service</title>
    <script>
        var downloadTickets = function () {
            var iframe = document.createElement('iframe');
            iframe.style.display = 'none';
            document.getElementById('file').appendChild(iframe);
            iframe.src = '/pdf/userTickets?userId=1';
            iframe.addEventListener("load", function () {
                console.log("FILE LOAD DONE.. Download should start now");
            });
        };
    </script>
<#include "../part/common.ftl">
</head>
<body>
<#include "../part/header.ftl">
<div class="container">
    <div class="page-header">
        <h1>PDF Tickets Download</h1>
    </div>
    <a href="#" onclick="downloadTickets()" class="btn btn-default">Download</a>
    <div id="file"></div>
</div>
<#include "../part/footer.ftl">
</body>
</html>