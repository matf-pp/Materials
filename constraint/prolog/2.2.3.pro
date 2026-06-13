% 2.2.3: GREEN + ORANGE = COLORS

solve(Vars) :-
    Vars = [A, C, E, G, L, N, O, R, S],
    Vars :: 0..9,
    C #\= 0,
    G #\= 0,
    O #\= 0,
    alldifferent(Vars),
    GREEN #= 10000*G + 1000*R + 100*E + 10*E + N,
    COLORS #= 100000*C + 10000*O + 1000*L + 100*O + 10*R + S,
    ORANGE #= 100000*O + 10000*R + 1000*A + 100*N + 10*G + E,
    GREEN + ORANGE #= COLORS,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
