/*

Insertion sort algoritam se zasniva na ubacivanju redom svakog elementa liste na svoje pravo
mesto. 

IH (Induktivna hipoteza) – umemo da sortiramo listu od n-1 elementa. 
IK (Induktivni korak) – n-ti element ubacujemo na odgovarajucu lokaciju.

*/
insertionSort([], []).
insertionSort([G|R], SL):- insertionSort(R, S1), ubaciS(G, S1, SL).

% ubacuje sortirano element X u listu 
ubaciS(X, [], [X]).

% ubaciS(X, [G|R], [X, G|R]):- X=<G.
% ubaciS(X, [G|R], [G|SL]):- X>G, ubaciS(X, R, SL).
/*
 alternativno sa cut operatorom ne moramo
 da pisemo X>G u drugom predikatu, jer 
 kada stavimo cut operator kod prvog, cim 
 on uspe drugi predikat se nece ni pokusavati
*/
ubaciS(X, [G|R], [X, G|R]):- X=<G, !.
ubaciS(X, [G|R], [G|SL]):- ubaciS(X, R, SL).



