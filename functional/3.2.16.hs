prosekOdlicni :: [[Integer]] -> Float
-- Prvo racunamo prosek svakog ucenika, zatim zadrzavamo odlicne i racunamo njihov zajednicki prosek.
prosekOdlicni = prosek . filter (>= 4.5) . map prosek
    where prosek xs = realToFrac (sum xs) / fromIntegral (length xs)
