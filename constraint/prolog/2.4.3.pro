% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
solve(Vars):- Vars=[S,K], S::0..40, K::0..26, 2*S+3*K#=<80, 120*S+100*K#=<5000, labeling([maximize(1000*S+1500*K)], Vars), writeln(Vars).
