# Conception
## Règle du jeu
Il s'agit d'un jeu de char en ligne. Les joueurs sont lancés sur une carte sphérique pouvant avoir trois tailles possibles (100x100, 300x300x 600x600). La carte est considérée comme sphérique car lorsqu'un joueur ou un tir arrive au bout de la carte, il se retrouve de l'autre côté et peut continuer son chemin.
Sur la carte, il y a de nombreux obstacles destructibles. Ceux-ci sont placés aléatoirement et représentent 15% de la carte.

Pour commencer, la partie doit avoir au minimum 2 joueurs. Ces joueur sont placés aléatoirement sur la carte.
Chaque joueur dispose d'un canon pouvant tirer des lasers. Ces lasers ont une portée de 5 cases, 30 cases ou infinie. Celle-ci est choisie aléatoirement à la création de la partie et est la même pour tous les joueurs. Ce laser permet de détruire les obstacles ainsi que les autres joueurs mais peu aussi se retourner contre son propriétaire à cause de la carte sphérique.

Le jeu se déroule en temps réel, cela permet d'éviter que certains joueurs bloquent la partie, comme cela aurait été possible au tour par tour. De plus, les joueurs sont limités dans leurs nombre d'actions par secondes. Cela permet d'empêcher le développement de bots (ou joueurs) trop efficaces, qui pourraient gagner la partie en quelques secondes.
Le joueur peut effectuer 5 actions différentes:
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
Pour jouer, un utilisateur a besoin de se créer un compte sur le serveur, celui-ci est stocké dans une base de donnée. Les mots de passe doivent être assez complexes (Majuscules, Minuscules, Chiffres, Caractères spéciaux, Taille minimale de 16). Seul leur hash est stocké : en cas de vol de données, les mots de passes restent inconnus. Les utilisateurs ont aussi un pseudo unique qui leur sera associé.

Les informations des parties en cours seront simplement stockés en mémoire le temps que la partie prenne fin. Ces données ne comprennent que les emplacements des obstacles, la taille de la carte, la liste des joueur ainsi que la liste des joueurs vivant et leur position.

Pour créer une partie, il suffit de créer un nouvelle objet grâce à PartyImpl contenu dans le package: com.esiea.tp4Game, par défaut tous les paramètres sont générés aléatoirement. Cette objet représente une partie, il suffit donc d'en créer un nouveau pour créer une nouvelle partie.

Pour ajouter un joueur, il suffit d'appeler la fonction addPlayer, celle si renverra un objet PlayerController pointant vers la partie et contenant le pseudo du joueur, cette objet permet donc de controller un joueur de manère spécifique sans avoir à renseigner sont pseudo dans les arguments des fonctions.

Pour ce qui est du multi-parties du point de vue de l'API REST, comme les pseudo des joueurs sont uniques, il y aura besoin d'utiliser un id pour la partie, il suffira juste de créer un PlayerController à l'aide de l'objet de la partie non commencé qui correspond à l'id fourni dans l'API REST, le serveur possèdera donc des objets PlayerController différent pour chaque partie et aura juste à appeler le bon..

Si un serveur crash, cela n'impactera pas les autres serveur car chaque serveur est indépendant. De plus, seules les parties hébergées dessus seront perdues. La base de données ne sert qu'a stocker des informations telles que les mot de passe, les emails, les pseudos et les scores.

Si jamais la base de donnée est endommagée, les parties déjà commencées pourront être finies mais il sera impossible de mettre à jour les statistiques des joueur ainsi que de se connecter.

Les joueurs s'authentifient par la combinaison d'un email et d'un mot de passe. Leur statistiques sont stockées sur la base de données.

Pour gérer des charges et pics imprévus, le système peut être installé sur une service Cloud de type Platform As A Service (Azure, AWS Elastic Beanstalk, Heroku...). Ces plateformes se chargent ensuite automatiquement du déploiement, du dimensionnement, de l'équilibrage de charges etc.
