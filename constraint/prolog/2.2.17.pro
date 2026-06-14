% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
% 2.2.17: MACHU + PICCHU = INDIAN

solve(Vars) :-
    Vars = [A, C, D, H, I, M, N, P, U],
    Vars :: 0..9,
    I #\= 0,
    M #\= 0,
    P #\= 0,
    alldifferent(Vars),
    MACHU #= 10000*M + 1000*A + 100*C + 10*H + U,
    INDIAN #= 100000*I + 10000*N + 1000*D + 100*I + 10*A + N,
    PICCHU #= 100000*P + 10000*I + 1000*C + 100*C + 10*H + U,
    MACHU + PICCHU #= INDIAN,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
