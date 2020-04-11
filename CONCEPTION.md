# Conception
## Règle du jeu
Il s'agit d'un jeu de char en ligne. Les joueurs sont lancé sur une carte spherique pouvant avoir trois taille possible (100x100, 300x300x 600x600). La carte est considérée comme sphérique car lorsqu'un joueur ou un tir arrive au bout de la carte, il se retrouve de l'autre côté et peu continuer son chemin.
Sur la carte, il y a de nombreux obstacles destructibles. Ceux-ci sont placés aléatoirement et représante 15% de la map.

Pour commencer, la partie doit avoir au minimum 2 joueurs. Ces joueur sont placé aléatoirement sur la carte.
Chaque joueur dispose d'un canon pouvant tirer des lasers. Ces lasers ont une portée de 5 cases, 30 cases ou infinie. Celle-ci est choisie aléatoirement à la création de la partie et est la même pour tous les joueurs. Ce laser permet de détruire les obstacles ainsi que les autres joueurs mais peu aussi se retourner contre son propriétaire à cause de la carte sphérique.

Le jeu se déroule en temps réel, les joueurs sont de même limité dans leur nombre d'actions par seccondes, cela permet d'éviter que des joueurs bloquent la partie comme cela aurait été possible en tour par tour. Le fait de limiter les actions par secondes permet d'empecher le développement d'IA au tous du moins d'empecher qu'elles ne gagne la partie en queleque secondes.
Le joueurs peut effectuer 5 actions différentes:
* Avancer
* Reculer
* Tourner à droite
* Tourner à gauche
* Tirer

Il peut visualiser les obstacles et autres joueurs dans un carré de 30x30 autour de lui.
Une fois qu'il ne reste qu'un seul joueur, la partie prend fin.
Les parties seront limitées à 50 joueurs.

## Fonctionnement du Serveur
Notre système est composé d'un serveur proposant une API REST qui permet au joueur de rejoindre une partie ainsi que d'envoyer les commandes au serveur. Il est aussi composé d'une base de donnée pour stocker les informations des joueurs
Pour jouer, un utilisateur aurra besoin de se créer un compte sur le serveur, celui-ci sera stocké dans une base de donnée. Les mots de passe devront être assez complex (Majuscules, Minuscules, Chiffres, Charactères spéciaux, Taille Minimal de 16). Seul leur hash sera stocké, dans le cas d'un vole de la base de donnée, il ne sera donc pas possible pour les attaquants de les récupérrer. Les utilisateurs aurront aussi un pseudo unique qui leur sera associé.

Les informations des parties en cours seront simplement stockés en mémoir le temps que la partie prenne fin. Ces données ne comprennent que les emplacements des obstacles, la taille de la carte, la liste des joueur ainsi que la liste des joueurs vivant et leur position.
Pour créer une partie, il suffit de créer un nouvelle objet grâce à PartyImpl contenue dans le package: com.esiea.tp4Game, par défaut tous les paramètres sont générés aléatoirement. Cette objet représente une partie, il suffit donc d'en créer un autre pour créer une autre partie. Pour ajouter un joueur, il suffit d'onc d'appeler la fonction addPlayer, celle si renvéra un objet pointant vers la partie et contenant le pseudo du joueur, cette objet permet donc de controller un joueur de manère spécifique sans avoir à renseigner sont pseudo dans les arguments des fonctions.
Pour ce qui est du multi partie du point de vue de l'API REST, comme les pseudo des joueurs sont uniques, il aurra besoin d'utiliser un id pour la partie, il suffira juste de créer un PlayerController à l'aide de l'objet de la partie non commencé qui correspond à l'id fourni dans l'API REST, le serveur possèdera donc des objets PlayerController différent pour chaque partie et aurra juste à appeler le bon..

Si, un serveur plante, cela n'impactera pas les autres serveur car chaque serveur est indépendant car la base de données ne sert qu'a stocker les informations tel que le mot de pass, l'email, le pseudo et le score. Donc si un serveur plante, seule les partie qui étaient hébergé dessus seront perdu.
Si jamais la base de donnée plantes, les parties commencée pourront être fini mais ils sera impossible d'enregister d'incrementer les statistiques des joueur ainsi que de se connecter.

Les joueurs d'authentifie par la combinaison d'un email et d'un mot de pass. Leur statistique seront stocké sur la base de donnée.

Pour gérer des charges imprévue, le système sera installer une une infrastructure de type Platform as a Service, les serveurs pourront donc s'adapter à des charges imprévues.
