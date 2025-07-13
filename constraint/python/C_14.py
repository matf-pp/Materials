import constraint

problem = constraint.Problem()


# Nikola se sprema za takmicenje u bodibildingu, i potrebno mu je da svoju 
# ishranu dovede do savrsenstva. Na raspolaganju ima cetiri suplementa:
# whey protein, kreatin monohidrat, i anabolicke steroide: testosteron i danazol. 

# Prema planu ishrane, Nikola moze pojesti 3kg suplementacije. Jedna doza whey proteina 
# staje 100gr, kreatina monohidrata 45gr, a anabolickih steroida testosterona i 
# danazola po 10 odnosno 25gr. 

# Svaki suplement povecava vezivanje vode u organizmu, koje, prema pravilima 
# takmicenje ne sme bici vece od 1dm3. Jedna doza whey proteina veze 3cm3 vode, 
# doza kreatina veze 10cm3 vode, dok anabolici redom vezu 15 odnosno 20cm3 vode. 

# Cena jedne doze whey proteina jeste 8EUR, doza kreatina kosta 6EUR, dok doza 
# anabolika staje 14EUR odnosno 11EUR redom. 

# Ukoliko Nikola na raspolaganju ima po 20 doza svakog od navedena cetiri suplementa, 
# i ukoliko doza whey proteina donosi 5gr misicne mase, doza kreatina povecava 
# misicnu masu za 11 grama dok doza steroida povecava misicnu masu za 20 odnosno 
# 15gr redom, odrediti Nikolin optimalni plan ishrane. 
# Na standardni izlaz ispisati maksimalnu kolicinu misicne mase koja se moze
# stimulisati suplementacijom kao i koliko doza kog suplementa Nikola mora uzeti 
# da bi ostvario taj rezultat. Koristiti komandu ispisa:

# print("""
# Maksimalno povecanje misicne mase: {}
# Plan ishrane:
# {} doza whey proteina,
# {} doza kreatina monohidrata,
# {} doza testosterona,
# {} doza danazola
# """.format(maximum_gr, plan_ishrane['W'], plan_ishrane['K'], plan_ishrane['T'], plan_ishrane['D']))


problem.addVariable('W', range(20))
problem.addVariable('K', range(20))
problem.addVariable('T', range(20))
problem.addVariable('D', range(20))

# We have 3kg = 3,000g available
def weight_constraint(a, b, c, d):
    if (a*100 + b*45 + c*10 + d*25) <= 3000:
        return True

# We have 1dm^3 = 1,000cm^3 available
def volume_constraint(a, b, c, d):
    if (a*3 + b*10 * c*15 + d*20) <= 1000:
        return True

# We can't exceed $300
def value_constraint(a, b, c, d):
    if (a*8 + b*6 + c*14 + d*11) < 300:
        return True

problem.addConstraint(weight_constraint, "WKTD")
problem.addConstraint(volume_constraint, "WKTD")
problem.addConstraint(value_constraint, "WKTD")

maximum_gr = 0
plan_ishrane = {}
solutions = problem.getSolutions()

for s in solutions:
    current_sweetness = s['W']*5 + s['K']*11 + s['T']*20 + s['D']*15
    if current_sweetness > maximum_gr:
        maximum_gr = current_sweetness
        plan_ishrane = s

print("""
Maksimalno povecanje misicne mase: {}
Plan ishrane:
{} doza whey proteina,
{} doza kreatina monohidrata,
{} doza testosterona,
{} doza danazola
""".format(maximum_gr, plan_ishrane['W'], plan_ishrane['K'], plan_ishrane['T'], plan_ishrane['D']))