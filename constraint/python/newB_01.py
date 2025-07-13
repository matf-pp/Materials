#   TWO
#  +TWO
#  ------
#  FOUR
#

import constraint

problem = constraint.Problem()
# Definisemo promenljive i njihove vrednosti
problem.addVariables("GO",range(1,10))
problem.addVariables("RENACLS",range(10))

# Definisemo ogranicenje za cifre
def o(g, o, r, e, n, a, c, l, s):
    if g*10000+r*1000+e*100+e*10+n+o*100000+r*10000+a*1000+n*100+g*10+e==c*100000+o*10000+l*1000+o*100+r*10+s:
        return True

# Dodajemo ogranicenja za cifre
problem.addConstraint(o,"GORENACLS")
# Dodajemo ogranicenje da su sve cifre razlicite
problem.addConstraint(constraint.AllDifferentConstraint())

resenja = problem.getSolutions()

for r in resenja:
    print(r)
