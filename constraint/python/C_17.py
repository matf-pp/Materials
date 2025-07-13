import constraint

def o(j,e,p,n,s,d,o,c,u,i):
    if j*20 + e*1003 + p*10000 + n*90 + s*10 - d*1000 - o*100 - c == s*1001 + u*100 + i*10:
        return True

problem = constraint.Problem()

problem.addVariables('JPSD', range(1,10))
problem.addVariables('ENOCUI', range(10))

problem.addConstraint(constraint.AllDifferentConstraint(), 'JEPNSDOCUI')
problem.addConstraint(o, 'JEPNSDOCUI')

i = 1
for r in problem.getSolutions():
    print(f'{i}. (({r["J"]}{r["E"]} + {r["P"]}{r["E"]}{r["N"]}{r["S"]}{r["E"]}) - {r["D"]}{r["O"]}{r["N"]}{r["C"]}) + {r["J"]}{r["E"]} = {r["S"]}{r["U"]}{r["I"]}{r["S"]}')
    i += 1
