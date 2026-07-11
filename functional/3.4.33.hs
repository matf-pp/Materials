import Data.List (nub, sort)

odrediPeriodu :: Int -> Int
odrediPeriodu atomskiBroj
    | atomskiBroj <= 2 = 1
    | atomskiBroj <= 10 = 2
    | atomskiBroj <= 18 = 3
    | atomskiBroj <= 36 = 4
    | atomskiBroj <= 54 = 5
    | atomskiBroj <= 86 = 6
    | otherwise = 7

odrediPeriode :: [(String, Int)] -> [(String, Int)]
odrediPeriode =
    map (\(simbol, atomskiBroj) -> (simbol, odrediPeriodu atomskiBroj))

brojRadioaktivnih :: [(String, Int)] -> Int
brojRadioaktivnih =
    length . filter (\(_, atomskiBroj) -> atomskiBroj >= 83)

grupisiPoPeriodi :: [(String, Int)] -> [(Int, [String])]
grupisiPoPeriodi elementi =
    [ (perioda, [simbol | (simbol, atomskiBroj) <- elementi, odrediPeriodu atomskiBroj == perioda])
    | perioda <- sort (nub [odrediPeriodu atomskiBroj | (_, atomskiBroj) <- elementi])
    ]

najcescaPerioda :: [(String, Int)] -> Maybe Int
najcescaPerioda [] = Nothing
najcescaPerioda elementi = Just (minimum najcesce)
  where
    grupe = grupisiPoPeriodi elementi
    brojPoPeriodi = [(perioda, length simboli) | (perioda, simboli) <- grupe]
    najvise = maximum [broj | (_, broj) <- brojPoPeriodi]
    najcesce = [perioda | (perioda, broj) <- brojPoPeriodi, broj == najvise]

kompresuj :: [(String, Int)] -> [(String, Int, Int)]
kompresuj [] = []
kompresuj ((simbol, atomskiBroj) : ostatak) =
    (simbol, atomskiBroj, 1 + length isti) : kompresuj dalje
  where
    (isti, dalje) = span (== (simbol, atomskiBroj)) ostatak
