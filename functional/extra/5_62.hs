qsort :: Ord a => [a] -> [a]
qsort [] = []
-- Pivot je prvi element; ostale delimo na manje/jednake i vece, pa sortiramo delove.
qsort (x:xs) = qsort manji ++ [x] ++ qsort veci
      where manji = [a | a <- xs, a <= x]
            veci  = [b | b <- xs, b > x]
