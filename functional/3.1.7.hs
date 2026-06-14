uDekadnu :: Int -> Int -> Int
uDekadnu x osn = 
    if x == 0 then 0 
    -- Poslednja cifra se dodaje kao najmanja pozicija, a ostatak broja se obradjuje rekurzivno.
    else uDekadnu (x `div` 10) osn * osn + (mod x 10)

izDekadne :: Int -> Int -> Int
izDekadne x osn = 
    if x == 0 then 0 
    -- Pri vracanju iz dekadnog zapisa ostatak pri deljenju osnovom postaje sledeca cifra.
    else izDekadne (x `div` osn) osn * 10 + (mod x osn)
