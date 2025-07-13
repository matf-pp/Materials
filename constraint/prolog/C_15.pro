word(L,Sum):-
    sum(L) #= Sum,
    all_different(L).

kakuro:-
    Vars=[X1,X2,X3,X4,X5,X6,X7,X8,X9,X10,X11,X12,X13,X14,X15,X16],
    Vars :: 1..9,
    word([X1,X2],5),
    word([X3,X4,X5,X6],17),
    word([X7,X8], 6),
    word([X9, X10], 4),
    word([X11,X12,X13,X14], 10),
    word([X15,X16], 3),
    word([X3,X7], 14),
    word([X1,X4,X8,X11], 11),
    word([X2,X5], 4),
    word([X12,X15], 3),
    word([X6,X9,X13,X16], 10),
    word([X10,X14], 3),
    word([X10,X14],3),
    labeling(Vars),
    writeln(Vars).

    
