<html>
<head>
    <title>Search Results</title>
</head>
<body>
    <p> City Form
        <#--<form action="/search" method="POST">-->
            <#--City:-->
            <#--<select name="city">-->
                <#--<#list cities as city>-->
                    <#--<option value="${city}">${city}</option>-->
                <#--</#list>-->
            <#--</select>-->
            <#--<input type="submit" value="Search">-->
        <#--</form>-->
    </p>
    <p>
        Locations
        <#list locations as location>
            ${location.id} - ${location.status} - ${location.name}<p>
        </#list>
    </p>
</body>
</html>