solve(Vars):- Vars=[V,P,C], Vars::0..9, 4*V+3*P+2*C#=<35, labeling([maximize(15*V+10*P+7*C)], Vars), writeln(Vars).
