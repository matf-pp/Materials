% 2.2.30: TEN + TEN + NINE + EIGHT + THREE = FORTY

solve(Vars) :-
    Vars = [E, F, G, H, I, N, O, R, T, Y],
    Vars :: 0..9,
    E #\= 0,
    F #\= 0,
    N #\= 0,
    T #\= 0,
    alldifferent(Vars),
    TEN #= 100*T + 10*E + N,
    NINE #= 1000*N + 100*I + 10*N + E,
    EIGHT #= 10000*E + 1000*I + 100*G + 10*H + T,
    FORTY #= 10000*F + 1000*O + 100*R + 10*T + Y,
    THREE #= 10000*T + 1000*H + 100*R + 10*E + E,
    TEN + TEN + NINE + EIGHT + THREE #= FORTY,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
