type Poeni = [Double]

promena :: Poeni -> Double -> Int -> Poeni
promena pts d n =
  [ if i >= n then x + d else x
  | (i,x) <- zip [0..] pts
  ]

bolji :: Poeni -> Poeni -> Double -> Poeni
bolji p1 p2 d =
  let s1 = sum p1
      s2 = sum p2
      adjust = map (+d)
  in if s1 >= s2 then adjust p1 else adjust p2