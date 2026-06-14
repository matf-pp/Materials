qsort :: (Ord a) => [a] -> [a]
qsort [] = []
qsort (x:xs) =
  -- Pivot je x; listu delimo na manje/jednake i vece elemente, pa delove sortiramo rekurzivno.
  let manji = qsort [a | a <- xs, a <= x]
      veci = qsort [a | a <- xs, a > x]
   in manji ++ [x] ++ veci
