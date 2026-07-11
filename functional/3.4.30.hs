import Data.List (sort, sortBy)

data LogPoruka
    = Debug String
    | Info String
    | Warn String
    | Error String
    deriving (Eq, Ord)

instance Show LogPoruka where
    show (Debug poruka) = "[DEBUG] " ++ poruka
    show (Info poruka) = "[INFO] " ++ poruka
    show (Warn poruka) = "[WARN] " ++ poruka
    show (Error poruka) = "[ERROR] " ++ poruka

sadrzaj :: LogPoruka -> String
sadrzaj (Debug poruka) = poruka
sadrzaj (Info poruka) = poruka
sadrzaj (Warn poruka) = poruka
sadrzaj (Error poruka) = poruka

greska :: [LogPoruka] -> Maybe String
greska poruke =
    case [poruka | Error poruka <- sort poruke] of
        [] -> Nothing
        greske -> Just (last greske)

sortirajPoDuzini :: [LogPoruka] -> [LogPoruka]
sortirajPoDuzini =
    sortBy (\p1 p2 -> compare (length (sadrzaj p1)) (length (sadrzaj p2)) <> compare p1 p2)
