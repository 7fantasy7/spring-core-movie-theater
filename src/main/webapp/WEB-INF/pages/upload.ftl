<!DOCTYPE html>
<html>
<head>
    <title>Batch File Upload</title>
<#include "part/common.ftl">
</head>
<body>
<#include "part/header.ftl">
<div class="container">
    <div class="page-header">
        <h1>Batch file upload</h1>
    </div>
<#if message??>
    <div class="alert alert-success">${message}</div>
</#if>
    <h2>Simple file load</h2>
    <form class="form-horizontal" method="POST" action="/upload/file" enctype="multipart/form-data">
        <div class="form-group">
            <label for="file" class="col-sm-2">Choose file: </label>
            <div class="col-sm-4">
                <input class="form-control btn btn-default" type="file" name="file"/><br/>
                <input class="btn btn-default" type="submit" value="Upload"/>
            </div>
        </div>
    </form>
    <br>
    <h2>Import users</h2>
    <form class="form-horizontal" method="POST" action="/upload/users" enctype="multipart/form-data">
        <div class="form-group">
            <label for="file" class="col-sm-2">Choose file: </label>
            <div class="col-sm-4">
                <input class="form-control btn btn-default" type="file" name="file"/><br/>
                <input class="btn btn-default" type="submit" value="Upload"/>
            </div>
        </div>
    </form>
    <br>
    <h2>Import events</h2>
    <form class="form-horizontal" method="POST" action="/upload/events" enctype="multipart/form-data">
        <div class="form-group">
            <label for="file" class="col-sm-2">Choose file: </label>
            <div class="col-sm-4">
                <input class="form-control btn btn-default" type="file" name="file"/><br/>
                <input class="btn btn-default" type="submit" value="Upload"/>
            </div>
        </div>
    </form>
</div>

<#include "part/footer.ftl">
</body>
</html>