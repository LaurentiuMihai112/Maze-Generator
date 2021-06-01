# Maze-Generator
Proiect realizat de Mihai Cristian-Laurentiu
Acest proiect a fost realizat folosind platforma Java impreuna cu Processing 3 pentru interfata grafica.

Proiectul este o aplicatie desktop care genereaza si rezolva labirinturi.
Acesta contine 2 algoritmi pentru generarea de labirinturi si unul pentru rezolvarea lor.
Generare 1: Este un algoritm bazat pe Depth-first search (DFS). Din matricea labirintului se creeaza un graf conex, si folosind o parcurgere DFS ( in care urmatorul nod este ales aleator) si creeaza un labirint. La fiecare pas  se elimina peretele dintre nodul curent si nodul urmator.

Generare 2: Acest algoritm este bazat pe backtracking. El lucreaza direct pe matricea labirintului si la fiecare pas alege (aleator) o celula vecina cu cea curenta, elimina peretele dintre acestea si continua pana cand toate celulele au fost vizitate.

Solutie: Algoritmul pentru solutie este un algoritm de timp backtracking care indiferent de algoritmul de generare folosit, plecand de la intrarea in labirint va gasi o solutie pentru acesta (algoritmii de generare prezentati anterior asigura intotdeauna existenta unei solutii).

Aplicatia perminte exportul lahirinturilor in formatele (jpeg, png, tif, tga) (atat simplu cat si cu solutia acestuia). Acest lucru se realizeaza printr-un FileChooser (Swing) pentru alegerea unei locatii unde se va face exportul.

Proiectul a fost testat folosit JUnit pentru sigurata ca toate celulele din labirint sunt vizitate, nu sunt eliminati toti peretii unei celule, macar unul dintre pereti este eliminat.
