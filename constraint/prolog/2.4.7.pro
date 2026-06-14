% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
% 2.4.7: MANET + MATISSE + MIRO + MONET + RENOIR = ARTISTS

solve(Vars) :-
    Vars = [A, E, I, M, N, O, R, S, T],
    Vars :: 0..9,
    A #\= 0,
    M #\= 0,
    R #\= 0,
    alldifferent(Vars),
    MIRO #= 1000*M + 100*I + 10*R + O,
    MANET #= 10000*M + 1000*A + 100*N + 10*E + T,
    MONET #= 10000*M + 1000*O + 100*N + 10*E + T,
    RENOIR #= 100000*R + 10000*E + 1000*N + 100*O + 10*I + R,
    ARTISTS #= 1000000*A + 100000*R + 10000*T + 1000*I + 100*S + 10*T + S,
    MATISSE #= 1000000*M + 100000*A + 10000*T + 1000*I + 100*S + 10*S + E,
    MANET + MATISSE + MIRO + MONET + RENOIR #= ARTISTS,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
