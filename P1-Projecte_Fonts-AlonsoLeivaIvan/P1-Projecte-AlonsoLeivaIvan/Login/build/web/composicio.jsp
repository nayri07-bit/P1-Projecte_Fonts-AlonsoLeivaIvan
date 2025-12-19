<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, p1.t4.model.Item, p1.t4.model.Composicio" %>

<%
    Integer itPare = (Integer) request.getAttribute("itPare");
    List<Composicio> composicio = (List<Composicio>) request.getAttribute("composicio");
    List<Item> itemsDisponibles = (List<Item>) request.getAttribute("itemsDisponibles");
    String error = (String) request.getAttribute("error");
%>

<html>
<head>
    <title>Gestión de Composición</title>
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

        input[type="number"], select {
            padding: 6px;
            margin-right: 10px;
            border: 1px solid #CCC;
            border-radius: 4px;
        }

        button {
            background-color: #FF6A45;
            color: white;
            border: none;
            padding: 8px 12px;
            font-weight: 600;
            cursor: pointer;
            border-radius: 4px;
            margin-top: 5px;
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

        label {
            margin-right: 5px;
        }

        form {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<h2>Composición del Item: <%= itPare %></h2>

<c:if test="${not empty error}">
    <p class="error"><%= error %></p>
</c:if>

<table>
    <tr>
        <th>Item hijo</th>
        <th>Cantidad</th>
        <th>Acciones</th>
    </tr>
    <% if(composicio != null && !composicio.isEmpty()) {
        for(Composicio c : composicio) { %>
        <tr>
            <td><%= c.getNom() %></td>
            <td><%= c.getQuantitat() %></td>
            <td>
                <form method="post" action="borrarComposicio">
                    <input type="hidden" name="it_pare" value="<%= c.getItPare() %>">
                    <input type="hidden" name="it_fill" value="<%= c.getItFill() %>">
                    <button type="submit" onclick="return confirm('¿Está seguro que desea borrar este componente?')">Eliminar</button>
                </form>
            </td>
        </tr>
    <%   }
       } else { %>
        <tr>
            <td colspan="3">No hay componentes en la composición</td>
        </tr>
    <% } %>
</table>

<form method="post" action="gestioComposicio">
    <input type="hidden" name="it_pare" value="<%= itPare %>">

    <label>Item hijo:</label>
    <select name="it_codi" required>
        <% if(itemsDisponibles != null) {
            for(Item it : itemsDisponibles){
                if(it.getCodii() != itPare.intValue()){ %>
                <option value="<%= it.getCodii() %>"><%= it.getNom() %></option>
        <%      }
            }
        } %>
    </select>

    <label>Cantidad:</label>
    <input type="number" name="quantitat" min="1" value="1" required>

    <button type="submit">Agregar</button>
</form>

<a href="gestioItems">⬅ Volver a Items</a>

</body>
</html>
