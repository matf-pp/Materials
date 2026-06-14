proizvodPrvih :: Num a => Int -> a
proizvodPrvih n
      | n < 1 = 0
      | n == 1 = 1
      -- Za n > 1 izdvajamo n i rekurzivno racunamo proizvod prethodnih brojeva.
      | otherwise = n * proizvodPrvih (n-1)
