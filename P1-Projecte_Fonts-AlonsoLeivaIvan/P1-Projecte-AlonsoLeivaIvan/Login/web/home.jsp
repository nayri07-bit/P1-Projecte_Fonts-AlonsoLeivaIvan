<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" href="estils.css">
    <style>
        body {
            font-family: Verdana, Geneva, Tahoma, sans-serif;
            color: #000000;
            background-color: white;
            margin: 0;
        }

        h2 {
            color: #F3A47B;
            border-bottom: 2px solid #000000;
            padding-bottom: 4px;
            margin-bottom: 20px;
        }

        form {
            margin-bottom: 15px;
        }

        button {
            background-color: #FF6A45;
            color: white;
            border: none;
            padding: 10px 15px;
            font-weight: 600;
            cursor: pointer;
            border-radius: 4px;
        }

        button:hover {
            background-color: #F3A47B;
        }


        .menu-lateral {
            position: fixed;
            top: 0;
            left: 0;
            width: 200px;
            height: 100%;
            background-color: #FF6A45;
            color: white;
            overflow-y: auto;
            border-radius: 0 8px 8px 0;
            z-index: 1000;
        }

        .menu-lateral button.toggle-btn {
            width: 100%;
            background-color: #FF6A45;
            border: none;
            color: white;
            padding: 12px;
            font-weight: 600;
            cursor: pointer;
            text-align: left;
        }

        .menu-lateral button.toggle-btn:hover {
            background-color: #F3A47B;
        }

        .menu-lateral ul {
            list-style: none;
            padding: 0;
            margin: 0;
            display: none;
        }

        .menu-lateral ul li a {
            display: block;
            color: white;
            text-decoration: none;
            padding: 10px 15px;
        }

        .menu-lateral ul li a:hover {
            background-color: #F3A47B;
        }

        .menu-lateral.active ul {
            display: block;
        }

        
        .contenido-principal {
            margin-left: 220px;
            padding: 20px;
        }

    </style>
</head>
<body>

<div class="menu-lateral" id="menuLateral">
    <button class="toggle-btn" onclick="toggleMenu()">Menú ▼</button>
    <ul>
        <li><a href="home.jsp">Home</a></li>
        <li><a href="gestioItems">Gestió de Productes</a></li>
        <li><a href="components">Gestió de Components</a></li>
    </ul>
</div>

<div class="contenido-principal">
    <h2>Benvingut, ${username}</h2>

    <form action="<%= request.getContextPath() %>/gestioItems" method="get">
        <button type="submit">Gestió de productes</button>
    </form>

    <form action="components" method="get">
        <button type="submit">Gestió de components</button>
    </form>

    <form method="post">
        <button type="submit" name="logout" value="1">Tancar sessió</button>
    </form>

    <%
        if ("1".equals(request.getParameter("logout"))) {
            session.invalidate();
            response.sendRedirect("login.jsp");
        }
    %>
</div>

<script>
    function toggleMenu() {
        document.getElementById('menuLateral').classList.toggle('active');
    }
</script>

</body>
</html>
