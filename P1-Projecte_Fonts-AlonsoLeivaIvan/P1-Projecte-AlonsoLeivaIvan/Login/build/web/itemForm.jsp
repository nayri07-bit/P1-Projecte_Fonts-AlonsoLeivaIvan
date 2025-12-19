<%@page import="p1.t4.model.Item"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Item item = (Item) request.getAttribute("item"); 
    String contextPath = request.getContextPath();
    String error = (String) request.getAttribute("error");

    boolean esEdicion = (item != null);
%>

<html>
<head>
    <title><%= esEdicion ? "Editar Item" : "Insertar Item" %></title>
    <link rel="stylesheet" href="estils.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 40px;
            background-color: #fff;
            color: #000;
        }

        h2 {
            color: #F36A45;
            border-bottom: 2px solid #000;
            padding-bottom: 5px;
        }

        form { max-width: 500px; }

        label {
            display: block;
            margin-top: 10px;
        }

        input[type="text"], input[type="number"], select, input[type="file"] {
            width: 100%;
            padding: 6px;
            margin-bottom: 8px;
            border: 1px solid #CCC;
            border-radius: 4px;
        }

        button {
            background-color: #F36A45;
            color: #fff;
            border: none;
            padding: 10px 15px;
            cursor: pointer;
            border-radius: 4px;
            margin-top: 10px;
        }

        button:hover {
            background-color: #F3A47B;
        }

        p.error {
            color: red;
            font-weight: bold;
        }

        .foto-actual img {
            border: 1px solid #CCC;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

<h2><%= esEdicion ? "Editar Item" : "Insertar Item" %></h2>

<% if(error != null) { %>
    <p class="error"><%= error %></p>
<% } %>

<form method="post" action="<%= esEdicion ? contextPath + "/editarItem" : contextPath + "/insertarItem" %>" enctype="multipart/form-data">

    <label>Código:</label>
    <% if(esEdicion) { %>
        <input type="number" name="it_codi" value="<%= item.getCodii() %>" disabled>
        <input type="hidden" name="it_codi" value="<%= item.getCodii() %>">
    <% } else { %>
        <input type="number" name="it_codi" required>
    <% } %>

    <label>Nombre:</label>
    <input type="text" name="it_nom" value="<%= esEdicion ? item.getNom() : "" %>" required>

    <label>Descripción:</label>
    <input type="text" name="it_desc" value="<%= esEdicion ? item.getDescr() : "" %>">

    <label>Stock:</label>
    <input type="number" name="it_stock" min="0" value="<%= esEdicion ? item.getStock() : 0 %>" required>

    <label>Tipo:</label>
    <select name="it_tipus" required>
        <option value="p" <%= esEdicion && item.isEsProducte() ? "selected" : "" %>>Producto</option>
        <option value="c" <%= esEdicion && !item.isEsProducte() ? "selected" : "" %>>Componente</option>
    </select>

    <label>Foto <%= esEdicion ? "(opcional)" : "(obligatoria)" %>:</label>
    <% if(esEdicion && item.getFoto() != null && item.getFoto().length > 0) { %>
        <div class="foto-actual">
            <p>Foto actual:</p>
            <img src="<%= contextPath %>/verFotoItem?it_codi=<%= item.getCodii() %>" width="150" height="150">
        </div>
    <% } %>
    <input type="file" name="it_foto" accept="image/*" <%= esEdicion ? "" : "required" %>>

    <button type="submit"><%= esEdicion ? "Actualizar Item" : "Guardar Item" %></button>
</form>

<br>
<a href="<%= contextPath %>/gestioItems" style="color:#4400FF; font-weight:600;">⬅ Volver a Items</a>

</body>
</html>
