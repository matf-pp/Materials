% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
% 2.2.27: ELEVEN + NINE + FIVE + FIVE = THIRTY

solve(Vars) :-
    Vars = [E, F, H, I, L, N, R, T, V, Y],
    Vars :: 0..9,
    E #\= 0,
    F #\= 0,
    N #\= 0,
    T #\= 0,
    alldifferent(Vars),
    FIVE #= 1000*F + 100*I + 10*V + E,
    NINE #= 1000*N + 100*I + 10*N + E,
    ELEVEN #= 100000*E + 10000*L + 1000*E + 100*V + 10*E + N,
    THIRTY #= 100000*T + 10000*H + 1000*I + 100*R + 10*T + Y,
    ELEVEN + NINE + FIVE + FIVE #= THIRTY,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
