% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
solve(Vars):- Vars=[X11,X12,X13,X21,X22,X23,X31,X32,X33], [X11,X13,X22,X31,X33]::[1,3,5,7,9], [X12,X21,X23,X32]::[2,4,6,8], alldifferent(Vars), labeling(Vars), writeln(Vars).
