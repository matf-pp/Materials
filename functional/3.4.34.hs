import Data.List (sort, sortBy)

data Element
    = Metal String Int
    | Nemetal String Int
    | Gas String Int
    | Radioaktivan String Int
    deriving (Eq, Ord)

instance Show Element where
    show (Metal naziv valenca) = "[METAL] " ++ naziv ++ " (" ++ show valenca ++ ")"
    show (Nemetal naziv valenca) = "[NEMETAL] " ++ naziv ++ " (" ++ show valenca ++ ")"
    show (Gas naziv valenca) = "[GAS] " ++ naziv ++ " (" ++ show valenca ++ ")"
    show (Radioaktivan naziv valenca) = "[RADIOAKTIVAN] " ++ naziv ++ " (" ++ show valenca ++ ")"

valenca :: Element -> Int
valenca (Metal _ v) = v
valenca (Nemetal _ v) = v
valenca (Gas _ v) = v
valenca (Radioaktivan _ v) = v

pronadjiRadioaktivan :: [Element] -> Maybe String
pronadjiRadioaktivan elementi =
    case [naziv | Radioaktivan naziv _ <- sort elementi] of
        [] -> Nothing
        radioaktivni -> Just (last radioaktivni)

sortirajPoValenci :: [Element] -> [Element]
sortirajPoValenci =
    sortBy (\e1 e2 -> compare (valenca e1) (valenca e2) <> compare e1 e2)
