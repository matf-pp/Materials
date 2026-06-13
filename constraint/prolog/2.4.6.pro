% 2.4.6: ((JE + PENSE) - DONC) + JE = SUIS

solve(Vars) :-
    Vars = [C, D, E, I, J, N, O, P, S, U],
    Vars :: 0..9,
    D #\= 0,
    J #\= 0,
    P #\= 0,
    S #\= 0,
    alldifferent(Vars),
    JE #= 10*J + E,
    DONC #= 1000*D + 100*O + 10*N + C,
    SUIS #= 1000*S + 100*U + 10*I + S,
    PENSE #= 10000*P + 1000*E + 100*N + 10*S + E,
    JE + PENSE - DONC + JE #= SUIS,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
