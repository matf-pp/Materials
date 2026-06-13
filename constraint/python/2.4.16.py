from constraint import Problem

def main():
    p=Problem(); names=['A','B','C','D']
    for n,b in zip(names,[30,66,300,120]): p.addVariable(n, range(b+1))
    p.addConstraint(lambda a,b,c,d: 100*a+45*b+10*c+25*d <= 3000, names)
    p.addConstraint(lambda a,b,c,d: 10*a+7*b+3*c+4.5*d <= 1000, names)
    p.addConstraint(lambda a,b,c,d: 8*a+6.8*b+4*c+3*d <= 300, names)
    r=max(p.getSolutions(), key=lambda x: 10*x['A']+8*x['B']+4.5*x['C']+3.5*x['D'])
    print(10*r['A']+8*r['B']+4.5*r['C']+3.5*r['D'], r)
if __name__ == '__main__': main()
