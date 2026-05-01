data Poklapanje = Od { kar :: Char, poz :: Int } deriving (Show)

poklapanjeShow :: Poklapanje -> String
poklapanjeShow (Od c p) = [c] ++ " (" ++ show p ++ ")"

poklapanjeM :: Int -> String -> Maybe Poklapanje
poklapanjeM i str
  | i < 0 || i >= length str = Nothing
  | otherwise = Just (Od (str !! i) i)

poklapanjeE :: Int -> String -> Either String Poklapanje
poklapanjeE i str
  | i < 0 || i >= length str = Left "Index error"
  | otherwise = Right (Od (str !! i) i)

pronadjiM :: Poklapanje -> String -> Maybe Bool
pronadjiM (Od c p) str
  | p < 0 || p >= length str = Nothing
  | otherwise = Just (str !! p == c)

pronadjiE :: Poklapanje -> String -> Either String Bool
pronadjiE (Od c p) str
  | p < 0 || p >= length str = Left "Index error"
  | otherwise = Right (str !! p == c)