% Komentar za studente: promenljive imaju konacne domene; ogranicenja su uslovi zadatka, a labeling pokrece pretragu resenja.
solve(Vars) :-
    Vars = [A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S],
    Vars :: 1..19,
    alldifferent(Vars),
    A+B+C #= 38, D+E+F+G #= 38, H+I+J+K+L #= 38, M+N+O+P #= 38, Q+R+S #= 38,
    A+D+H #= 38, B+E+I+M #= 38, C+F+J+N+Q #= 38, G+K+O+R #= 38, L+P+S #= 38,
    C+G+L #= 38, B+F+K+P #= 38, A+E+J+O+S #= 38, D+I+N+R #= 38, H+M+Q #= 38,
    labeling(Vars),
    writeln(Vars),
    fail.
solve(_).
