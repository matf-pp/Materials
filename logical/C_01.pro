% Kompanija ParadigmeSolutions doo vrsi usluge programiranja aplikacija po zeljama korisnika. Stoga podrzavaju sirok spektar jezika i tehnologija. 
% Cetiri Tim Leada ove kompanije Vukasin, Lazar, Nemanja i Milica ne samo da programiraju u razlicitim jezicima vec koriste i razlicite editore. 
% Za svakog team lida potrebno je otkriti ime, prezime, omiljeni jezik i omiljeni editor. 
% Poznato je: 
% Lazar programira u Microemacsu
% Marković pak bira Intelij
% Miličino prezime nije Milic
% Neko programira u Notepadu!
% Onaj ko programira u prologu, programira ga u Geditu.
% Milica mrzi Prolog
% Vukasinovo prezime je Pavlovic
% Nemanjino prezime nije Torbica
% Nemanja programira u jeziku java
% Programer koji programira u Cu se ne preziva Torbica
% Lazar ne zna jezik Go


% 			
% Ime	    Prezime	        Jezik	        Okruzenje
% ----------------------------------------------------
% Vukasin	Pavlovic	    Prolog	        Gedit
% Lazar	    Milic	        C	            Microemacs
% Nemanja	Markovic	    Java     	    Intelij
% Milica	Torbica     	Go	            Notepad


clan(X, [X|_]).
clan(X, [_|R]) :- clan(X, R).

friends(X) :-
    X = [[vukasin, pavlovic, _, _], [lazar, _, _, microemacs], [nemanja, _, _, _], _],

    % Lazar programira u Microemacsu
    clan([lazar, _, _, microemacs], X),

    % Marković pak bira Intelij
    clan([_, markovic, _, intelij], X),

    % Milicino prezime nije Milic
    clan([milica, _, _, _], X),
    clan([_, milic, _, _], X),

    % Neko programira u Notepadu
    clan([_, _, _, notepad], X),

    % Onaj ko programira u prologu, programira ga u Geditu.
    clan([_, _, prolog, gedit], X),

    
    % Vukasinovo prezime je Pavlovic
    clan([vukasin, pavlovic, _, _], X),

    % Nemanjino prezime nije Torbica
    clan([_, torbica, _, _], X),

    % Nemanja programira u jeziku java
    clan([nemanja, _, java, _], X),

    % Programer koji programira u Cu se ne preziva Torbica
    clan([_, _, c, _], X),

    % Lazar ne zna jezik Go
    clan([_, _, go, _], X),

    %% Negacije

    % Miličino prezime nije Milic
    \+(clan([milica, milic, _, _], X)),
    % Milica mrzi Prolog
    \+(clan([milica, _, prolog, _], X)),
    % Nemanjino prezime nije Torbica
    \+(clan([nemanja, torbica, _, _], X)),
    % Programer koji programira u Cu se ne preziva Torbica
    \+(clan([_, torbica, c, _], X)),
    % Lazar ne zna jezik Go
    \+(clan([lazar, _, go, _], X)).
    
