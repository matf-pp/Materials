solve(Vars):- Vars=[X1,X2], Vars::0..9, 2*X1+3*X2#<23, 45*X1-34*X2#<12, labeling([maximize(X1*X1+X2*X2)], Vars), writeln(Vars).
