% 2.2.35: SIXTEEN + TWELVE + TWELVE + TWELVE + NINE + NINE = SEVENTY

solve(Vars) :-
    Vars = [E, I, L, N, S, T, V, W, X, Y],
    Vars :: 0..9,
    N #\= 0,
    S #\= 0,
    T #\= 0,
    alldifferent(Vars),
    NINE #= 1000*N + 100*I + 10*N + E,
    TWELVE #= 100000*T + 10000*W + 1000*E + 100*L + 10*V + E,
    SEVENTY #= 1000000*S + 100000*E + 10000*V + 1000*E + 100*N + 10*T + Y,
    SIXTEEN #= 1000000*S + 100000*I + 10000*X + 1000*T + 100*E + 10*E + N,
    SIXTEEN + TWELVE + TWELVE + TWELVE + NINE + NINE #= SEVENTY,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
