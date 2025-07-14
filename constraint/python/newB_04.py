#   TWO
#  +TWO
#  ------
#  FOUR
#

import constraint

problem = constraint.Problem()
# Definisemo promenljive i njihove vrednosti
problem.addVariables("SN",range(1,10))
problem.addVariables("IXE",range(10))

# Definisemo ogranicenje za cifre
def o(s, n, i, x, e):
    if 3*(s*100+i*10+x) == 2*(n*1000+i*100+n*10+e):
        return True

# Dodajemo ogranicenja za cifre
problem.addConstraint(o,"SNIXE")
# Dodajemo ogranicenje da su sve cifre razlicite
problem.addConstraint(constraint.AllDifferentConstraint())

resenja = problem.getSolutions()

for r in resenja:
    print(r)
