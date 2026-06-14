% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
solve(Vars) :-
    Vars = [A,B,C,D,E,F,G,H],
    Vars :: 1..8,
    alldifferent(Vars),
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
