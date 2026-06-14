% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
% 2.2.39: SIXTY + EIGHT + THREE + NINE + TEN = NINETY

solve(Vars) :-
    Vars = [E, G, H, I, N, R, S, T, X, Y],
    Vars :: 0..9,
    E #\= 0,
    N #\= 0,
    S #\= 0,
    T #\= 0,
    alldifferent(Vars),
    TEN #= 100*T + 10*E + N,
    NINE #= 1000*N + 100*I + 10*N + E,
    EIGHT #= 10000*E + 1000*I + 100*G + 10*H + T,
    SIXTY #= 10000*S + 1000*I + 100*X + 10*T + Y,
    THREE #= 10000*T + 1000*H + 100*R + 10*E + E,
    NINETY #= 100000*N + 10000*I + 1000*N + 100*E + 10*T + Y,
    SIXTY + EIGHT + THREE + NINE + TEN #= NINETY,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
