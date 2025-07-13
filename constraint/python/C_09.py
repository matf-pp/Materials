import constraint
problem = constraint.Problem()

problem.addVariable("b", range(10))    # B vitamin
problem.addVariable("c", range(20))    # C vitamin
problem.addVariable("d", range(7))      # D vitamin
problem.addVariable("mg", range(5))   # Mg
problem.addVariable("s", range(3))      # Selen 
problem.addVariable("zn", range(9))    # Zn

def o_size(b, c, d, mg, s, zn):
  return b+c+d+mg+s+zn <= 7

def o_cena(b, c, d, mg, s, zn):
  return 130*b + 800*c + 150*d + 370*mg + 490*s + 150*zn <= 11_800

def o_masti(b, c, d, mg, s, zn):
  return 15*b + 11*c + 10*d + 22*mg + 1*s + 13*zn <= 100

def o_secer(b, c, d, mg, s, zn):
  return 33*b + 31*c + 20*d + 18*mg + 21*s + 16*zn <= 200

problem.addConstraint(o_size, ["b", "c", "d", "mg", "s", "zn"])
problem.addConstraint(o_cena, ["b", "c", "d", "mg", "s", "zn"])
problem.addConstraint(o_masti, ["b", "c", "d", "mg", "s", "zn"])
problem.addConstraint(o_secer, ["b", "c", "d", "mg", "s", "zn"])

R = problem.getSolutions()
r = max(R, key = lambda x: x["b"]*92.5+x["c"]*155.5+x["d"]*79.6+x["mg"]*156.2+x["s"]*413.0+x["zn"]*137.7)

print("{:.2f}".format(r["b"]*92.5+r["c"]*155.5+r["d"]*79.6+r["mg"]*156.2+r["s"]*413.0+r["zn"]*137.7))



