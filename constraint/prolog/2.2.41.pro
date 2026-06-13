% 2.2.41: MEN * AND = WOMEN

solve(Vars) :-
    Vars = [A, D, E, M, N, O, W],
    Vars :: 0..9,
    A #\= 0,
    M #\= 0,
    W #\= 0,
    alldifferent(Vars),
    AND #= 100*A + 10*N + D,
    MEN #= 100*M + 10*E + N,
    WOMEN #= 10000*W + 1000*O + 100*M + 10*E + N,
    MEN*AND #= WOMEN,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
