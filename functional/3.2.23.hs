ukloniDuplikate :: Eq a => [a] -> [a]
-- Kada dodamo x, iz ostatka akumulatora uklanjamo sva prethodna pojavljivanja tog elementa.
ukloniDuplikate = foldr (\ x a -> x : filter (/= x) a) []
