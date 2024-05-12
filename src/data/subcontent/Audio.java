package data.subcontent;

public class Audio extends AudioPlay {

    public String key = "soundKey";

    public Audio() {}

    @Override
    public String toString() {
        return key;// + ": " + super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof data.subcontent.Audio n) {
            return key.equals(n.key) && super.equals(obj);
        }
        return false;
    }
}
