% 2.2.25: ONE + ONE + ONE + THREE + THREE + ELEVEN = TWENTY

solve(Vars) :-
    Vars = [E, H, L, N, O, R, T, V, W, Y],
    Vars :: 0..9,
    E #\= 0,
    O #\= 0,
    T #\= 0,
    alldifferent(Vars),
    ONE #= 100*O + 10*N + E,
    THREE #= 10000*T + 1000*H + 100*R + 10*E + E,
    ELEVEN #= 100000*E + 10000*L + 1000*E + 100*V + 10*E + N,
    TWENTY #= 100000*T + 10000*W + 1000*E + 100*N + 10*T + Y,
    ONE + ONE + ONE + THREE + THREE + ELEVEN #= TWENTY,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
