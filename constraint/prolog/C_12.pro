ivan :-
  [W,P,C] :: 0..35,
  4*W+3*P+2*C #=< 35,
  maxof(labeling([W,P,C]),15*W+10*P+7*C),
  write('Vino: '), writeln(W),
  write('Parfem: '), writeln(P),
  write('Casa: '), write(C).
  
