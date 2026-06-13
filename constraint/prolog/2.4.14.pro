get([H|_],0,H):-!.
get([_|T],I,X):- I1 is I-1, get(T,I1,X).
solve(Vars):- Vars=[X1,X2,X3,X4], X1::0..2, X2::0..3, X3::0..5, X4::0..4, 500*X1+7*X2+55*X3+X4#=<128, labeling([maximize(3400*X1+1800*X2+200*X3+10*X4)], Vars), writeln(Vars).
ranac(Vars,_,_,_,_):- solve(Vars).
