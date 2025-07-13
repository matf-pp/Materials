resetka :-
    Vars = [X11, X12, X13, X21, X22, X23, X31,  X32, X33],
    [X11, X13, X22, X31, X33] :: 1..2..9,
    [X12, X21, X23, X32] :: 2..2..9,

    alldifferent(Vars), 
    labeling(Vars),

    write(X13), write(' '), write(X23), write(' '), write(X33), nl,
    write(X12), write(' '), write(X22), write(' '), write(X32), nl,
    write(X11), write(' '), write(X21), write(' '), write(X31), nl, nl.
