<%--
  Created by IntelliJ IDEA.
  User: sebbos
  Date: 01.10.2016
  Time: 22:52
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Page d'enregistrement</title>
</head>
<body>
<h1>Enregistrement</h1>
<p>Veuillez-vous enregistrer.</p>
<form method="post" action="">
    <p>
        <label for="userName">Votre nom d'utilisateur (email) : </label><input type="text" name="userName" id="userName" />
        <br>
        <label for="userPassword">Votre mot de passe : </label><input type="password" name="userPassword" id="userPassword" />
        <br>
        <input type="submit" value="Envoyer" />
    </p>
</form>
</body>
</html>
