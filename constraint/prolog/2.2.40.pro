% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
% 2.2.40: ONE + NINE + TWENTY + THIRTY + THIRTY = NINETY

solve(Vars) :-
    Vars = [E, H, I, N, O, R, T, W, Y],
    Vars :: 0..9,
    N #\= 0,
    O #\= 0,
    T #\= 0,
    alldifferent(Vars),
    ONE #= 100*O + 10*N + E,
    NINE #= 1000*N + 100*I + 10*N + E,
    NINETY #= 100000*N + 10000*I + 1000*N + 100*E + 10*T + Y,
    THIRTY #= 100000*T + 10000*H + 1000*I + 100*R + 10*T + Y,
    TWENTY #= 100000*T + 10000*W + 1000*E + 100*N + 10*T + Y,
    ONE + NINE + TWENTY + THIRTY + THIRTY #= NINETY,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
