kompresuj :: [[Int]] -> [[(Int, Int)]]
kompresuj = map kompresujRed
  where
    kompresujRed row =
      [ (i, stepen x)
      | (i, x) <- zip [0..] row
      , x /= 0
      ]
    stepen x = round (logBase 2 (fromIntegral x))

dekompresuj :: [[(Int, Int)]] -> Int -> [[Int]]
dekompresuj lst n = map (dekompresujRed n) lst
  where
    dekompresujRed len pairs =
      [ valueAt i pairs | i <- [0..len-1] ]
    valueAt i pairs =
      case lookup i pairs of
        Just p -> 2 ^ p
        Nothing -> 0