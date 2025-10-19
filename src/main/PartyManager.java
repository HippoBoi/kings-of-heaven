package main;

import entity.Character;
import java.util.List;

public class PartyManager {
    public List<Character> partyMembers;
    GamePanel gamePanel;

    public PartyManager(GamePanel gamePanel, List<Character> partyMembers) {
        this.gamePanel = gamePanel;
        this.partyMembers = partyMembers;
    }
}
