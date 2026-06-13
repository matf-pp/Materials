solve(Vars) :-
    Vars = [A,B,C,D,E],
    A :: 0..50,
    B :: 0..25,
    C :: 0..10,
    D :: 0..5,
    E :: 0..2,
    A + 2*B + 5*C + 10*D + 20*E #= 50,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
