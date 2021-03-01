package Start;

import EnginePackage.Engine;
import GUIPackage.GameOverFrame;
import GUIPackage.MainFrame;
import MapAndMoving.RectangularMap;
import MapAndMoving.Vector2d;
import TankPackage.PlayerTank;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class start {
    public static void main(String[] args)
    {

        JSONParser jsonParser=new JSONParser();
        try(FileReader reader=new FileReader("src/main/parameters.json")) {
            Object obj=jsonParser.parse(reader);
            org.json.simple.JSONObject jsonObject=(org.json.simple.JSONObject) obj;
            int mapWidth=((Long) jsonObject.get("mapWidth")).intValue();
            int mapHeight=((Long) jsonObject.get("mapHeight")).intValue();
            int enemyTankXParameter=((Long) jsonObject.get("enemyTankXParameter")).intValue();
            int wallXParameter=((Long) jsonObject.get("wallXParameter")).intValue();
            int startingHealth=((Long) jsonObject.get("startingHealth")).intValue();
            if(mapWidth<=5 || mapHeight<=5)
            {
                System.out.println("GAME IS NOT PLAYABLE WHEN MAP IS UNDER 5x5 SIZE");
                return;
            }
            RectangularMap rectangularMap=new RectangularMap(mapWidth,mapHeight);
            PlayerTank playerTank= new PlayerTank(rectangularMap,startingHealth, new Vector2d(mapWidth/2,mapHeight/2));
            rectangularMap.setPlayerTank(playerTank);
            Engine engine=new Engine(rectangularMap,playerTank,enemyTankXParameter,wallXParameter);
            MainFrame mainFrame= new MainFrame(rectangularMap,playerTank,engine,playerTank);

        }catch (FileNotFoundException e)
        {
            System.out.println("PARAMETERS FILE NOT FOUND");
        }
        catch (IOException e)
        {
            System.out.println("IO error");
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }



        //new GameOverFrame(0);

    }
}
