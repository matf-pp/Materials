% 2.2.28: NINE + SEVEN + SEVEN + SEVEN = THIRTY

solve(Vars) :-
    Vars = [E, H, I, N, R, S, T, V, Y],
    Vars :: 0..9,
    N #\= 0,
    S #\= 0,
    T #\= 0,
    alldifferent(Vars),
    NINE #= 1000*N + 100*I + 10*N + E,
    SEVEN #= 10000*S + 1000*E + 100*V + 10*E + N,
    THIRTY #= 100000*T + 10000*H + 1000*I + 100*R + 10*T + Y,
    NINE + SEVEN + SEVEN + SEVEN #= THIRTY,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
