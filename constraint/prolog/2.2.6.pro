% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
% 2.2.6: COMPLEX + LAPLACE = CALCULUS

solve(Vars) :-
    Vars = [A, C, E, L, M, O, P, S, U, X],
    Vars :: 0..9,
    C #\= 0,
    L #\= 0,
    alldifferent(Vars),
    COMPLEX #= 1000000*C + 100000*O + 10000*M + 1000*P + 100*L + 10*E + X,
    LAPLACE #= 1000000*L + 100000*A + 10000*P + 1000*L + 100*A + 10*C + E,
    CALCULUS #= 10000000*C + 1000000*A + 100000*L + 10000*C + 1000*U + 100*L + 10*U + S,
    COMPLEX + LAPLACE #= CALCULUS,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
