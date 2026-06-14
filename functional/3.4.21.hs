data DnevniPodaci = DnevniPodatak
  { temperatura :: Int
  , padavine :: Int
  } deriving (Show, Eq)

suviDani :: [DnevniPodaci] -> [Int]
-- Kompozicija prvo izdvaja dane bez padavina, zatim iz njih uzima temperature.
suviDani = map temperatura . filter (\d -> padavine d == 0)

rasponSuvihDana :: [DnevniPodaci] -> Int
rasponSuvihDana lst =
  let temps = suviDani lst
  in if null temps then 0 else maximum temps - minimum temps

topliPadavniDani :: [DnevniPodaci] -> Int -> [DnevniPodaci]
topliPadavniDani lst t =
  filter (\d -> temperatura d > t && padavine d > 0) lst

stvarniOsecaj :: [DnevniPodaci] -> [DnevniPodaci]
stvarniOsecaj =
  map (\d ->
    let t = temperatura d
        p = padavine d
        -- Padavine umanjuju temperaturu za 0.3 po jedinici padavina.
        newT = floor (fromIntegral t - 0.3 * fromIntegral p)
    in DnevniPodatak newT p)
