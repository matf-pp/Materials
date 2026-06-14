izbaci :: [[Char]] -> Int -> [[Char]]
izbaci lst k =
  let (a,b) = splitAt k lst
  -- Izbacivanje radimo spajanjem dela pre pozicije i dela posle izbacenog elementa.
  in a ++ drop 1 b

izbaciVise :: [[Char]] -> [Int] -> [[Char]]
izbaciVise lst ks = foldl izbaci lst ks

rekonstruisi :: [[Char]] -> [[Char]] -> [Int] -> [[Char]]
rekonstruisi preostali izbaceni pozicije =
  -- Rekonstrukcija redom ubacuje sacuvane elemente na njihove stare pozicije.
  foldl (\acc (p,x) ->
    let (a,b) = splitAt p acc
    in a ++ [x] ++ b)
  preostali
  (zip pozicije izbaceni)
