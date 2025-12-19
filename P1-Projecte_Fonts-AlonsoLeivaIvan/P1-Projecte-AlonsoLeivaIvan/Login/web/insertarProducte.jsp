<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, p1.t4.model.Item" %>
<%
    List<Item> items = (List<Item>) request.getAttribute("items");
%>

<html>
<head>
    <title>Insertar Producte</title>
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
        }

        button:hover {
            background-color: #F3A47B;
        }
    </style>
</head>
<body>

<h2>Insertar Producte</h2>

<form method="post" action="insertarProducte">
    <label>Item:</label>
    <select name="it_codi" required>
        <% if (items != null) {
               for (Item it : items) { %>
                   <option value="<%= it.getCodii() %>"><%= it.getNom() %></option>
        <%     }
           } %>
    </select>

    <button type="submit">Guardar Producte</button>
</form>

</body>
</html>
