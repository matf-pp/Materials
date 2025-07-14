#   TWO
#  +TWO
#  ------
#  FOUR
#

import constraint

problem = constraint.Problem()
# Definisemo promenljive i njihove vrednosti
problem.addVariables("NGH",range(1,10))
problem.addVariables("OUT",range(10))

# Definisemo ogranicenje za cifre
def o(n, g, h, o, u, t):
    if n*10+o + g*100+u*10+n + 10*n+o == h*1000+u*100+n*10+t:
        return True

# Dodajemo ogranicenja za cifre
problem.addConstraint(o,"NGHOUT")
# Dodajemo ogranicenje da su sve cifre razlicite
problem.addConstraint(constraint.AllDifferentConstraint())

resenja = problem.getSolutions()

for r in resenja:
    print(r)
