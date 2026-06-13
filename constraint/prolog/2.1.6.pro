solve(N, S) :-
    length(S, N),
    S :: 0..N-1,
    foreach(I in 0..N-1, sum([(S[J] #= I) : J in 1..N]) #= S[I+1]),
    labeling(S),
    writeln(S),
    fail.
solve(_, _).
