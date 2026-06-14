% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
% 2.2.29: TEN + SEVEN + SEVEN + SEVEN + FOUR + FOUR + ONE = FORTY

solve(Vars) :-
    Vars = [E, F, N, O, R, S, T, U, V, Y],
    Vars :: 0..9,
    F #\= 0,
    O #\= 0,
    S #\= 0,
    T #\= 0,
    alldifferent(Vars),
    ONE #= 100*O + 10*N + E,
    TEN #= 100*T + 10*E + N,
    FOUR #= 1000*F + 100*O + 10*U + R,
    FORTY #= 10000*F + 1000*O + 100*R + 10*T + Y,
    SEVEN #= 10000*S + 1000*E + 100*V + 10*E + N,
    TEN + SEVEN + SEVEN + SEVEN + FOUR + FOUR + ONE #= FORTY,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
