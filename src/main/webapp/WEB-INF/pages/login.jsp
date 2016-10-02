<%--
  Created by IntelliJ IDEA.
  User: sebbos
  Date: 28.09.2016
  Time: 17:01
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Page de connexion</title>
</head>
<body>
<h1>Connexion</h1>
<p>Veuillez-vous connecter.</p>
<form method="post" action="">
    <p>
        <label for="userName">Votre nom d'utilisateur (email) : </label><input type="text" name="userName" id="userName" />
        <br>
        <label for="userPassword">Votre mot de passe : </label><input type="password" name="userPassword" id="userPassword" />
        <br>
        <input type="submit" value="Envoyer" />
    </p>
</form>
<p>
    <a href="register">Créer un compte</a>
</p>
</body>
</html>
