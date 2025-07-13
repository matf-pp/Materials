% Resenje problema Hyper-6

hyper6(Vars) :-
    write('Welcome to Hyper 6'), nl,
    write('  '), write('A'), write('  '), write('B'), nl,
    write('C'), write('   '), write('D'), write('   '), write('E'), nl, 
    write('  '), write('F'), write('  '), write('G'), nl,
    Vars = [A, B, C, D, E, F, G],
    Vars :: 1..7,
    Border is 15,

    % Razliciti
    alldifferent(Vars),

    % Ogranicenje za susede
    A + B + E + D #=<Border,
    B + E + G + D #=<Border,
    E + G + F + D #=<Border,
    G + F + C + D #=<Border,
    F + C + A + D #=<Border,
    C + A + B + D #=<Border,
    
    labeling([maximize(C+D+E), minimize(C*D*E)], Vars),
    print(Vars), nl,
    write('  '), write(A), write('  '), write(B), nl,
    write(C), write('   '), write(D), write('   '), write(E), nl, 
    write('  '), write(F), write('  '), write(G), nl, nl.