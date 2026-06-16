**Lab 03 – Code Coverage (JaCoCo) - Group 11**

**Objective:** The goal of this lab was to analyze and improve test coverage for the Date class using JUnit and JaCoCo.

**Initial Coverage: Before adding new tests, coverage was measured using JaCoCo.**
<img width="386" height="477" alt="image" src="https://github.com/user-attachments/assets/a5ed1070-eb16-4840-8579-347d64626036" />
<img width="975" height="160" alt="image" src="https://github.com/user-attachments/assets/7eeaf129-dda1-4128-886c-40affaa9d89c" />

**Apres modifications: ** modifications apporter afin d’améliorer la couverture de code de la classe Date. De nouveaux tests ont été ajoutés pour couvrir davantage de cas limites. Ceux-ci incluent les conditions aux bornes des mois, la gestion des années bissextiles ainsi que des scénarios d’entrées invalides. Ces modifications ont permis d’exécuter des chemins supplémentaires dans les méthodes principales, en particulier dans setDay, isEndOfMonth et equals, contribuant ainsi à une amélioration significative de la couverture globale mesurée par JaCoCo.
<img width="230" height="600" alt="image" src="https://github.com/user-attachments/assets/f0955016-fba6-4433-ba00-519f2e9040c1" />
<img width="764" height="128" alt="image" src="https://github.com/user-attachments/assets/21c69c29-c074-4df8-a719-a45a6244d190" />

**Analyse: **
Après avoir étendu la suite de tests à 79 cas de test, la couverture des instructions a atteint 93 % et la couverture des branches 94 %. Des tests supplémentaires ont été ajoutés afin de cibler les conditions aux limites, la logique des années bissextiles et les entrées invalides. Cependant, une couverture complète des branches n’a pas pu être atteinte en raison de chemins conditionnels mutuellement exclusifs ou non atteignables dans des scénarios d’exécution valides. Par conséquent, une couverture de 100 % n’est pas réalisable pour cette implémentation.


