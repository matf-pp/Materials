% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
% 2.2.14: DONT + STOP + THE = DANCE

solve(Vars) :-
    Vars = [A, C, D, E, H, N, O, P, S, T],
    Vars :: 0..9,
    D #\= 0,
    S #\= 0,
    T #\= 0,
    alldifferent(Vars),
    THE #= 100*T + 10*H + E,
    DONT #= 1000*D + 100*O + 10*N + T,
    STOP #= 1000*S + 100*T + 10*O + P,
    DANCE #= 10000*D + 1000*A + 100*N + 10*C + E,
    DONT + STOP + THE #= DANCE,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
