from constraint import Problem

def main():
    p = Problem(); p.addVariable('V', range(10)); p.addVariable('P', range(10)); p.addVariable('C', range(10))
    p.addConstraint(lambda v,p,c: 4*v + 3*p + 2*c <= 35, ['V','P','C'])
    r = max(p.getSolutions(), key=lambda x: 15*x['V'] + 10*x['P'] + 7*x['C'])
    print(f"Vino: {r['V']}, Parfem: {r['P']}, Casa: {r['C']}")
if __name__ == '__main__': main()
