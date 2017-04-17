<nav class="navbar navbar-default navbar-static-top">
    <div class="container">
        <form action="/logout" method="post" id="logoutForm">
            <input type="hidden"
                   name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>
        </form>
        <div class="navbar-header">
            <a class="navbar-brand" href="/">Spring MVC Theater</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="/">Main</a></li>
                <li><a href="/user">Users</a></li>
                <li><a href="/auditorium">Auditoriums</a></li>
                <li><a href="/event">Events</a></li>
                <li><a href="/ticket">Tickets (PDF)</a></li>
                <li><a href="/upload">Batch Loading</a></li>
                <li><a href="#" onclick="document.getElementById('logoutForm').submit();">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>