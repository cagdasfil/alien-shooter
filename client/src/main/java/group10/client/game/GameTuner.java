package group10.client.game;

import java.util.HashMap;

public  class GameTuner {
    private HashMap<String,Integer> level1;
    private HashMap<String,Integer> level2;
    private HashMap<String,Integer> level3;
    private HashMap<String,Integer> level4;

    public static int maxLevel = 4;

    public GameTuner() {
        level1 = new HashMap<>();
        level2 = new HashMap<>();
        level3 = new HashMap<>();
        level4 = new HashMap<>();

        level1.put("alienCount",4);
        level2.put("alienCount",5);
        level3.put("alienCount",6);
        level4.put("alienCount",7);

        level1.put("playerBulletRadius",15);
        level2.put("playerBulletRadius",15);
        level3.put("playerBulletRadius",15);
        level4.put("playerBulletRadius",15);

        level1.put("alienBulletRadius",10);
        level2.put("alienBulletRadius",11);
        level3.put("alienBulletRadius",12);
        level4.put("alienBulletRadius",13);

        level1.put("playerBulletMovingRate",10);
        level2.put("playerBulletMovingRate",11);
        level3.put("playerBulletMovingRate",12);
        level4.put("playerBulletMovingRate",13);

        level1.put("playerBulletCratingRate",150); // ms
        level2.put("playerBulletCratingRate",150);
        level3.put("playerBulletCratingRate",150);
        level4.put("playerBulletCratingRate",150);


        level1.put("alienBulletMovingRate",40);
        level2.put("alienBulletMovingRate",30);
        level3.put("alienBulletMovingRate",20);
        level4.put("alienBulletMovingRate",10);

        level1.put("alienBulletCreatingRate",4); //seconds
        level2.put("alienBulletCreatingRate",3);
        level3.put("alienBulletCreatingRate",3);
        level4.put("alienBulletCreatingRate",2);

    }

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
        else{
            System.out.println("Invalid game level.");
            return null;
        }

    }
}
