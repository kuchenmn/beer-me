<html>
<head>
    <title>Search Results</title>
</head>
<body>
    <#if cities??>
    <p>
        <form action="/search" method="POST">
            Location:
            <select name="city">
                <#list cities as city>
                    <#if citySelected == city>
                        <option value="${city}" selected>${city}</option>
                    <#else>
                        <option value="${city}">${city}</option>
                    </#if>
                </#list>
            </select>
            <p>
            <#list locationTypes as type>
                <input type="checkbox" name="${type}" value="${type}">${type}<br>
            </#list>
            <input type="submit" value="Search">
        </form>
    </p>
    </#if>
    <#if locations??>
    <p>
        Locations
        <p>
        <#list locations as location>
            ${location.id} - ${location.status} - ${location.name}<p>
        </#list>
    </p>
    </#if>
</body>
</html>