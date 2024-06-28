package character;

public class FirstAppearanceCharacter {

    public static void main(String[] args) {
        String str = "google";


        int[] array = new int['z' - 'A'];


        for (int i = 0; i < str.length(); i++) {
            array[str.charAt(i) - 'A']++;
        }

        for (int i = 0; i < str.length(); i++) {
            if (array[str.charAt(i) - 'A'] == 1) {
                System.out.println(str.charAt(i));
            }
        }


    }

}
