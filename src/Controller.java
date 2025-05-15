//install node.js  https://nodejs.org
//npx create-react-app survivor-ui --template typescript
//cd survivor-ui
//npm start

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class Controller {

    private final Game simulator = new Game();

    @GetMapping("/start")
    public GameState startGame() {
        simulator.startGame();
        return simulator.getCurrentState();
    }

    @PostMapping("/next")
    public GameState nextRound() {
        simulator.simulateNextRound();
        return simulator.getCurrentState();
    }

}
