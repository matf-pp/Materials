appendQ([],Ys,Ys).
appendQ([X|Xs],Ys,[X|Zs]) :- appendQ(Xs,Ys,Zs).

% | ?- appendQ([1,2,3], [4,5,6], R)
% R = [1,2,3,4,5,6]

partitionQ(X, [], [], []).
partitionQ(X, [Y|Ytail], [Y|Small], Large) :- X>Y, partitionQ(X, Ytail, Small, Large), !.
partitionQ(X, [Y|Ytail], Small, [Y|Large]) :- X=<Y, partitionQ(X, Ytail, Small, Large).

% | ?- partitionQ(5, [1, 2, 9, 8, 1, 6, 4, 3, 10, 0], L, R)   
% L = [1,2,1,4,3,0]
% R = [9,8,6,10]

quicksort([],[]).
quicksort([X|Xs],Ys) :-
  partitionQ(X,Xs,Left,Right),
  quicksort(Left,Ls),
  quicksort(Right,Rs),
  appendQ(Ls,[X|Rs],Ys).

% | ?- quicksort([1, 2, 9, 8, 1, 6, 4, 3, 10, 0, 0, 0, -1], R)
% R = [-1,0,0,0,1,1,2,3,4,6,8,9,10]
