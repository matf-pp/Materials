-- Pokazujemo funkcije viseg reda, inace moze formula:
-- n * (n+1) * (2*n+1) / 6

sumaKvadrata :: Int -> Int
sumaKvadrata n = sum $ map (^2) $ [1..n]

