<@ww.form action="processFreeMarkerExample" method="POST">
    <@ww.textfield label="First name" name="firstName"/>
    <@ww.textfield label="Last name" name="lastName"/>
    <@ww.submit value="Say hello!"/>
</@ww.form>

<a href="<@ww.url action="index"/>">Back to front page</a>
