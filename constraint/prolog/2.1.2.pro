% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
candidate(Broj, Kolicnik) :-
    Vars = [A,B,C],
    A :: 1..9,
    B :: 0..9,
    C :: 0..9,
    alldifferent(Vars),
    labeling(Vars),
    Broj is 100*A + 10*B + C,
    Zbir is A + B + C,
    Kolicnik is Broj / Zbir.

solve(Broj) :-
    findall(K-B, candidate(B,K), Pairs),
    keysort(Pairs, [_-Broj|_]),
    writeln(Broj).
