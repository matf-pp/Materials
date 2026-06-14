% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
solve(Vars):- Vars=[A,B,C,D], A::0..30, B::0..66, C::0..300, D::0..120, 100*A+45*B+10*C+25*D#=<3000, 20*A+14*B+6*C+9*D#=<2000, 80*A+68*B+40*C+30*D#=<3000, labeling([maximize(100*A+80*B+45*C+35*D)], Vars), writeln(Vars).
