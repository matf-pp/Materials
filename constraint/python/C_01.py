import constraint
problem = constraint.Problem()

problem.addVariable("b", range(10))
problem.addVariable("p", range(20))
problem.addVariable("j", range(7))
problem.addVariable("m", range(5))
problem.addVariable("v", range(3))
problem.addVariable("n", range(9))

def o_size(b, p, j, m, v, n):
  return b+p+j+m+v+n <= 10

def o_cena(b, p, j, m, v, n):
  return 30*b + 300*p + 50*j + 170*m + 400*v + 450*n <= 1170

def o_masti(b, p, j, m, v, n):
  return 30*b + 10*p + 150*j + 32*m + 3*v + 15*n < 500

def o_secer(b, p, j, m, v, n):
  return 5*b + 30*p + 2*j + 15*m + 45*v + 68*n <= 150

problem.addConstraint(o_size, ["b", "p", "j", "m", "v", "n"])
problem.addConstraint(o_cena, ["b", "p", "j", "m", "v", "n"])
problem.addConstraint(o_masti, ["b", "p", "j", "m", "v", "n"])
problem.addConstraint(o_secer, ["b", "p", "j", "m", "v", "n"])

R = problem.getSolutions()
r = max(R, key = lambda x: x["b"]*20+x["p"]*15+x["j"]*70+x["m"]*40+x["v"]*23+x["n"]*7)

print("Maksimalna kolicina proteina: {} grama.".format(r["b"]*20+r["p"]*15+r["j"]*70+r["m"]*40+r["v"]*23+r["n"]*7))