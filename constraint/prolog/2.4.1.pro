solve(Vars):- Vars=[H,K], H::0..120, K::0..167, 10*H+12*K#=<1200, 300*H+120*K#=<20000, labeling([maximize(7*H+9*K)], Vars), writeln(Vars).
