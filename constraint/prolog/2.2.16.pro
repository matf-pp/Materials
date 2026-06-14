% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
% 2.2.16: OSAKA + HAIKU + SUSHI = JAPAN

solve(Vars) :-
    Vars = [A, H, I, J, K, N, O, P, S, U],
    Vars :: 0..9,
    H #\= 0,
    J #\= 0,
    O #\= 0,
    S #\= 0,
    alldifferent(Vars),
    HAIKU #= 10000*H + 1000*A + 100*I + 10*K + U,
    JAPAN #= 10000*J + 1000*A + 100*P + 10*A + N,
    OSAKA #= 10000*O + 1000*S + 100*A + 10*K + A,
    SUSHI #= 10000*S + 1000*U + 100*S + 10*H + I,
    OSAKA + HAIKU + SUSHI #= JAPAN,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
