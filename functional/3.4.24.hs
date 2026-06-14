type Temperature = [Double]

promena :: Temperature -> Double -> Int -> Temperature
promena temps d n =
  -- Indekse dodajemo pomocu zip, pa menjamo samo elemente od pozicije n nadalje.
  [ if i >= n then t + d else t
  | (i, t) <- zip [0..] temps
  ]

topliji :: Temperature -> Temperature -> Double -> Temperature
topliji t1 t2 d =
  let cnt1 = length [() | (a,b) <- zip t1 t2, a > b]
      cnt2 = length [() | (a,b) <- zip t1 t2, b > a]
      adjust = map (+d)
  -- Pomeramo onu seriju koja ima vise toplijih dana u poredjenju par po par.
  in if cnt1 >= cnt2 then adjust t1 else adjust t2
