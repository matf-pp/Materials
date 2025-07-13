#   TWO
#  +TWO
#  ------
#  FOUR
#

import constraint

problem = constraint.Problem()
# Definisemo promenljive i njihove vrednosti
problem.addVariables("SY",range(1,10))
problem.addVariables("EOUN",range(10))

# Definisemo ogranicenje za cifre
def o(s, y, e, o, u, n):
    if s*100+e*11 + y*100+10*o+u == s*1000+o*110+n:
        return True

# Dodajemo ogranicenja za cifre
problem.addConstraint(o,"SYEOUN")
# Dodajemo ogranicenje da su sve cifre razlicite
problem.addConstraint(constraint.AllDifferentConstraint())

resenja = problem.getSolutions()

for r in resenja:
    print(r)
