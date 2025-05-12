public class Sample {
    public static void main(String[] args){

    String st = "My name is ABC";

    String[] words = st.split(" ");

for(int i = words.length - 1; i >=0; i--) {

    System.out.println(words[i] + " ");
    }
}
}


