% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
% 2.2.44: WINNIE / THE = POOH

solve(Vars) :-
    Vars = [E, H, I, N, O, P, T, W],
    Vars :: 0..9,
    P #\= 0,
    T #\= 0,
    W #\= 0,
    alldifferent(Vars),
    THE #= 100*T + 10*H + E,
    POOH #= 1000*P + 100*O + 10*O + H,
    WINNIE #= 100000*W + 10000*I + 1000*N + 100*N + 10*I + E,
    THE*POOH #= WINNIE,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
