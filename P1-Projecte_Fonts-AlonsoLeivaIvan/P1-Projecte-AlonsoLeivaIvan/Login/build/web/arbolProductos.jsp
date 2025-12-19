<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, p1.t4.model.Item, p1.t4.model.Composicio, p1.t4.daooracle.DAOItemOracle, p1.t4.daooracle.DAOComposicioOracle, p1.t4.daooracle.OracleConnection, java.sql.Connection" %>

<%
    Item itemPadre = (Item) request.getAttribute("item");
    Connection conn = OracleConnection.getConnection();
    DAOItemOracle daoItem = new DAOItemOracle(conn);
    DAOComposicioOracle daoComp = new DAOComposicioOracle(conn);

    java.util.Stack<Item> pila = new java.util.Stack<Item>();
%>

<html>
<head>
    <title>Árbol de Productos</title>
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

        ul {
            list-style-type: none;
            padding-left: 20px;
        }

        li {
            margin: 5px 0;
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

        p {
            margin-top: 10px;
        }
    </style>
</head>
<body>

<h2>Árbol de Productos</h2>

<% if(itemPadre != null) { %>
    <ul>
        <%
            List<Composicio> hijos1 = daoComp.getByProducte(itemPadre.getCodii());
            out.println("<li>" + itemPadre.getNom());
            if(hijos1 != null && !hijos1.isEmpty()) {
                out.println("<ul>");
                for(int i = 0; i < hijos1.size(); i++) {
                    int codiHijo1 = hijos1.get(i).getItFill();
                    Item hijo1 = daoItem.getById(codiHijo1);
                    List<Composicio> hijos2 = daoComp.getByProducte(hijo1.getCodii());
                    out.println("<li>" + hijo1.getNom());
                    if(hijos2 != null && !hijos2.isEmpty()) {
                        out.println("<ul>");
                        for(int j = 0; j < hijos2.size(); j++) {
                            int codiHijo2 = hijos2.get(j).getItFill();
                            Item hijo2 = daoItem.getById(codiHijo2);
                            out.println("<li>" + hijo2.getNom() + "</li>");
                        }
                        out.println("</ul>");
                    }
                    out.println("</li>");
                }
                out.println("</ul>");
            }
            out.println("</li>");
        %>
    </ul>
<% } else { %>
    <p>No hay productos para mostrar.</p>
<% } %>

<br>
<button type="button" onclick="window.location.href='gestioItems'">Volver a Items</button>

<%
    conn.close();
%>

</body>
</html>
