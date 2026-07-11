import Data.List
import Data.Ord

data SeminarskiRad
    = PredatNaVreme String Int
    | PredatSaKasnjenjem String Int Int
    deriving (Eq, Ord)

instance Show SeminarskiRad where
    show (PredatNaVreme student poeni) =
        student ++ ": " ++ show poeni ++ " poena"

    show (PredatSaKasnjenjem student poeni kasnjenje) =
        student ++ ": " ++ show poeni ++
        " poena (" ++ show kasnjenje ++ " dana kasnjenja)"

efektivniPoeni :: SeminarskiRad -> Int
efektivniPoeni (PredatNaVreme _ poeni) =
    poeni

efektivniPoeni (PredatSaKasnjenjem _ poeni kasnjenje) =
    max 0 (poeni - 2 * kasnjenje)

imeStudenta :: SeminarskiRad -> String
imeStudenta (PredatNaVreme ime _) =
    ime

imeStudenta (PredatSaKasnjenjem ime _ _) =
    ime

najslabijiSeminarskiRad :: [SeminarskiRad] -> Maybe String
najslabijiSeminarskiRad [] = Nothing
najslabijiSeminarskiRad (r:rs) =
    Just (imeStudenta (najslabiji r rs))
  where
    najslabiji rad [] = rad

    najslabiji rad (x:xs)
        | efektivniPoeni x < efektivniPoeni rad =
            najslabiji x xs

        | efektivniPoeni x == efektivniPoeni rad &&
          imeStudenta x < imeStudenta rad =
            najslabiji x xs

        | otherwise =
            najslabiji rad xs

sortirajRadovePoEfektivnimPoenima :: [SeminarskiRad] -> [SeminarskiRad]
sortirajRadovePoEfektivnimPoenima =
    sortBy (comparing efektivniPoeni <> compare)