package main;

public class GameScene {
    public enum Scene {
        MENU("menu"),
        OUTSIDE("outside"),
        FIELD_01("field_01"),
        CASTLE_01("castle_01");

        private final String displayName;

        Scene(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return this.displayName;
        }
    };

    private Scene currentScene = Scene.OUTSIDE;

    public void changeScene(Scene newScene) {
        this.currentScene = newScene;
    }

    public Scene getScene() {
        return this.currentScene;
    }

    public String getSceneName() {
        return this.currentScene.getDisplayName();
    }
}
