% 2.2.33: FORTY + TEN + TEN = SIXTY

solve(Vars) :-
    Vars = [E, F, I, N, O, R, S, T, X, Y],
    Vars :: 0..9,
    F #\= 0,
    S #\= 0,
    T #\= 0,
    alldifferent(Vars),
    TEN #= 100*T + 10*E + N,
    FORTY #= 10000*F + 1000*O + 100*R + 10*T + Y,
    SIXTY #= 10000*S + 1000*I + 100*X + 10*T + Y,
    FORTY + TEN + TEN #= SIXTY,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
