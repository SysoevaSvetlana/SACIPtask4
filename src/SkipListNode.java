public class SkipListNode {
    int key;
    String value;
    SkipListNode[] forward;

    public SkipListNode(int level, int key, String value) {
        this.key = key;
        this.value = value;
        this.forward = new SkipListNode[level + 1];
    }
}
