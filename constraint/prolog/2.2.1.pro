% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
% 2.2.1: TWO + TWO = FOUR

solve(Vars) :-
    Vars = [F, O, R, T, U, W],
    Vars :: 0..9,
    F #\= 0,
    T #\= 0,
    alldifferent(Vars),
    TWO #= 100*T + 10*W + O,
    FOUR #= 1000*F + 100*O + 10*U + R,
    TWO + TWO #= FOUR,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
