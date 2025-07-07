% maksimum dva broja 
% I nacin:
maksimum(A,B,M):- A>=B, M is A.
maksimum(A,B,M):- A<B, M is B. 
% II nacin bez trece promenjive:
% maksimum(A,B,A):- A>=B.
% maksimum(A,B,B):- A<B. 
