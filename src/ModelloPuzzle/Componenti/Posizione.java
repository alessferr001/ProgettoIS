package ModelloPuzzle.Componenti;

import java.util.Objects;

public class Posizione{
    private int x,y;

    public Posizione(int x, int y){
        this.x=x;
        this.y=y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public String toString(){
        return "<"+this.getX()+"> <"+this.getY()+">";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Posizione posizione = (Posizione) o;
        return x == posizione.x && y == posizione.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
