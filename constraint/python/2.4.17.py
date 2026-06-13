from constraint import Problem

def main():
    p=Problem(); names=['W','K','T','D']; p.addVariables(names, range(21))
    p.addConstraint(lambda w,k,t,d: 100*w+45*k+10*t+25*d <= 3000, names)
    p.addConstraint(lambda w,k,t,d: 3*w+10*k+15*t+20*d <= 1000, names)
    p.addConstraint(lambda w,k,t,d: 8*w+6*k+14*t+11*d <= 300, names)
    r=max(p.getSolutions(), key=lambda x: 5*x['W']+11*x['K']+20*x['T']+15*x['D'])
    print(5*r['W']+11*r['K']+20*r['T']+15*r['D'], r)
if __name__ == '__main__': main()
