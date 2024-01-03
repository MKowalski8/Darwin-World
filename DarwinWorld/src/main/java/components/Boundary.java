package components;

public class Boundary {
//    To jest tylko taki wstepny szkielet jakby to mogło wszystko wyglądać
//    Nie wiem czy jakieś sprawdzania czy zwierzak nie wbił sie na granice to właśnie robimy tutaj
//    (na moje by mozna).
    private final int width;
    private final int height;

    public Boundary(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
