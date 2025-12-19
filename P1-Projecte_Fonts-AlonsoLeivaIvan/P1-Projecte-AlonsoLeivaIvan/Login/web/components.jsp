<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, p1.t4.model.Component" %>
<%
    List<Component> components = (List<Component>) request.getAttribute("components");
    String error = (String) request.getAttribute("error");
    String buscarId = request.getParameter("buscarId");
    String ordre = request.getParameter("ordre");
    String contextPath = request.getContextPath();
%>

<html>
<head>
    <title>Componentes</title>
    <style>
        body {
            font-family: Verdana, Geneva, Tahoma, sans-serif;
            color: #000000;
            background-color: white;
            margin: 0;
            padding: 0;
            display: flex;
        }

        .menu-lateral {
            width: 220px;
            height: 100%;
            background-color: #FF6A45;
            color: white;
            padding-top: 20px;
            position: fixed;
            top: 0;
            left: 0;
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

        .contenido-principal {
            margin-left: 220px;
            padding: 40px;
            transition: margin-left 0.3s;
        }

        h2 {
            color: #F3A47B;
            border-bottom: 2px solid #000000;
            padding-bottom: 4px;
            margin-bottom: 20px;
        }

        table {
            border-collapse: collapse;
            width: 100%;
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

        input[type="checkbox"] {
            margin-right: 10px;
        }

        input, select {
            padding: 6px;
            border: 1px solid #CCC;
            border-radius: 4px;
        }

        .error {
            color: red;
            margin-bottom: 15px;
        }

        .reset-link {
            margin-left: 10px;
            font-size: 14px;
            color: #FF6A45;
            text-decoration: none;
        }

        .reset-link:hover {
            text-decoration: underline;
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
    <h2>Listado de Componentes</h2>

    <form method="get" action="components">
        Buscar por ID:
        <input type="number" name="buscarId" value="<%= buscarId != null ? buscarId : "" %>" />
        Ordenar por:
        <select name="ordre">
            <option value="">-- Sin ordenar --</option>
            <option value="id" <%= "id".equals(ordre) ? "selected" : "" %>>ID</option>
            <option value="fabricant" <%= "fabricant".equals(ordre) ? "selected" : "" %>>Fabricante</option>
            <option value="preu" <%= "preu".equals(ordre) ? "selected" : "" %>>Precio</option>
        </select>
        <button type="submit">Aplicar</button>
        <a href="components" class="reset-link">Reset</a>
    </form>

    <% if(error != null) { %>
        <p class="error"><%= error %></p>
    <% } %>

    <form id="componentsForm" method="post">
        <table>
            <tr>
                <th>Seleccionar</th>
                <th>ID</th>
                <th>Fabricante</th>
                <th>Unidad</th>
                <th>Precio medio</th>
            </tr>
            <% if(components != null && !components.isEmpty()) {
                for(Component c : components) { %>
                    <tr>
                        <td><input type="checkbox" name="cm_codi" value="<%= c.getCodic() %>"></td>
                        <td><%= c.getCodic() %></td>
                        <td><%= c.getCodiFabricant() %></td>
                        <td><%= c.getUnitatMesura().getNom() %></td>
                        <td><%= c.getPreuMig() %></td>
                    </tr>
            <%  }
               } else { %>
                    <tr>
                        <td colspan="5">No hay componentes disponibles</td>
                    </tr>
            <% } %>
        </table>

        <br>

        <button type="button" onclick="editarSeleccionado()">
            Editar seleccionado
        </button>

        <button type="submit" formaction="<%= contextPath %>/borrarComponents" formmethod="post" data-borrar="true">
            Borrar seleccionados
        </button>
    </form>

    <br>

    <form action="cargarComponent" method="get">
        <button type="submit">Insertar nuevo componente</button>
    </form>
</div>

<script>
    function toggleMenu() {
        document.getElementById('menuLateral').classList.toggle('active');
    }

    function editarSeleccionado() {
        var checkboxes = document.querySelectorAll('#componentsForm input[name="cm_codi"]:checked');
        if (checkboxes.length !== 1) {
            alert("Debe seleccionar exactamente un componente para editar.");
            return;
        }
        var cm_codi = checkboxes[0].value;
        window.location.href = '<%= contextPath %>/cargarComponent?cm_codi=' + cm_codi;
    }

    document.getElementById('componentsForm').addEventListener('submit', function(event) {
        var boton = event.submitter;
        if (boton && boton.dataset.borrar) {
            if (!confirm('¿Seguro que deseas borrar los componentes seleccionados?')) {
                event.preventDefault();
            }
        }
    });
</script>
</body>
</html>
