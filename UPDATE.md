# Mise à jour

Ce document explique comment va se dérouler la mise à jour du système.
Cette mise à jour vise à rajouter un système de bonus temporaire qui changerait tous les 15 jours.

Pour eviter la coupure de service, la mise à jours serait diffusé sur les serveurs petit à petit. On commencerai par empecher de créer des parties sur un serveur spécifique, puis une fois toute les parties du serveur finies, on eteindrait le serveur, le mettrait à jour et le redémarrerait. Puis, il y aurrait juste à répetter l'opération pour chaque serveurs.
Pour que les anciens joueurs puissent jouer, il y aurrait deux possibilités:
* Maintient de l'ancienne API et création de la nouvelle API sur un autre lien:
    * Pas de déséquilibre entre les joueuers
    * Communauté séparé sur deux versions donc plus de difficulté pour trouver des parties en fonction de la répartition des joueurs.
* Création de la deuxième API par dessus l'anciennes:
    * Les joueurs sur la nouvelles versions peuvent jouer avec ceux sur l'anciennes.
    * Déséquilibre potentielle entre les joueurs des deux versions. Ceux sur l'ancienne versions ne pourraient pas activer les bonus et seraient donc désavantagés.
    
Pour avertir les joueuers nous pourrions envoyer un message en jeu, par mail ou sur le site internet.
