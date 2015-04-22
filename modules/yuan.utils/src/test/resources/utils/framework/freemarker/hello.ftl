<html>
<head>
  <title>Welcome!</title>
</head>
<body>
  <h1>
    Welcome ${username}<#if username == "Big Joe">, our beloved leader</#if>!
  </h1>
  <p>Our latest product:
  <a href="${latestProductUrl}">${latestProductName}</a>!
</body>
</html>  