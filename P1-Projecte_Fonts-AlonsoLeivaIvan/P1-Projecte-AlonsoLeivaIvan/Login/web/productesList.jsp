<%@page import="java.util.Map"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, p1.t4.model.Item" %>

<%
    List<Item> items = (List<Item>) request.getAttribute("items");
    String ordenSeleccionado = (String) request.getAttribute("orden");
    String buscarCodi = request.getParameter("buscarCodi");
    String buscarNom = request.getParameter("buscarNom");
    String contextPath = request.getContextPath();
    String error = (String) request.getAttribute("error");
%>

<html>
<head>
    <title>Lista de Items</title>
    <link rel="stylesheet" href="estils.css">
    <style>
        body {
            font-family: Verdana, Geneva, Tahoma, sans-serif;
            color: #000000;
            background-color: white;
            margin: 0;
        }

        .menu-lateral {
            position: fixed;
            top: 0;
            left: 0;
            width: 220px;
            height: 100%;
            background-color: #FF6A45;
            padding-top: 20px;
        }

        .menu-lateral ul {
            list-style-type: none;
            padding: 0;
        }

        .menu-lateral ul li {
            margin: 20px 0;
        }

        .menu-lateral ul li a {
            color: white;
            text-decoration: none;
            font-weight: 600;
            padding: 10px 20px;
            display: block;
        }

        .menu-lateral ul li a:hover {
            background-color: #F3A47B;
        }

        .contenido {
            margin-left: 240px;
            padding: 40px;
        }

        h2 {
            color: #F3A47B;
            border-bottom: 2px solid #000000;
            padding-bottom: 4px;
            margin-bottom: 20px;
        }

        form {
            margin-bottom: 20px;
        }

        input[type="text"], input[type="number"], select {
            padding: 6px;
            margin-right: 10px;
            border: 1px solid #CCC;
            border-radius: 4px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
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

        button {
            background-color: #FF6A45;
            color: white;
            border: none;
            padding: 8px 12px;
            font-weight: 600;
            cursor: pointer;
            border-radius: 4px;
            margin-top: 5px;
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

        p.error {
            color: red;
            margin-bottom: 10px;
        }
        .menu-lateral {
            position: fixed;
            top: 0;
            left: 0;
            width: 220px;
            height: 100%;
            background-color: #FF6A45;
            padding-top: 20px;
            overflow: hidden;
            transition: width 0.3s;
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
            list-style-type: none;
            padding: 0;
            margin: 0;
            display: none; 
        }

        .menu-lateral ul li a {
            display: block;
            color: white;
            text-decoration: none;
            padding: 10px 20px;
        }

        .menu-lateral ul li a:hover {
            background-color: #F3A47B;
        }

        .menu-lateral.active ul {
            display: block; 
        }

        .contenido {
            margin-left: 220px;
            padding: 40px;
            transition: margin-left 0.3s;
        }

        .menu-lateral.collapsed {
            width: 60px;
        }

        .menu-lateral.collapsed ul {
            display: none;
        }

        .menu-lateral.collapsed button.toggle-btn::after {
            content: "▶";
            float: right;
        }

        .menu-lateral.active.collapsed ul {
            display: block;
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

<div class="contenido">
    <h2>Items</h2>

    <% if (error != null && !error.isEmpty()) { %>
        <p class="error"><%= error %></p>
    <% } %>

    <form method="get" action="<%= contextPath %>/gestioItems">
        <label>Buscar por código:</label>
        <input type="text" name="buscarCodi" value="<%= buscarCodi != null ? buscarCodi : "" %>">
        <label>Buscar por nombre:</label>
        <input type="text" name="buscarNom" value="<%= buscarNom != null ? buscarNom : "" %>">
        <label>Ordenar por:</label>
        <select name="orden" onchange="this.form.submit()">
            <option value="" <%= "".equals(ordenSeleccionado) ? "selected" : "" %>>-- Sin ordenar --</option>
            <option value="codigo" <%= "codigo".equals(ordenSeleccionado) ? "selected" : "" %>>Código</option>
            <option value="nombre" <%= "nombre".equals(ordenSeleccionado) ? "selected" : "" %>>Nombre</option>
            <option value="stock" <%= "stock".equals(ordenSeleccionado) ? "selected" : "" %>>Stock</option>
        </select>
        <button type="submit">Buscar</button>
    </form>


    <form method="post" action="<%= contextPath %>/borrarItems" onsubmit="return confirmarBorrado()">
        <table>
            <tr>
                <th>Seleccionar</th>
                <th>Código</th>
                <th>Nombre</th>
                <th>Descripción</th>
                <th>Stock</th>
                <th>Tipo</th>
                <th>Foto</th>
                <th>Acciones</th>
            </tr>
            <% if(items != null && !items.isEmpty()) {
                for(Item it : items){ %>
                    <tr>
                        <td><td><input type="checkbox" name="it_codi" value="<%= it.getCodii() %>"></td></td>
                        <td><%= it.getCodii() %></td>
                        <td><%= it.getNom() %></td>
                        <td><%= it.getDescr() != null ? it.getDescr() : "" %></td>
                        <td><%= it.getStock() %></td>
                        <td><%= it.isEsProducte() ? "Producto" : "Componente" %></td>
                        <td>
                            <% if(it.getFoto() != null && it.getFoto().length > 0){ %>
                                <img src="<%= contextPath %>/verFotoItem?it_codi=<%= it.getCodii() %>" width="50" height="50">
                            <% } else { %>
                                N/A
                            <% } %>
                        </td>
                        <td>
                            <% if(it.isEsProducte()) { %>
                                <button type="button" onclick="window.location.href='<%= contextPath %>/gestioComposicio?it_pare=<%= it.getCodii() %>'">Composición</button>
                            <% } %>
                            <button type="button" onclick="window.location.href='<%= contextPath %>/arbolProductos?it_codi=<%= it.getCodii() %>'">
                                Árbol
                            </button>

                            <% if(it.isEsProducte()) { %>
                                <button type="button" onclick="window.open('http://localhost:8090/jasperserver/flow.html?_flowId=viewReportFlow&reportUnit=/Report_proyecto/report_dun_sol_item&PR_CODI=<%= it.getCodii() %>&j_username=jasperadmin&j_password=jasperadmin','_blank')">BOM</button>
                            <% } %>
                        </td>
                    </tr>
            <%  }
               } else { %>
                <tr>
                    <td colspan="8">No hay items disponibles</td>
                </tr>
            <% } %>
        </table>
        <br>
        <button type="submit">Borrar seleccionados</button>
    </form>

    <button type="button" onclick="window.location.href='<%= contextPath %>/insertarItem'">
        Insertar Item
    </button>

    <button type="button" onclick="editarItem()">
        Editar Item
    </button>

    <hr>

    <button type="button" onclick="window.open('http://localhost:8090/jasperserver/flow.html?_flowId=viewReportFlow&reportUnit=/Report_proyecto/report_proyecto&j_username=jasperadmin&j_password=jasperadmin','_blank')">Ver informe de productos</button>

</div>

<script>
    function confirmarBorrado() {
        return confirm("¿Está seguro de que quiere borrar los items seleccionados?");
    }

    function editarItem() {
        var checkboxes = document.querySelectorAll('input[name="it_codi"]:checked');
        if (checkboxes.length !== 1) {
            alert("Seleccione exactamente un item para editar");
            return;
        }
        window.location.href = '<%= contextPath %>/cargarItem?it_codi=' + checkboxes[0].value;
    }
</script>
<script>
    function toggleMenu() {
        document.getElementById('menuLateral').classList.toggle('active');
    }
</script>
</body>
</html>
