% 2.2.45: TWO * TWO + EIGHT = TWELVE

solve(Vars) :-
    Vars = [E, G, H, I, L, O, T, V, W],
    Vars :: 0..9,
    E #\= 0,
    T #\= 0,
    alldifferent(Vars),
    TWO #= 100*T + 10*W + O,
    EIGHT #= 10000*E + 1000*I + 100*G + 10*H + T,
    TWELVE #= 100000*T + 10000*W + 1000*E + 100*L + 10*V + E,
    TWO*TWO + EIGHT #= TWELVE,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
