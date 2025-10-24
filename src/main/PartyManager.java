package main;

import entity.Character;
import entity.FollowerCharacter;

import java.util.ArrayList;
import java.util.List;

public class PartyManager {
    public List<Character> partyMembers;
    public FollowerCharacter[] followerCharacters = new FollowerCharacter[4];
    public int followerSize;
    GamePanel gamePanel;

    int MAX_FOLLOWERS = 4;

    public PartyManager(GamePanel gamePanel) {
        List<Character> defaultParty = new ArrayList<>();

        Character rhay = new Character("Rhay");
        Character clean = new Character("Clean");
        Character ramon = new Character("Ramon");

        defaultParty.add(rhay);
        defaultParty.add(ramon);
        defaultParty.add(clean);

        this.gamePanel = gamePanel;
        this.partyMembers = defaultParty;
    }

    public void addFollowerCharacter(Character character) {
        int position = 15 + (followerSize * 15);
        followerSize += 1;

        if (followerSize >= MAX_FOLLOWERS) {
            followerSize = 3;
        }

        FollowerCharacter follower = new FollowerCharacter(this.gamePanel, character, position, this.gamePanel.player);

        this.followerCharacters[followerSize] = follower;
    }
}
