% 2.2.15: HERE + THEY + GO = AGAIN

solve(Vars) :-
    Vars = [A, E, G, H, I, N, O, R, T, Y],
    Vars :: 0..9,
    A #\= 0,
    G #\= 0,
    H #\= 0,
    T #\= 0,
    alldifferent(Vars),
    GO #= 10*G + O,
    HERE #= 1000*H + 100*E + 10*R + E,
    THEY #= 1000*T + 100*H + 10*E + Y,
    AGAIN #= 10000*A + 1000*G + 100*A + 10*I + N,
    HERE + THEY + GO #= AGAIN,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
