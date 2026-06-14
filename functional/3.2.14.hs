obrni :: [a] -> [a]
-- foldl prolazi sleva nadesno, a flip (:) stavlja svaki novi element na pocetak akumulatora.
obrni = foldl (flip (:))  []
