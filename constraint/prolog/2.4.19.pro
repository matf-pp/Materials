% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
solve(Vars):- Vars=[X1,X2,X3,X4,X5,X6,X7], Vars::0..2, X1#\=X4, X1#\=X7, X2#\=X3, X2#\=X4, X2#\=X7, X3#\=X4, X3#\=X5, X4#\=X5, X5#\=X6, X6#\=X7, X7#=0, X3#\=1, X4#\=1, labeling(Vars), writeln(Vars), fail.
solve(_).
