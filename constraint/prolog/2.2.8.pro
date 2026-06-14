% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
% 2.2.8: CROSS + ROADS = DANGER

solve(Vars) :-
    Vars = [A, C, D, E, G, N, O, R, S],
    Vars :: 0..9,
    C #\= 0,
    D #\= 0,
    R #\= 0,
    alldifferent(Vars),
    CROSS #= 10000*C + 1000*R + 100*O + 10*S + S,
    ROADS #= 10000*R + 1000*O + 100*A + 10*D + S,
    DANGER #= 100000*D + 10000*A + 1000*N + 100*G + 10*E + R,
    CROSS + ROADS #= DANGER,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
