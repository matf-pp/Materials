% 2.2.13: WHEN + IN + ROME + BE + A = ROMAN

solve(Vars) :-
    Vars = [A, B, E, H, I, M, N, O, R, W],
    Vars :: 0..9,
    B #\= 0,
    I #\= 0,
    R #\= 0,
    W #\= 0,
    alldifferent(Vars),
    A #= A,
    BE #= 10*B + E,
    IN #= 10*I + N,
    ROME #= 1000*R + 100*O + 10*M + E,
    WHEN #= 1000*W + 100*H + 10*E + N,
    ROMAN #= 10000*R + 1000*O + 100*M + 10*A + N,
    WHEN + IN + ROME + BE + A #= ROMAN,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
