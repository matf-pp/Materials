% 2.2.36: TWENTY + TWENTY + THIRTY = SEVENTY

solve(Vars) :-
    Vars = [E, H, I, N, R, S, T, V, W, Y],
    Vars :: 0..9,
    S #\= 0,
    T #\= 0,
    alldifferent(Vars),
    THIRTY #= 100000*T + 10000*H + 1000*I + 100*R + 10*T + Y,
    TWENTY #= 100000*T + 10000*W + 1000*E + 100*N + 10*T + Y,
    SEVENTY #= 1000000*S + 100000*E + 10000*V + 1000*E + 100*N + 10*T + Y,
    TWENTY + TWENTY + THIRTY #= SEVENTY,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
