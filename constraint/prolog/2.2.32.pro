% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
% 2.2.32: NINETEEN + THIRTEEN + THREE + TWO + TWO + ONE + ONE + ONE = FORTYTWO

solve(Vars) :-
    Vars = [E, F, H, I, N, O, R, T, W, Y],
    Vars :: 0..9,
    F #\= 0,
    N #\= 0,
    O #\= 0,
    T #\= 0,
    alldifferent(Vars),
    ONE #= 100*O + 10*N + E,
    TWO #= 100*T + 10*W + O,
    THREE #= 10000*T + 1000*H + 100*R + 10*E + E,
    FORTYTWO #= 10000000*F + 1000000*O + 100000*R + 10000*T + 1000*Y + 100*T + 10*W + O,
    NINETEEN #= 10000000*N + 1000000*I + 100000*N + 10000*E + 1000*T + 100*E + 10*E + N,
    THIRTEEN #= 10000000*T + 1000000*H + 100000*I + 10000*R + 1000*T + 100*E + 10*E + N,
    NINETEEN + THIRTEEN + THREE + TWO + TWO + ONE + ONE + ONE #= FORTYTWO,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
