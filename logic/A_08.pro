% proizvod neparnih prirodnih brojeva od 1 do N
proizvodNeparnih(1,1).
proizvodNeparnih(N,P):- N>1, N1 is N-2, proizvodNeparnih(N1,P1), P is P1*N.
