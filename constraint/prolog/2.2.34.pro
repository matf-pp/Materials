% 2.2.34: SIXTEEN + TWENTY + TWENTY + TEN + TWO + TWO = SEVENTY

solve(Vars) :-
    Vars = [E, I, N, O, S, T, V, W, X, Y],
    Vars :: 0..9,
    S #\= 0,
    T #\= 0,
    alldifferent(Vars),
    TEN #= 100*T + 10*E + N,
    TWO #= 100*T + 10*W + O,
    TWENTY #= 100000*T + 10000*W + 1000*E + 100*N + 10*T + Y,
    SEVENTY #= 1000000*S + 100000*E + 10000*V + 1000*E + 100*N + 10*T + Y,
    SIXTEEN #= 1000000*S + 100000*I + 10000*X + 1000*T + 100*E + 10*E + N,
    SIXTEEN + TWENTY + TWENTY + TEN + TWO + TWO #= SEVENTY,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
