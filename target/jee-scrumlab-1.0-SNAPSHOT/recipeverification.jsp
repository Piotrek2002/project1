<%--
  Created by IntelliJ IDEA.
  User: piotr
  Date: 30.01.2020
  Time: 23:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Zaplanuj Jedzonko</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Charmonman:400,700|Open+Sans:400,600,700&amp;subset=latin-ext"
          rel="stylesheet">
    <link rel="stylesheet" href="./css/style.css">
    <link href='<c:url value="/css/style.css"/>' rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
</head>
<body>

<%@include file="backend-header.jsp" %>

<section class="dashboard-section">
    <div class="row dashboard-nowrap">
        <div class="m-4 p-3 width-medium">
            <div class="dashboard-content border-dashed p-3 m-4 view-height">
                <div class="col noPadding"><h3 class="color-header text-uppercase">Czy na pewno chcesz usunąć ten przepis ?</h3></div>
                <table class="table border-bottom schedules-content">

                    <tbody class="text-color-lighter">

                    <tr class="d-flex">

                        <td class="col-12 d-flex align-items-center justify-content-center flex-wrap">
                            <a href="/DeleteRecipeServlet?id=${id}&&delete=1" class="btn btn-danger rounded-0 text-light m-1">OK</a>
                            <a href="/DeleteRecipeServlet?delete=0" class="btn btn-info rounded-0 text-light m-1">Anuluj</a>

                        </td>
                    </tr>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</section>
<%@include file="scripts.jsp"%>
</body>
</html>
