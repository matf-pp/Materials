% 2.2.22: ONE + THREE + FOUR = EIGHT

solve(Vars) :-
    Vars = [E, F, G, H, I, N, O, R, T, U],
    Vars :: 0..9,
    E #\= 0,
    F #\= 0,
    O #\= 0,
    T #\= 0,
    alldifferent(Vars),
    ONE #= 100*O + 10*N + E,
    FOUR #= 1000*F + 100*O + 10*U + R,
    EIGHT #= 10000*E + 1000*I + 100*G + 10*H + T,
    THREE #= 10000*T + 1000*H + 100*R + 10*E + E,
    ONE + THREE + FOUR #= EIGHT,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
