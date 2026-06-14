% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
% 2.2.43: FERMAT * S = LAST + THEOREM

solve(Vars) :-
    Vars = [A, E, F, H, L, M, O, R, S, T],
    Vars :: 0..9,
    F #\= 0,
    L #\= 0,
    T #\= 0,
    alldifferent(Vars),
    S #= S,
    LAST #= 1000*L + 100*A + 10*S + T,
    FERMAT #= 100000*F + 10000*E + 1000*R + 100*M + 10*A + T,
    THEOREM #= 1000000*T + 100000*H + 10000*E + 1000*O + 100*R + 10*E + M,
    FERMAT*S #= LAST + THEOREM,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
