import Data.List (insertBy)

unesiProizvod :: (String, Float) -> [(String, Float)] -> [(String, Float)]
unesiProizvod p =
  insertBy (\(_,c1) (_,c2) -> compare c1 c2) p

ukloniProizvod :: String -> [(String, Float)] -> [(String, Float)]
ukloniProizvod naziv =
  filter (\(n,_) -> n /= naziv)

primeniPopust :: [(String, Float)] -> [(String, Float)]
primeniPopust =
  map (\(n,c) ->
    if c > 1000 then (n, c * 0.9) else (n, c)
  )

data Proizvod = MkProizvod { naziv :: String, cena :: Float }
  deriving (Show, Eq)

izbaciSkupeProizvode :: Float -> [Proizvod] -> [Proizvod]
izbaciSkupeProizvode maxCena =
  filter (\p -> cena p <= maxCena)

pronadjiProizvod :: String -> [Proizvod] -> Either String Proizvod
pronadjiProizvod n lst =
  case filter (\p -> naziv p == n) lst of
    (x:_) -> Right x
    [] -> Left ("Proizvod sa nazivom " ++ n ++ " nije pronaden.")
