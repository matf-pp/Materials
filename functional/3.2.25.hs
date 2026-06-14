grupisi :: [String] -> [[String]]
grupisi [] = []
grupisi (x:xs) =
    -- span izdvaja pocetni blok elemenata jednakih prvom elementu.
    let (isti, ostali) = span (== x) xs
    in (x : isti) : grupisi ostali
