package group10.client.game;

import java.util.HashMap;

public  class GameTuner {
    private HashMap<String,Integer> level1;
    private HashMap<String,Integer> level2;
    private HashMap<String,Integer> level3;
    private HashMap<String,Integer> level4;
    private HashMap<String,Integer> level5;
    // max single player level
    public static int maxLevel = 4;

    public GameTuner() {
        // settings for each level of the game
        level1 = new HashMap<>();
        level2 = new HashMap<>();
        level3 = new HashMap<>();
        level4 = new HashMap<>();
        level5 = new HashMap<>();

        // put number of aliens in a row for each different game level
        level1.put("alienCount",4);
        level2.put("alienCount",5);
        level3.put("alienCount",6);
        level4.put("alienCount",7);

        // put player bullet radius for each different game level
        level1.put("playerBulletRadius",15);
        level2.put("playerBulletRadius",15);
        level3.put("playerBulletRadius",15);
        level4.put("playerBulletRadius",15);
        level5.put("playerBulletRadius",15);

        // put alien bullet radius for each different game level
        level1.put("alienBulletRadius",10);
        level2.put("alienBulletRadius",11);
        level3.put("alienBulletRadius",12);
        level4.put("alienBulletRadius",13);
        level5.put("bossBulletRadius",15);


        // put player bullet moving rate for each different game level
        level1.put("playerBulletMovingRate",10);
        level2.put("playerBulletMovingRate",11);
        level3.put("playerBulletMovingRate",12);
        level4.put("playerBulletMovingRate",13);
        level5.put("playerBulletMovingRate",14);

        // put player bullet creating rate for each different game level
        level1.put("playerBulletCratingRate",150); // ms
        level2.put("playerBulletCratingRate",150);
        level3.put("playerBulletCratingRate",150);
        level4.put("playerBulletCratingRate",150);
        level5.put("playerBulletCratingRate",150);

        // put alien bullet moving rate for each different game level
        level1.put("alienBulletMovingRate",40);
        level2.put("alienBulletMovingRate",30);
        level3.put("alienBulletMovingRate",20);
        level4.put("alienBulletMovingRate",10);
        level5.put("bossBulletMovingRate",8);

        // put alien bullet creating rate for each different game level
        level1.put("alienBulletCreatingRate",4); //seconds
        level2.put("alienBulletCreatingRate",3);
        level3.put("alienBulletCreatingRate",3);
        level4.put("alienBulletCreatingRate",2);
        level5.put("bossBulletCreatingRate",500); //millis

        // put health of the tank alien for each different game level
        level1.put("tankAlienHealth",5);
        level2.put("tankAlienHealth",5);
        level3.put("tankAlienHealth",5);
        level4.put("tankAlienHealth",5);
        level5.put("bossHealth",100);

    }

    /**
     * Getter function to be able to get game settings for each different game level
     * @param level game level
     * @return game settings for each game level
     */

    public HashMap<String, Integer> getSettings(int level) {
        if(level == 1){
            return level1;
        }
        else if (level == 2){
            return level2;
        }
        else if (level == 3){
            return level3;
        }
        else if (level == 4){
            return level4;
        }
        else if(level == 5){
            return level5;
        }
        else{
            System.out.println("Invalid game level.");
            return null;
        }

    }
}
