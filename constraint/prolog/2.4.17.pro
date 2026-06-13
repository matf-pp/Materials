solve(Vars):- Vars=[W,K,T,D], Vars::0..20, 100*W+45*K+10*T+25*D#=<3000, 3*W+10*K+15*T+20*D#=<1000, 8*W+6*K+14*T+11*D#=<300, labeling([maximize(5*W+11*K+20*T+15*D)], Vars), writeln(Vars).
