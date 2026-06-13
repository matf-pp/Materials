% 2.2.18: SHE + KNOWS + HOW + IT = WORKS

solve(Vars) :-
    Vars = [E, H, I, K, N, O, R, S, T, W],
    Vars :: 0..9,
    H #\= 0,
    I #\= 0,
    K #\= 0,
    S #\= 0,
    W #\= 0,
    alldifferent(Vars),
    IT #= 10*I + T,
    HOW #= 100*H + 10*O + W,
    SHE #= 100*S + 10*H + E,
    KNOWS #= 10000*K + 1000*N + 100*O + 10*W + S,
    WORKS #= 10000*W + 1000*O + 100*R + 10*K + S,
    SHE + KNOWS + HOW + IT #= WORKS,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
