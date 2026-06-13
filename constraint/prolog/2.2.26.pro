% 2.2.26: EIGHT + EIGHT + TWO + ONE + ONE = TWENTY

solve(Vars) :-
    Vars = [E, G, H, I, N, O, T, W, Y],
    Vars :: 0..9,
    E #\= 0,
    O #\= 0,
    T #\= 0,
    alldifferent(Vars),
    ONE #= 100*O + 10*N + E,
    TWO #= 100*T + 10*W + O,
    EIGHT #= 10000*E + 1000*I + 100*G + 10*H + T,
    TWENTY #= 100000*T + 10000*W + 1000*E + 100*N + 10*T + Y,
    EIGHT + EIGHT + TWO + ONE + ONE #= TWENTY,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
