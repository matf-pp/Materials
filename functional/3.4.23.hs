ubaciNaPoz :: [(String, Int)] -> Int -> (String, Int) -> [(String, Int)]
ubaciNaPoz lst k x =
  let (a, b) = splitAt k lst
  in a ++ [x] ++ b

ubaciURed :: [(String, Int)] -> (String, Int) -> [(String, Int)]
ubaciURed [] x = [x]
ubaciURed (y:ys) x@(_, br)
  | br <= snd y = x : y : ys
  | otherwise = y : ubaciURed ys x

ubaciVise :: [(String, Int)] -> [Int] -> [(String, Int)] -> [(String, Int)]
ubaciVise lst [] [] = lst
ubaciVise lst (k:ks) (x:xs) =
  ubaciVise (ubaciNaPoz lst k x) ks xs
ubaciVise lst _ _ = lst