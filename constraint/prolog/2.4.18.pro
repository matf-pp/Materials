% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
solve(Vars):- Vars=[D17,R35,Z,N], D17::0..4, R35::0..31, Z::0..14, N::0..3, 800*D17+1300*R35+120*Z+9000*N#=<20000, 480*D17+3980*R35+290*Z+6600*N#=<20000, 8400*D17+1728*R35+600*Z+273900*N#=<350000, labeling([maximize(930*D17+990*R35+217*Z+30350*N)], Vars), writeln(Vars).
