% 2.2.37: FIFTY + EIGHT + EIGHT + TEN + TWO + TWO = EIGHTY

solve(Vars) :-
    Vars = [E, F, G, H, I, N, O, T, W, Y],
    Vars :: 0..9,
    E #\= 0,
    F #\= 0,
    T #\= 0,
    alldifferent(Vars),
    TEN #= 100*T + 10*E + N,
    TWO #= 100*T + 10*W + O,
    EIGHT #= 10000*E + 1000*I + 100*G + 10*H + T,
    FIFTY #= 10000*F + 1000*I + 100*F + 10*T + Y,
    EIGHTY #= 100000*E + 10000*I + 1000*G + 100*H + 10*T + Y,
    FIFTY + EIGHT + EIGHT + TEN + TWO + TWO #= EIGHTY,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
