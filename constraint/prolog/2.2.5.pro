% 2.2.5: SIX + SIX + SIX = NINE + NINE

solve(Vars) :-
    Vars = [E, I, N, S, X],
    Vars :: 0..9,
    N #\= 0,
    S #\= 0,
    alldifferent(Vars),
    SIX #= 100*S + 10*I + X,
    NINE #= 1000*N + 100*I + 10*N + E,
    SIX + SIX + SIX #= NINE + NINE,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
