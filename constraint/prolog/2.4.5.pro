solve(Vars):- Vars=[R,S], R::30..70, S::0..125, 2*R#>=3*S, 360*R+240*S#=<30000, 200*R+60*S#=<14000, labeling([maximize(200*R+80*S)], Vars), writeln(Vars).
