from constraint import AllDifferentConstraint, Problem

def main():
    p=Problem(); cells=[(r,c) for r in range(1,4) for c in range(1,4)]
    for cell in cells: p.addVariable(cell, range(1,10))
    p.addConstraint(AllDifferentConstraint(), cells)
    for r,c in cells:
        domain = [x for x in range(1,10) if x % 2 != (r+c) % 2]
        p.addConstraint(lambda x, domain=domain: x in domain, [(r,c)])
    sol=p.getSolution()
    for r in range(3,0,-1): print(' '.join(str(sol[(r,c)]) for c in range(1,4)))
if __name__ == '__main__': main()
