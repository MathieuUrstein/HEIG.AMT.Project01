<%--
  Created by IntelliJ IDEA.
  User: Mathieu Urstein and Sébastien Boson
  Date: 01.10.2016
  Time: 22:52
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Project01 - AMT 2016-2017</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/landing-page.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

<!-- Navigation -->
<nav class="navbar navbar-default navbar-fixed-top topnav" role="navigation">
    <div class="container topnav">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="/Project01">Go back to welcome page</a>
                </li>
                <li>
                    <a href="login">Go back to login page</a>
                </li>
                <!-- <li>
                    <a href="#contact">Contact</a>
                </li> -->
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>

<!-- Page Content -->

<div class="content-section-b">

    <div class="container">

        <div class="row">
            <div class="col-lg-7 col-sm-6">
                <div class="clearfix"></div>
                <h2 class="section-heading">Create your user account</h2>
                <p class="lead">Please enter the required information to create a new account.</p>
                <p class="lead">The fields marked with a * must be filled in.</p>
                <form id="registerForm" method="post" action="">
                    <div class="form-group">
                        <label for="lastName">Last name:</label>
                        <input type="text" class="form-control" id="lastName" name="lastName" value="${requestScope.givenLastName}" maxlength="${requestScope.maxInputSize}">
                    </div>
                    <div class="form-group">
                        <label for="firstName">First name:</label>
                        <input type="text" class="form-control" id="firstName" name="firstName" value="${requestScope.givenFirstName}" maxlength="${requestScope.maxInputSize}">
                    </div>
                    <div class="form-group">
                        <label for="userName">* Username (email):</label>
                        <input type="email" class="form-control" id="userName" name="userName" placeholder="example@domain.com" value="${requestScope.givenUserName}" maxlength="${requestScope.maxInputSize}" required>
                    </div>
                    <div class="form-group">
                        <label for="password">* Password:</label>
                        <input type="password" class="form-control" id="password" name="password" value="${requestScope.givenPassword}" maxlength="${requestScope.maxInputSize}" required>
                    </div>
                    <div class="form-group">
                        <label for="passwordConfirmation">* Confirm your password:</label>
                        <input type="password" class="form-control" id="passwordConfirmation" name="passwordConfirmation" value="${requestScope.givenPasswordConfirmation}" maxlength="${requestScope.maxInputSize}" required>
                    </div>
                    <button type="submit" class="btn btn-default">Create account</button>
                </form>
                <c:if test="${requestScope.message != null}">
                    <br>
                    <div class="alert alert-danger">
                        <p>${requestScope.message}</p>
                    </div>
                </c:if>
            </div>
            <div class="col-lg-3 col-lg-offset-2 margin-top-150px col-sm-6">
                <img class="img-responsive" src="img/register.png" alt="">
            </div>
        </div>

    </div>
    <!-- /.container -->

</div>
<!-- /.content-section-b -->

<!-- jQuery -->
<script src="js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="js/bootstrap.min.js"></script>

</body>

</html>

