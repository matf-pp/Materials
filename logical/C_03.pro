mergeSort([], []).
mergeSort([X], [X]).
mergeSort(N, SL):- podeli(N, L, R),
mergeSort(L, L1),
mergeSort(R, R1),
objedini(L1, R1, SL).

podeli([], [], []).
podeli([X], [X], []):- !.
podeli([G1, G2|R], [G1|R1], [G2|R2]):- podeli(R, R1, R2).

objedini(L, [], L).
objedini([], L, L).
objedini([G1|R1], [G2|R2], [G1|R]):- G1<G2, objedini(R1, [G2|R2], R), !.
objedini([G1|R1], [G2|R2], [G2|R]):- G1>=G2, objedini([G1|R1], R2, R).
