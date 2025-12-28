% suma parnih prirodnih brojeva od 2 do N
% moze se dodati provera N mod 2 =:= 0 u pravilu, ali i bez toga sam prepoznaje za neparne da je netacan upit jer rekurzijom dodje do sumaParnih(1,S) sto je netacna cinjenica u bazi
sumaParnih(2,2).
sumaParnih(N,S):- N>2, N1 is N-2, sumaParnih(N1,S1), S is S1+N. 
