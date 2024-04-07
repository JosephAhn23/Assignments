public class ITIStringBuffer {

    private SinglyLinkedList<String> list;

    public ITIStringBuffer() {
        list = new SinglyLinkedList<>();
    }

    public ITIStringBuffer(String firstString){
        list = new SinglyLinkedList<>();
        list.add(firstString);
    }

    public void append(String nextString){
        list.add(nextString);
    }

    public String toString(){
        int length=0;
        for (String s:list) {
            length += s.length();
        }

        char[] charStr=new char[length];
        int currentIndex = 0;

        for (String s:list) {
            char[] chars=s.toCharArray();
            System.arraycopy(chars, 0, charStr, currentIndex, chars.length);
            currentIndex += chars.length;
        }
        return new String(charStr);
    }

}
