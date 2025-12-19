<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, p1.t4.model.Proveidor" %>

<%
    Integer cmCodi = (Integer) request.getAttribute("cm_codi");
    List<Proveidor> proveidors = (List<Proveidor>) request.getAttribute("proveidors");
    String error = (String) request.getAttribute("error");
%>

<html>
<head>
    <title>Asignar precios a proveedores</title>
    <link rel="stylesheet" href="estils.css">
    <style>
        body {
            font-family: Verdana, Geneva, Tahoma, sans-serif;
            color: #000000;
            background-color: white;
            margin: 40px;
        }

        h2 {
            color: #F3A47B;
            border-bottom: 2px solid #000000;
            padding-bottom: 4px;
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        table, th, td {
            border: 1px solid #000000;
        }

        th, td {
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #F3A47B;
            color: #000000;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        input[type="number"] {
            width: 100%;
            padding: 6px;
            border: 1px solid #CCC;
            border-radius: 4px;
            box-sizing: border-box;
        }

        button {
            background-color: #FF6A45;
            color: white;
            border: none;
            padding: 8px 12px;
            font-weight: 600;
            cursor: pointer;
            border-radius: 4px;
            margin-top: 10px;
        }

        button:hover {
            background-color: #F3A47B;
        }

        a {
            color: #4400FF;
            text-decoration: none;
            font-weight: 600;
        }

        a:hover {
            text-decoration: underline;
        }

        p.error {
            color: red;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

<h2>Asignar Precios al Componente <%= cmCodi %></h2>

<c:if test="${not empty error}">
    <p class="error"><%= error %></p>
</c:if>

<form method="post" action="asignarPrecios">
    <input type="hidden" name="cm_codi" value="<%= cmCodi %>">

    <table>
        <tr>
            <th>Proveedor</th>
            <th>Precio</th>
        </tr>
        <% for(Proveidor p : proveidors){ %>
        <tr>
            <td><%= p.getCodi() %> - <%= p.getRaoSocial() %></td>
            <td>
                <input type="number" name="precio_<%= p.getCodi() %>" min="1" required>
            </td>
        </tr>
        <% } %>
    </table>

    <button type="submit">Guardar Precios</button>
</form>

<br>
<a href="components">⬅ Volver a Components</a>

</body>
</html>
