% 2.2.4: NO + GUN + NO = HUNT

solve(Vars) :-
    Vars = [G, H, N, O, T, U],
    Vars :: 0..9,
    G #\= 0,
    H #\= 0,
    N #\= 0,
    alldifferent(Vars),
    NO #= 10*N + O,
    GUN #= 100*G + 10*U + N,
    HUNT #= 1000*H + 100*U + 10*N + T,
    NO + GUN + NO #= HUNT,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
