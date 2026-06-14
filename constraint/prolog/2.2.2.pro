% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
% 2.2.2: SEE + YOU = SOON

solve(Vars) :-
    Vars = [E, N, O, S, U, Y],
    Vars :: 0..9,
    S #\= 0,
    Y #\= 0,
    alldifferent(Vars),
    SEE #= 100*S + 10*E + E,
    YOU #= 100*Y + 10*O + U,
    SOON #= 1000*S + 100*O + 10*O + N,
    SEE + YOU #= SOON,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
