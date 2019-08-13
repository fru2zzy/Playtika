import org.codehaus.jackson.map.ObjectMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        Game game = new Game(80, "Den", "Warrior");

        ///////////////////// JSON Serialization /////////////////////

        ObjectMapper mapper = new ObjectMapper();

        // Write our cluster into json file
        File fileJson = new File("game.json");
        mapper.writeValue(fileJson, game);

        //JSON from file to Object
        Game gameJson = mapper.readValue(fileJson, Game.class);
        fileJson.delete();

        if (game.equals(gameJson)) {
            System.out.println("Our object 'game' is equal to 'gameJson' that means successful serialization / de-serialization");
            System.out.println(gameJson + "\n");
        } else {
            throw new Exception("Something wrong with serialization by JSON\nOriginal Object: " + game + "\nObject after de-serialization: " + gameJson);
        }

        ///////////////////// XML Serialization /////////////////////

        File fileXml = new File("game.xml");


        JAXBContext jaxbContext = JAXBContext.newInstance(Game.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.marshal(game, fileXml);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Game gameXml = (Game) unmarshaller.unmarshal(fileXml);
        fileXml.delete();

        if (game.equals(gameXml)) {
            System.out.println("Our object 'game' is equal to 'gameXml' that means successful serialization / de-serialization");
            System.out.println(gameXml + "\n");
        } else {
            throw new Exception("Something wrong with serialization by XML\nOriginal Object: " + game + "\nObject after de-serialization: " + gameXml);
        }
    }
}
