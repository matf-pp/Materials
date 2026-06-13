solve(Vars) :-
    Vars = [X,Y,Z],
    X :: 1..90,
    Y :: 2..2..60,
    Z :: [1,4,9,16,25,36,49,64,81,100],
    X #>= Z,
    2*X + Y*X + Z #=< 34,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
