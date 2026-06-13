% 2.2.12: SATURN + URANUS + NEPTUNE + PLUTO = PLANETS

solve(Vars) :-
    Vars = [A, E, L, N, O, P, R, S, T, U],
    Vars :: 0..9,
    N #\= 0,
    P #\= 0,
    S #\= 0,
    U #\= 0,
    alldifferent(Vars),
    PLUTO #= 10000*P + 1000*L + 100*U + 10*T + O,
    SATURN #= 100000*S + 10000*A + 1000*T + 100*U + 10*R + N,
    URANUS #= 100000*U + 10000*R + 1000*A + 100*N + 10*U + S,
    NEPTUNE #= 1000000*N + 100000*E + 10000*P + 1000*T + 100*U + 10*N + E,
    PLANETS #= 1000000*P + 100000*L + 10000*A + 1000*N + 100*E + 10*T + S,
    SATURN + URANUS + NEPTUNE + PLUTO #= PLANETS,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
