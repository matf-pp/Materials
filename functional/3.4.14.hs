import Data.Char (toLower)
import Data.List (isInfixOf)

aktivni :: [(Int, String)] -> String
aktivni [] = ""
aktivni ((_, s):_) = s

otvori :: Int -> String -> [(Int, String)] -> [(Int, String)]
otvori i str lst
  | any (\(x, _) -> x == i) lst = lst
  | otherwise = (i, str) : lst

zatvori :: Int -> [(Int, String)] -> [(Int, String)]
zatvori i = filter (\(x, _) -> x /= i)

tabUnapred :: [(Int, String)] -> Int -> [(Int, String)]
tabUnapred lst n =
  let k = n `mod` length lst
  -- Rotacija liste pomera aktivni tab za k mesta unapred.
  in drop k lst ++ take k lst

zatvoriSve :: String -> [(Int, String)] -> [(Int, String)]
zatvoriSve str =
  filter (\(_, s) -> not (map toLower str `isInfixOf` map toLower s))

fokusiraj :: Int -> [(Int, String)] -> [(Int, String)]
fokusiraj i lst =
  case break (\(x, _) -> x == i) lst of
    -- Ako trazeni tab ne postoji, redosled ostaje nepromenjen.
    (_, []) -> lst
    (before, (x:after)) -> x : before ++ after
