% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
% 2.2.31: FOURTEEN + TEN + TEN + SEVEN = FORTYONE

solve(Vars) :-
    Vars = [E, F, N, O, R, S, T, U, V, Y],
    Vars :: 0..9,
    F #\= 0,
    S #\= 0,
    T #\= 0,
    alldifferent(Vars),
    TEN #= 100*T + 10*E + N,
    SEVEN #= 10000*S + 1000*E + 100*V + 10*E + N,
    FORTYONE #= 10000000*F + 1000000*O + 100000*R + 10000*T + 1000*Y + 100*O + 10*N + E,
    FOURTEEN #= 10000000*F + 1000000*O + 100000*U + 10000*R + 1000*T + 100*E + 10*E + N,
    FOURTEEN + TEN + TEN + SEVEN #= FORTYONE,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
