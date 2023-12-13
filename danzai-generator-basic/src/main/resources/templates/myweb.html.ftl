<!DOCTYPE html>
<html>
<head>
    <title>welocme</title>
</head>
<body>
<h1>welocme</h1>
<ul>
    <#list menuItems as item>
        <li><a href="${item.url}">${item.label}</a></li>
    </#list>
</ul>
<footer>
    ${currentYear} 你好啊！
</footer>
</body>
</html>