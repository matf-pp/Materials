% ispis cifara unetog prirodnog broja N
cifra(0, nula).
cifra(1, jedan).
cifra(2, dva).
cifra(3, tri).
cifra(4, cetiri).
cifra(5, pet).
cifra(6, sest).
cifra(7, sedam).
cifra(8, osam).
cifra(9, devet).

% ukoliko nije prirodan broj, cut operatorom sprecavamo poziv poslednjeg predikata
cifre(N):- N < 1, !.

% ukoliko je jednocifren svodi se na poziv predikata cifra
% write(t) gde je t neki term, ispisuje term
% nl (newline) - ispisuje se novi red
% obratiti paznju na upotrebu cut operatora ! - sprecavamo poziv poslednjeg predikata za jednocifrene
cifre(N):- N > 1, N < 10, cifra(N, C), write(C), nl, !.

% ukoliko nije jednocifren, racunamo tekucu cifru koju ispisujemo i ostatak broja za koji se ponovo poziva predikat                
cifre(N):- N1 is (N // 10), cifre(N1), N2 is (N mod 10), cifra(N2, C), write(C), nl. 
