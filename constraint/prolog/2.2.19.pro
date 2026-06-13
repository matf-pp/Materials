% 2.2.19: COPY + PASTE + SAVE = TOOLS

solve(Vars) :-
    Vars = [A, C, E, L, O, P, S, T, V, Y],
    Vars :: 0..9,
    C #\= 0,
    P #\= 0,
    S #\= 0,
    T #\= 0,
    alldifferent(Vars),
    COPY #= 1000*C + 100*O + 10*P + Y,
    SAVE #= 1000*S + 100*A + 10*V + E,
    PASTE #= 10000*P + 1000*A + 100*S + 10*T + E,
    TOOLS #= 10000*T + 1000*O + 100*O + 10*L + S,
    COPY + PASTE + SAVE #= TOOLS,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
