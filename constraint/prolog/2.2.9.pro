% 2.2.9: FATHER + MOTHER = PARENT

solve(Vars) :-
    Vars = [A, E, F, H, M, N, O, P, R, T],
    Vars :: 0..9,
    F #\= 0,
    M #\= 0,
    P #\= 0,
    alldifferent(Vars),
    FATHER #= 100000*F + 10000*A + 1000*T + 100*H + 10*E + R,
    MOTHER #= 100000*M + 10000*O + 1000*T + 100*H + 10*E + R,
    PARENT #= 100000*P + 10000*A + 1000*R + 100*E + 10*N + T,
    FATHER + MOTHER #= PARENT,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
