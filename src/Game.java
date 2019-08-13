import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class Game {

    @JsonProperty("level")
    @XmlElement
    private int level;

    @JsonProperty("name")
    @XmlElement
    private String name;

    @JsonProperty("characterClass")
    @XmlElement
    private String characterClass;

    public Game() {
    }

    public Game(int level, String name, String characterClass) {
        this.level = level;
        this.name = name;
        this.characterClass = characterClass;
    }

    @Override
    public String toString() {
        return "Game{" +
                "level=" + level +
                ", name='" + name + '\'' +
                ", characterClass='" + characterClass + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return level == game.level &&
                Objects.equals(name, game.name) &&
                Objects.equals(characterClass, game.characterClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, name, characterClass);
    }
}
