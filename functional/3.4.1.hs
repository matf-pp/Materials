type Racun = (String, Int)

otvori :: [Racun] -> String -> [Racun]
otvori b br
  | any (\(x, _) -> x == br) b = b
  | otherwise = (br, 0) : b

zatvori :: [Racun] -> String -> [Racun]
zatvori b br = filter (\(x, _) -> x /= br) b

uplati :: [Racun] -> String -> Int -> [Racun]
uplati b br iznos =
  map (\(x, y) -> if x == br then (x, y + iznos) else (x, y)) b