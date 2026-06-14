% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
solve(Vars) :-
    Vars = [X,Y,Z,W],
    X :: 1..110,
    Y :: 1..2..51,
    Z :: 10..10..100,
    W :: [1,8,27,64,125,216,343,512,729,1000],
    X #>= 2*W,
    3 + Y #=< Z,
    X - 11*W + Y + 11*Z #=< 100,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
