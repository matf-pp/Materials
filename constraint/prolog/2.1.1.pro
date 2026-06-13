solve(Vars) :-
    Vars = [X,Y,Z],
    X :: 1..3,
    Y :: 1..3,
    Z :: 1..3,
    Y #= Z,
    labeling(Vars),
    write_symbol(X), write(' '), write(Y), write(' 0.'), writeln(Z),
    fail.
solve(_).

write_symbol(1) :- write(a).
write_symbol(2) :- write(b).
write_symbol(3) :- write(c).
