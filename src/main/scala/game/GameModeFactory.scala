package game

object GameModeFactory {

    def construct(input: Int): GameMode = {
        input match {
            case 1 => ValueMode
            case 2 => SuitMode
            case 3 => BothMode
            case _ => ValueMode
        }
    }
}
