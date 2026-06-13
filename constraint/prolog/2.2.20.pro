% 2.2.20: THREE + THREE + ONE = SEVEN

solve(Vars) :-
    Vars = [E, H, N, O, R, S, T, V],
    Vars :: 0..9,
    O #\= 0,
    S #\= 0,
    T #\= 0,
    alldifferent(Vars),
    ONE #= 100*O + 10*N + E,
    SEVEN #= 10000*S + 1000*E + 100*V + 10*E + N,
    THREE #= 10000*T + 1000*H + 100*R + 10*E + E,
    THREE + THREE + ONE #= SEVEN,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
