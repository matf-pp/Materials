saberiKolokvijume :: [[Int]] -> [Int]
saberiKolokvijume = map sum

maksimum :: [Int] -> Int
maksimum [] = 0
maksimum xs = maximum xs

normalizuj :: [Int] -> [Float]
normalizuj xs =
  let m = maksimum xs
  in if m == 0 then replicate (length xs) 0
     else map (\x -> fromIntegral x / fromIntegral m * 100) xs

boljiOdProcentualno :: [Int] -> [Float]
boljiOdProcentualno xs =
  let n = length xs
  in map (\x ->
      let cnt = length (filter (< x) xs)
      in fromIntegral cnt / fromIntegral n * 100) xs

prilagodiPoene :: [Int] -> [Float]
prilagodiPoene [] = []
prilagodiPoene xs =
  let roots = map (sqrt . fromIntegral) xs
      m = maximum roots
  in if m == 0 then []
     else map (\x -> x / m * 100) roots