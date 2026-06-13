solve(Vars):- Vars=[E,D], Vars::0..250, E+D#=<250, 100*E+105*D#=<26000, 150*E+170*D#=<51200, labeling([maximize(150*5*E+170*6*D-(100*E+105*D))], Vars), writeln(Vars).
