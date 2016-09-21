# Déploiement de l'application

1. Construire l'application en entrant la commande suivante directement à la racine du répertoire cloné :

```
mvn clean install
```

2. Un fichier "Labo01-1.0.war" est créé dans un nouveau dossier "target" apparaissant également à la racine.
3. Copier ce fichier sous "docker\images\wildfly\".
4. Il suffit maintenant d'entrer la commande au même endroit que la première commande :

```
docker-compose up --build
```

5. Pour voir l'application, il faut entrer l'URL suivante "http://localhost:9090/Labo01-1.0".
