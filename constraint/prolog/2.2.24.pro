% 2.2.24: SEVEN + SEVEN + SIX = TWENTY

solve(Vars) :-
    Vars = [E, I, N, S, T, V, W, X, Y],
    Vars :: 0..9,
    S #\= 0,
    T #\= 0,
    alldifferent(Vars),
    SIX #= 100*S + 10*I + X,
    SEVEN #= 10000*S + 1000*E + 100*V + 10*E + N,
    TWENTY #= 100000*T + 10000*W + 1000*E + 100*N + 10*T + Y,
    SEVEN + SEVEN + SIX #= TWENTY,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
