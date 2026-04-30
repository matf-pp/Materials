grupisi :: [String] -> [[String]]
grupisi [] = []
grupisi (x:xs) =
    let (isti, ostali) = span (== x) xs
    in (x : isti) : grupisi ostali
