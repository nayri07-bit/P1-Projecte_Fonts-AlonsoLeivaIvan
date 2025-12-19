<%@page import="p1.t4.model.UnitatMesura"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="p1.t4.model.Component, p1.t4.model.Item, p1.t4.model.UnitatMesura, java.util.List" %>

<html>
<head>
    <title>Formulario Componente</title>
    <style>
        body {
            font-family: Verdana, Geneva, Tahoma, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }

        h2 {
            color: #FF6A45;
            margin-bottom: 20px;
            padding-bottom: 4px;
            border-bottom: 2px solid #000000;
        }

        form {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            width: 400px;
            margin: 0 auto;
        }

        label {
            display: block;
            margin: 10px 0 5px;
            font-weight: bold;
            color: #333;
        }

        input, select {
            width: 100%;
            padding: 8px;
            margin: 8px 0 15px 0;
            border-radius: 4px;
            border: 1px solid #ccc;
            font-size: 14px;
        }

        button {
            background-color: #FF6A45;
            color: white;
            border: none;
            padding: 10px 20px;
            font-weight: 600;
            cursor: pointer;
            border-radius: 4px;
            width: 100%;
            font-size: 16px;
        }

        button:hover {
            background-color: #F3A47B;
        }

        .error {
            color: red;
            margin-bottom: 15px;
            font-size: 14px;
        }

        .form-group {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<%
    String accion = (String) request.getAttribute("accion");
    Component component = (Component) request.getAttribute("component");
    List<Item> items = (List<Item>) request.getAttribute("items");
    List<String> fabricants = (List<String>) request.getAttribute("fabricants");
    List<UnitatMesura> unitats = (List<UnitatMesura>) request.getAttribute("unitats");
    String error = (String) request.getAttribute("error");

    if(component == null) {
        component = new Component();
    }
%>

<h2><%= "editar".equals(accion) ? "Editar Componente" : "Insertar Componente" %></h2>

<% if(error != null) { %>
    <p class="error"><%= error %></p>
<% } %>

<form action="<%= "editar".equals(accion) ? "editarComponent" : "insertarComponent" %>" method="post">
    <input type="hidden" name="accion" value="<%= accion %>" />

    <div class="form-group">
        <label for="cm_codi">Código (Item):</label>
        <% if("editar".equals(accion) && component.getCodic() != 0) { %>
            <input type="text" value="<%= component.getCodic() %>" disabled />
            <input type="hidden" name="cm_codi" value="<%= component.getCodic() %>" />
        <% } else { %>
            <select name="cm_codi" id="cm_codi" required>
                <option value="" disabled selected>Seleccione un código</option>
                <% for(Item it : items) { %>
                    <option value="<%= it.getCodii() %>"><%= it.getCodii() + " - " + it.getNom() %></option>
                <% } %>
            </select>
        <% } %>
    </div>

    <div class="form-group">
        <label for="fabricant">Fabricante:</label>
        <select name="cm_codi_fabricant" required>
            <option value="" disabled selected>Seleccione un fabricante</option>
            <% for(String fab : fabricants) { 
                   boolean selected = component.getCodiFabricant() != null && fab.equals(component.getCodiFabricant());
            %>
                <option value="<%= fab %>" <%= selected ? "selected" : "" %>><%= fab %></option>
            <% } %>
        </select>
    </div>

    <div class="form-group">
        <label for="um_codi">Unidad de medida:</label>
        <select name="cm_um_codi" required>
            <option value="" disabled selected>Seleccione una unidad</option>
                <% for(UnitatMesura um : unitats) {
                       boolean selected = component.getUnitatMesura() != null && component.getUnitatMesura().getCodiu() == um.getCodiu();
                %>
                <option value="<%= um.getCodiu() %>" <%= selected ? "selected" : "" %>>
                    <%= um.getNom() %>
                </option>
            <% } %>
        </select>
    </div>

    <div class="form-group">
        <label for="precio">Precio inicial:</label>
        <input type="number" name="precioInicial" id="precio" min="1" required
               value="<%= component.getPreuMig() %>" />
    </div>

    <button type="submit"><%= "editar".equals(accion) ? "Actualizar" : "Insertar" %></button>
</form>
</body>
</html>
