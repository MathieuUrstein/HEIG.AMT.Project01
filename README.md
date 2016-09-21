# Déploiement de l'application

1. Construire l'application en entrant la commande suivante directement à la racine du répertoire cloné :
```
mvn clean install
```
2. Un fichier "Labo01-1.0.war" est créé dans un nouveau dossier "target" apparaissant également à la racine.
3. Copier ce fichier sous "docker\images\wildfly\".
4. Il faut maintenant entrer la commande qui suit à nouveau à la racine pour lancer l'application :
```
docker-compose up --build
```
5. Pour voir l'application active, il suffit de taper l'URL suivante "http://localhost:9090/Labo01-1.0" dans le navigateur.
