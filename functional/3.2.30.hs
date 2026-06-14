import Data.Char (isDigit, isLower)

broj :: String -> Bool
broj s = all isDigit s

mala :: String -> Bool
mala s = all isLower s

sifruj :: [String] -> [String]
sifruj = map kodiraj
  where
    -- Rec se obelezava prefiksom i sufiksom prema tome da li sadrzi samo cifre, mala slova ili ostalo.
    kodiraj s
      | broj s = 'C' : s ++ "C"
      | mala s = 'M' : s ++ "M"
      | otherwise = 'O' : s ++ "O"
