solve(Vars):- Vars=[K,Z], Vars::0..18, K+Z#=18, 2*K+4*Z#=56, labeling(Vars), writeln(Vars).
