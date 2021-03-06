<%@ taglib prefix="ww" uri="/webwork" %>
<html>
    <head><title>Execute and Wait Examples</title></head>

<body>
    <h1>Execute and Wait Example 3</h1>

    <b>Example 3:</b> As example 1 but uses a delay of 3000 millis before the wait page is shown.
    While waiting for the wait page it will check every 1000 millis if the background process is already
    done. Try simulating with a value of 700 millis to see that the wait page is shown soon thereafter.

    <ww:form action="longProcess3">
        <ww:textfield label="Time (millis)" name="time" required="true" value="9000"/>
        <ww:submit value="submit"/>
    </ww:form>

</body>
</html>
