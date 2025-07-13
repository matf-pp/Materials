% Resenje problema Hyper-6

hyper6(Vars) :-
    write('Welcome to Hyper 6'), nl,
    write('  '), write('A'), write('  '), write('B'), nl,
    write('C'), write('   '), write('D'), write('   '), write('E'), nl, 
    write('  '), write('F'), write('  '), write('G'), nl,
    Vars = [A, B, C, D, E, F, G],
    Vars :: 1..7,
    Border is 11,
    Border2 is 16,

    % Razliciti
    alldifferent(Vars),

    % Ogranicenje za trouglove
    A + C + D #=< Border,
    A + D + B #=< Border,
    D + B + E #=< Border,
    C + D + F #=< Border,
    F + D + G #=< Border,
    G + D + E #=< Border,

    % Ogranicenje za rombove
    C + D + B + A #=<Border2,
    D + A + B + E #=<Border2,
    F + C + D + G #=<Border2,
    F + G + D + E #=<Border2,
    
    labeling(Vars),
    print(Vars), nl,
    write('  '), write(A), write('  '), write(B), nl,
    write(C), write('   '), write(D), write('   '), write(E), nl, 
    write('  '), write(F), write('  '), write(G), nl, nl.