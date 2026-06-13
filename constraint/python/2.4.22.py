from constraint import Problem

def main():
    p=Problem(); p.addVariables(['X1','X2'], range(10))
    p.addConstraint(lambda x1,x2: 2*x1 + 3*x2 < 23, ['X1','X2'])
    p.addConstraint(lambda x1,x2: 45*x1 - 34*x2 < 12, ['X1','X2'])
    r=max(p.getSolutions(), key=lambda x: x['X1']**2 + x['X2']**2)
    print(f"X = [{r['X1']}, {r['X2']}]")
if __name__ == '__main__': main()
