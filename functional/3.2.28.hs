-- Za duzinu 0 postoji jedna varijacija: prazna lista.
varijacije xs 0 = [[]]
-- Svaki element iz xs dodajemo na pocetak svih kracih varijacija.
varijacije xs n = concat (map (\ x -> map (x:) ys) xs)
    where ys = varijacije xs (n-1)
