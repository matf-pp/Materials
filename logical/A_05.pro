% Komentar za studente: cinjenice opisuju poznate podatke, a pravila i rekurzija opisuju kako Prolog izvodi zakljucke.
% suma prvih N prirodnih brojeva
suma(1,1).
suma(N,S):- N>1, N1 is N-1, suma(N1,S1), S is S1+N. 
