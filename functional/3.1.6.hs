tipJednacine :: Double -> Double -> Double -> String
tipJednacine a b c
  | a == 0 = "Degenerisana"
  -- Diskriminanta b^2 - 4ac odredjuje broj realnih resenja kvadratne jednacine.
  | (b*b - 4*a*c) == 0 = "Jedno resenje"
  | (b*b - 4*a*c) > 0 = "Dva resenja"
  | otherwise = "Nema resenja"
