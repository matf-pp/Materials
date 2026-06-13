solve(Vars):- Vars=[A,B,C,D,E,F,G], Vars::1..7, alldifferent(Vars), A+C+D#<11, A+D+B#<11, D+B+E#<11, C+D+F#<11, F+D+G#<11, G+D+E#<11, C+D+B+A#=<16, D+A+B+E#=<16, F+C+D+G#=<16, F+G+D+E#=<16, labeling(Vars), writeln(Vars), fail.
solve(_).
