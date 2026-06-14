% Komentar za studente: cinjenice opisuju poznate podatke, a pravila i rekurzija opisuju kako Prolog izvodi zakljucke.
% proizvod prvih N prirodnih brojeva
proizvod(1,1).
proizvod(N,P):- N>1, N1 is N-1, proizvod(N1,P1), P is P1*N. 
