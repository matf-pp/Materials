import constraint
problem = constraint.Problem()

problem.addVariable("katalizatorD17", range(5))   # Katalizator D17
problem.addVariable("filterR35", range(32))       # Filter za sagorevanje
problem.addVariable("turboZPump", range(15))      # Turbo pumpa
problem.addVariable("nitroSolar", range(4))       # Nitro

def o_cena(katalizatorD17, filterR35, turboZPump, nitroSolar):
  return 800*katalizatorD17 + 1300*filterR35 + 120*turboZPump + 9000*nitroSolar <= 20_000

def o_tezina(katalizatorD17, filterR35, turboZPump, nitroSolar):
  return 480*katalizatorD17 + 3980*filterR35 + 290*turboZPump + 6600*nitroSolar <= 20_000

def o_dimenzije(katalizatorD17, filterR35, turboZPump, nitroSolar):  # 3500 cm3
  return 6*14*katalizatorD17 + 2.4*7.2*filterR35 + 2*3*turboZPump + 24.9*110*nitroSolar <= 3500

problem.addConstraint(o_cena, ["katalizatorD17", "filterR35", "turboZPump", "nitroSolar"])
problem.addConstraint(o_tezina, ["katalizatorD17", "filterR35", "turboZPump", "nitroSolar"])
problem.addConstraint(o_dimenzije, ["katalizatorD17", "filterR35", "turboZPump", "nitroSolar"])

R = problem.getSolutions()
r = max(R, key = lambda x: x["katalizatorD17"]*9.3+x["filterR35"]*9.9+x["turboZPump"]*2.17+x["nitroSolar"]*303.5)

print("Moze se postici ubrzanje od {}s".format(r["katalizatorD17"]*9.3+r["filterR35"]*9.9+r["turboZPump"]*2.17+r["nitroSolar"]*303.5))
print(r)