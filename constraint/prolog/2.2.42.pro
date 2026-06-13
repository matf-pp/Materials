% 2.2.42: COGITO = ERGO * SUM

solve(Vars) :-
    Vars = [C, E, G, I, M, O, R, S, T, U],
    Vars :: 0..9,
    C #\= 0,
    E #\= 0,
    S #\= 0,
    alldifferent(Vars),
    SUM #= 100*S + 10*U + M,
    ERGO #= 1000*E + 100*R + 10*G + O,
    COGITO #= 100000*C + 10000*O + 1000*G + 100*I + 10*T + O,
    ERGO*SUM #= COGITO,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
