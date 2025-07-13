% Resenje problema ranca za broj instanci predmeta.

get([G|R], 0, G) :- !.
get([G|R], I, X) :-
    I1 is I-1,
    get(R, I1, X).

ranac(Vars, Brojaci, Tezine, Vrednosti, Kapacitet) :-
    Vars = [X1, X2, X3, X4],
    Vars :: 0..100,

    % Izvlacimo vrednosti iz listi
    get(Brojaci, 0, C1),
    get(Brojaci, 1, C2),
    get(Brojaci, 2, C3),
    get(Brojaci, 3, C4),
    get(Vrednosti, 0, V1),
    get(Vrednosti, 1, V2),
    get(Vrednosti, 2, V3),
    get(Vrednosti, 3, V4),
    get(Tezine, 0, W1),
    get(Tezine, 1, W2),
    get(Tezine, 2, W3),
    get(Tezine, 3, W4),

    % Ogranicenje za kapacitet ranca
    X1*W1 + X2*W2 + X3*W3 + X4*W4 #=< Kapacitet,

    % Ogranicenje za broj predmeta
    X1 #=< C1,
    X2 #=< C2,
    X3 #=< C3,
    X4 #=< C4,

    % Maksimizujemo vrednost predmeta koje uzimamo
    labeling([maximize(X1*V1 + X2*V2 + X3*V3 + X4*V4)], Vars),

    Zarada is X1*V1 + X2*V2 + X3*V3 + X4*V4,
    Tezina is X1*W1 + X2*W2 + X3*W3 + X4*W4,
    write('Zarada '), write(Zarada), nl,
    write('Te