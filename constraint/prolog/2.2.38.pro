% 2.2.38: FIVE + FIVE + TEN + TEN + TEN + TEN + THIRTY = EIGHTY

solve(Vars) :-
    Vars = [E, F, G, H, I, N, R, T, V, Y],
    Vars :: 0..9,
    E #\= 0,
    F #\= 0,
    T #\= 0,
    alldifferent(Vars),
    TEN #= 100*T + 10*E + N,
    FIVE #= 1000*F + 100*I + 10*V + E,
    EIGHTY #= 100000*E + 10000*I + 1000*G + 100*H + 10*T + Y,
    THIRTY #= 100000*T + 10000*H + 1000*I + 100*R + 10*T + Y,
    FIVE + FIVE + TEN + TEN + TEN + TEN + THIRTY #= EIGHTY,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
