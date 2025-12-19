<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ca">
<head>
    <meta charset="UTF-8">
    <title>Login - Aplicació T5</title>
    <link rel="stylesheet" href="estils.css">
    <style>
        body.login-body {
            font-family: Verdana, Geneva, Tahoma, sans-serif;
            color: #000000;
            background-color: white;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .login-container {
            border: 1px solid #000000;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0px 4px 10px rgba(0,0,0,0.1);
            width: 300px;
            background-color: #f9f9f9;
        }

        h2 {
            color: #F3A47B;
            border-bottom: 2px solid #000000;
            padding-bottom: 4px;
            margin-bottom: 20px;
            text-align: center;
        }

        .form-group {
            margin-bottom: 15px;
        }

        label {
            display: block;
            margin-bottom: 5px;
        }

        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #CCC;
            border-radius: 4px;
            box-sizing: border-box;
        }

        button {
            width: 100%;
            background-color: #FF6A45;
            color: white;
            border: none;
            padding: 10px;
            font-weight: 600;
            cursor: pointer;
            border-radius: 4px;
        }

        button:hover {
            background-color: #F3A47B;
        }

        p {
            margin: 5px 0 10px 0;
        }

        p.error {
            color: red;
            text-align: center;
        }
    </style>
</head>
<body class="login-body">

<div class="login-container">
    <h2>Autenticació</h2>

    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group">
            <label for="username">Usuari:</label>
            <input type="text" id="username" name="username" required autofocus value="admin">
        </div>

        <div class="form-group">
            <label for="password">Contrasenya:</label>
            <input type="password" id="password" name="password" required value="admin">
        </div>

        <button type="submit">Accedeix</button>
    </form>
</div>

</body>
</html>
