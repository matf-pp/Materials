import Data.List (sortBy)

-- 1.

data Artikal
    = NaStanju String Int
    | Rasprodat String
    deriving (Eq, Ord)

-- 2.

instance Show Artikal where
    show (NaStanju naziv kolicina) =
        naziv ++ ": " ++ show kolicina ++ " kom"

    show (Rasprodat naziv) =
        naziv ++ ": rasprodato"

-- 3.

pronadjiArtikal :: String -> [Artikal] -> Either String Int
pronadjiArtikal _ [] = Left "Nepostojeci artikal"

pronadjiArtikal naziv (x:xs) =
    case x of
        NaStanju n k
            | n == naziv -> Right k
        Rasprodat n
            | n == naziv -> Left "Rasprodat"
        _ -> pronadjiArtikal naziv xs

-- 4.

sortirajPoKolicini :: [Artikal] -> [Artikal]
sortirajPoKolicini =
    sortBy compareArtikli
  where
    compareArtikli a b =
        compare (kolicina a) (kolicina b) <>
        compare a b

    kolicina (NaStanju _ k) = k
    kolicina (Rasprodat _) = 0
