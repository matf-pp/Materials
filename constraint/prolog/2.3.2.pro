% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
solve(Vars) :-
    Vars = [A,B,C,D,E,G,F,H,I],
    Vars :: 1..9,
    alldifferent(Vars),
    A #< B, B #< C, C #< D, D #< E,
    G #< F, F #< C, C #< H, H #< I,
    A+B+C+D+E #= 25,
    G+F+C+H+I #= 25,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
