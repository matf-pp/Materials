import Data.List (nub, sort)

validniSeminarskiRadovi :: Int -> [(String, Int, Int)] -> [(String, Int)]
validniSeminarskiRadovi maxKasnjenje radovi =
    map umanjiPoene (filter validna radovi)
  where
    validna (_, _, kasnjenje) = kasnjenje <= maxKasnjenje
    umanjiPoene (student, poeni, kasnjenje) =
        (student, max 0 (poeni - 2 * kasnjenje))

ukupnoPoena :: [(String, Int, Int)] -> Int
ukupnoPoena radovi =
    sum [poeni | (_, poeni, kasnjenje) <- radovi, kasnjenje == 0]

poeniPoStudentu :: [(String, Int, Int)] -> [(String, Int)]
poeniPoStudentu radovi =
    [(student, zbirPoena student) | student <- studenti]
  where
    studenti = sort (nub [student | (student, _, _) <- radovi])
    zbirPoena student =
        sum [poeni | (student', poeni, _) <- radovi, student' == student]

najboljiStudent :: [(String, Int, Int)] -> Maybe String
najboljiStudent [] = Nothing
najboljiStudent radovi = Just (minimum najbolji)
  where
    poeni = poeniPoStudentu radovi
    najvise = maximum [zbir | (_, zbir) <- poeni]
    najbolji = [student | (student, zbir) <- poeni, zbir == najvise]

kompresujSeminarskeRadove :: [(String, Int, Int)] -> [(String, Int, Int, Int)]
kompresujSeminarskeRadove [] = []
kompresujSeminarskeRadove ((student, poeni, kasnjenje) : ostatak) =
    (student, poeni, kasnjenje, 1 + length iste) : kompresujSeminarskeRadove dalje
  where
    (iste, dalje) = span (== (student, poeni, kasnjenje)) ostatak
