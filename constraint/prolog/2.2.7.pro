% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
% 2.2.7: THIS + IS + VERY = EASY

solve(Vars) :-
    Vars = [A, E, H, I, R, S, T, V, Y],
    Vars :: 0..9,
    E #\= 0,
    I #\= 0,
    T #\= 0,
    V #\= 0,
    alldifferent(Vars),
    IS #= 10*I + S,
    EASY #= 1000*E + 100*A + 10*S + Y,
    THIS #= 1000*T + 100*H + 10*I + S,
    VERY #= 1000*V + 100*E + 10*R + Y,
    THIS + IS + VERY #= EASY,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
