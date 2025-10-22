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

        Character defaultCharacter = new Character("Rhay");
        Character defaultFollowCharacter = new Character("Clean");

        defaultParty.add(defaultCharacter);
        defaultParty.add(defaultFollowCharacter);

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
