<html>
<head>
    <title>Search</title>
</head>
<body>
    <form action="/search" method="POST">
        City:
        <select name="city">
            <#list cities as city>
                <option value="${city}">${city}</option>
            </#list>
        </select>
        <input type="submit" value="Search">
    </form>
</body>
</html>