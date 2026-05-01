data Transakcija = MkTransakcija
  { ident :: Int
  , iznos :: Int
  , posiljalac :: String
  , primalac :: String
  } deriving (Show, Eq)

type AktivneTransakcije = [Transakcija]

izlistaj :: AktivneTransakcije -> String -> [Transakcija]
izlistaj ts br =
  filter (\t -> posiljalac t == br || primalac t == br) ts

dodaj :: AktivneTransakcije -> Transakcija -> AktivneTransakcije
dodaj ts t = t : ts

ukloni :: AktivneTransakcije -> Int -> AktivneTransakcije
ukloni ts i = filter (\t -> ident t /= i) ts

ukupno :: AktivneTransakcije -> Int
ukupno ts = sum (map iznos ts)