import Data.List (nub, sort)

rezervacijeSale :: String -> [(String, Int, Int)] -> [(String, Int, Int)]
rezervacijeSale sala =
    filter (\(sala', _, _) -> sala' == sala)

ukupnoTrajanje :: [(String, Int, Int)] -> Int
ukupnoTrajanje =
    sum . map (\(_, _, trajanje) -> trajanje)

rasporedSala :: [(String, Int, Int)] -> [(String, [(Int, Int)])]
rasporedSala rezervacije =
    [ (sala, [(pocetak, trajanje) | (sala', pocetak, trajanje) <- rezervacije, sala' == sala])
    | sala <- sort (nub [sala | (sala, _, _) <- rezervacije])
    ]

najzauzetijaSala :: [(String, Int, Int)] -> Maybe String
najzauzetijaSala [] = Nothing
najzauzetijaSala rezervacije = Just (minimum najzauzetije)
  where
    raspored = rasporedSala rezervacije
    trajanja = [(sala, sum [trajanje | (_, trajanje) <- termini]) | (sala, termini) <- raspored]
    najvise = maximum [trajanje | (_, trajanje) <- trajanja]
    najzauzetije = [sala | (sala, trajanje) <- trajanja, trajanje == najvise]

kompresujRezervacije :: [(String, Int, Int)] -> [(String, Int, Int, Int)]
kompresujRezervacije [] = []
kompresujRezervacije ((sala, pocetak, trajanje) : ostatak) =
    (sala, pocetak, trajanje, 1 + length iste) : kompresujRezervacije dalje
  where
    (iste, dalje) = span (== (sala, pocetak, trajanje)) ostatak
