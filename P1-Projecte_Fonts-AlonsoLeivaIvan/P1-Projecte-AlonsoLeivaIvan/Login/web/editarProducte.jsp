<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, p1.t4.model.Producte, p1.t4.model.Item" %>

<%
    Producte p = (Producte) request.getAttribute("producte");
    List<Item> items = (List<Item>) request.getAttribute("items");
    String contextPath = request.getContextPath();
%>

<html>
<head>
    <title>Editar Producte</title>
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

        form {
            max-width: 400px;
        }

        label {
            display: block;
            margin-bottom: 8px;
        }

        select {
            width: 100%;
            padding: 8px;
            margin-bottom: 20px;
            border: 1px solid #CCC;
            border-radius: 4px;
            box-sizing: border-box;
        }

        button {
            background-color: #FF6A45;
            color: white;
            border: none;
            padding: 10px 15px;
            font-weight: 600;
            cursor: pointer;
            border-radius: 4px;
            margin-right: 10px;
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
    </style>
</head>
<body>

<h2>Editar Producte</h2>

<form method="post" action="<%= contextPath %>/editarProducte">
    <input type="hidden" name="pr_codi" value="<%= p.getCodip() %>">

    <label>Item:</label>
    <select name="it_codi" required>
        <% for (Item it : items) {
               boolean selected = p.getM_Item() != null && p.getM_Item().getCodii() == it.getCodii();
        %>
            <option value="<%= it.getCodii() %>" <%= selected ? "selected" : "" %>>
                <%= it.getNom() %>
            </option>
        <% } %>
    </select>

    <button type="submit">Actualizar Producte</button>
    <a href="<%= contextPath %>/gestioProductes">Cancelar</a>
</form>

</body>
</html>
