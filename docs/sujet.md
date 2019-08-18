# Sujet :

## Objectif :
La mode étant au retro-gaming, je vous propose, dans ce sujet de programmer un jeu de plateformes dans le style des jeux de console 32 bits des années 90 : cherchez votre inspiration dans des jeux comme la série des Super Mario Bros ou celle des Sonic the Hedgehog.

## Principe :
L’univers de jeu est constitué d’une séquence de niveaux en 2D (directions avant/arrière et haut/bas) dans lesquels un personnage, contrôlé par le joueur, se promenade, avec pour objectif de “terminer” chaque niveau (on part de la gauche, l’objectif est d’arriver tout à droite).
Les déplacements de base consistent à marcher/courir vers la droite ou la gauche et à sauter.

Évidemment, le niveau est parsemé d’embûches : on y trouve maints pièges mortels (trous,
pics, flammes, ...) et monstres tout aussi dangereux, mais qu’il est possible de “tuer” (les modalités varient selon les jeux).

## Parti pris :
On essaye de suivre les codes des jeux de l’époque.
Notamment, le niveau est typiquement bien plus large (et souvent plus haut) que l’écran
affiché. Ainsi, la vue défile avec le déplacement du personnage dans le niveau (qui est donc quasiment-fixe sur l’écran).
Le niveau est en 2D, mais on pourra essayer de donner une profondeur, en affichant un arrière plan qui défile plus lentement que la partie “tangible” du niveau. La réalisation technique pourra, ou pas, utiliser des mécanismes de rendu 3D ; la seule contrainte est le rendu final.

## Réalisation technique
Nous vous proposons d’utiliser la bibliothèque graphique JavaFX. JavaFX est la bibliothèque graphique “officielle” de Java des versions 8 à 10. JavaFX partage de nombreux principes avec Swing, mais son API est plus moderne. Par ailleurs, JavaFX propose des bibliothèques intéressantes pour gérer le graphisme (2D et 3D) et les animations. Néanmoins, ceci n’est qu’un conseil. Le projet pourra être réalisé au travers d’autres intermédiaires techniques que JavaFX, selon vos préférences (mais demandez-moi avant de vous engager sur un outil).