% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
% 2.2.21: NINE + LESS + TWO = SEVEN

solve(Vars) :-
    Vars = [E, I, L, N, O, S, T, V, W],
    Vars :: 0..9,
    L #\= 0,
    N #\= 0,
    S #\= 0,
    T #\= 0,
    alldifferent(Vars),
    TWO #= 100*T + 10*W + O,
    LESS #= 1000*L + 100*E + 10*S + S,
    NINE #= 1000*N + 100*I + 10*N + E,
    SEVEN #= 10000*S + 1000*E + 100*V + 10*E + N,
    NINE + LESS + TWO #= SEVEN,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
