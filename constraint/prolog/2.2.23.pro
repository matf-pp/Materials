% 2.2.23: THREE + THREE + TWO + TWO + ONE = ELEVEN

solve(Vars) :-
    Vars = [E, H, L, N, O, R, T, V, W],
    Vars :: 0..9,
    E #\= 0,
    O #\= 0,
    T #\= 0,
    alldifferent(Vars),
    ONE #= 100*O + 10*N + E,
    TWO #= 100*T + 10*W + O,
    THREE #= 10000*T + 1000*H + 100*R + 10*E + E,
    ELEVEN #= 100000*E + 10000*L + 1000*E + 100*V + 10*E + N,
    THREE + THREE + TWO + TWO + ONE #= ELEVEN,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
