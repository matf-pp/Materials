% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
% 2.2.10: WE + WANT + NO + NEW + ATOMIC = WEAPON

solve(Vars) :-
    Vars = [A, C, E, I, M, N, O, P, T, W],
    Vars :: 0..9,
    A #\= 0,
    N #\= 0,
    W #\= 0,
    alldifferent(Vars),
    NO #= 10*N + O,
    WE #= 10*W + E,
    NEW #= 100*N + 10*E + W,
    WANT #= 1000*W + 100*A + 10*N + T,
    ATOMIC #= 100000*A + 10000*T + 1000*O + 100*M + 10*I + C,
    WEAPON #= 100000*W + 10000*E + 1000*A + 100*P + 10*O + N,
    WE + WANT + NO + NEW + ATOMIC #= WEAPON,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
