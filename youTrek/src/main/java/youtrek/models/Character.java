package youtrek.models;

public class Character {
    public int id = -1;
    public String name;

    public Character(String name) {
        this.name = name;
    }

    public Character(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Character)) return false;
        return this.name.equals (((Character) o).name);
    }
}
