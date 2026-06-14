% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
% 2.2.11: EARTH + AIR + FIRE + WATER = NATURE

solve(Vars) :-
    Vars = [A, E, F, H, I, N, R, T, U, W],
    Vars :: 0..9,
    A #\= 0,
    E #\= 0,
    F #\= 0,
    N #\= 0,
    W #\= 0,
    alldifferent(Vars),
    AIR #= 100*A + 10*I + R,
    FIRE #= 1000*F + 100*I + 10*R + E,
    EARTH #= 10000*E + 1000*A + 100*R + 10*T + H,
    WATER #= 10000*W + 1000*A + 100*T + 10*E + R,
    NATURE #= 100000*N + 10000*A + 1000*T + 100*U + 10*R + E,
    EARTH + AIR + FIRE + WATER #= NATURE,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
