import Data.List (sort)

ukupnoArtikala :: [(String, Int)] -> Int
ukupnoArtikala stanje =
    sum [kolicina | (_, kolicina) <- stanje, kolicina > 0]

azurirajKolicinu :: String -> Int -> [(String, Int)] -> [(String, Int)]
azurirajKolicinu proizvod promena stanje =
    sort (filter pozitivnaKolicina stanjeSaNovim)
  where
    postoji = any (\(proizvod', _) -> proizvod' == proizvod) stanje
    izmenjeno =
        [ if proizvod' == proizvod
          then (proizvod', kolicina + promena)
          else (proizvod', kolicina)
        | (proizvod', kolicina) <- stanje
        ]
    stanjeSaNovim
        | postoji = izmenjeno
        | promena > 0 = (proizvod, promena) : izmenjeno
        | otherwise = izmenjeno
    pozitivnaKolicina (_, kolicina) = kolicina > 0

proizvodiNaStanju :: [(String, Int)] -> [String]
proizvodiNaStanju stanje =
    sort [proizvod | (proizvod, kolicina) <- stanje, kolicina > 0]

nedostajuciProizvodi :: [String] -> [(String, Int)] -> [String]
nedostajuciProizvodi trazeni stanje =
    filter (not . naStanju) trazeni
  where
    naStanju proizvod =
        any (\(proizvod', kolicina) -> proizvod' == proizvod && kolicina > 0) stanje

najzastupljeniji :: [(String, Int)] -> Maybe String
najzastupljeniji [] = Nothing
najzastupljeniji stanje = Just (head kandidati)
  where
    najvise = maximum [kolicina | (_, kolicina) <- stanje]
    kandidati = sort [proizvod | (proizvod, kolicina) <- stanje, kolicina == najvise]
