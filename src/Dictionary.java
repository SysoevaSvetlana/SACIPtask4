public class Dictionary {
    private SkipList skipList;

    public Dictionary() {
        this.skipList = new SkipList();
    }

    public void set(int key, String value) {
        skipList.insert(key, value);
    }

    public String get(int key) {
        return skipList.get(key);
    }

    public void delete(int key) {
        skipList.delete(key);
    }
}
