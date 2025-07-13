% Mnogi problemi u racunarstva, poput socijalnih mreza, kompajlerskih internih reprezentacija, struktura podataka, modela neuronskih mreza itd. mogu se modelovati grafovima. 
% Stoga su grafovi jako vazni. 
% Problem bojenja grafa izucava se od 70tih godina proslog veka. Zbog eksponencijalne slozenosti jedan je od tezih problema kog resavamo mnogim heuristikama. 
% 
% Milan pokusava da resi problem bojenja grafa koristeci logicko programiranje i programski jezik Prolog. 
% Potrebno je obojiti cvorove grafa u 3 boje (ukoliko je to moguce) tako da susedni cvorovi ne budu bojeni istim bojama. 
% Napisati predikat painter(Vars) koji pronalazi sva validna bojenja zadatog grafa (za boje koristiti 0-crvena, 1-plava, 2-bela).

% Resenje problema bojenja grafa
painter(Vars) :-
    % X1
    % X2
    % X3
    % X4
    % X5
    % X6
    % X7
    Vars = [X1, X2, X3, X4, X5, X6, X7],
    Vars :: 0..2,

    % Ogranicenje za susede
    X1 #\= X4,
    X1 #\= X7,
    X2 #\= X3,
    X2 #\= X4,
    X2 #\= X7,
    X3 #\= X4, 
    X3 #\= X5,
    X4 #\= X5,
    X5 #\= X6,
    X6 #\= X7,
    
    % Izolovani cvor mora biti crven, za kaznu!
    X7 #= 0,

    % Treci cvor ne sme biti plav
    X3 #\= 1,

    % Cvor koji ima 4 suseda ne sme biti plav
    X4 #\= 1, 
    
    labeling(Vars),
    print(Vars), nl.
