import java.util.Random;

public class SkipList {
    private static final int MAX_LEVEL = 16;
    private static final double P = 0.5;
    private int level;
    private SkipListNode header;
    private Random random;

    public SkipList() {
        this.level = 0;
        this.header = new SkipListNode(MAX_LEVEL, Integer.MIN_VALUE, null);
        this.random = new Random();
    }

    private int randomLevel() {
        int lvl = 0;
        while (Math.random() < P && lvl < MAX_LEVEL) {
            lvl++;
        }
        return lvl;
    }

    public void insert(int key, String value) {
        SkipListNode[] update = new SkipListNode[MAX_LEVEL + 1];
        SkipListNode x = header;

        for (int i = level; i >= 0; i--) {
            while (x.forward[i] != null && x.forward[i].key < key) {
                x = x.forward[i];
            }
            update[i] = x;
        }

        x = x.forward[0];

        if (x == null || x.key != key) {
            int lvl = randomLevel();
            if (lvl > level) {
                for (int i = level + 1; i < lvl + 1; i++) {
                    update[i] = header;
                }
                level = lvl;
            }

            x = new SkipListNode(lvl, key, value);
            for (int i = 0; i <= lvl; i++) {
                x.forward[i] = update[i].forward[i];
                update[i].forward[i] = x;
            }
        } else {
            x.value = value;
        }
    }

    public String get(int key) {
        SkipListNode x = header;
        for (int i = level; i >= 0; i--) {
            while (x.forward[i] != null && x.forward[i].key < key) {
                x = x.forward[i];
            }
        }

        x = x.forward[0];

        if (x != null && x.key == key) {
            return x.value;
        } else {
            return null;
        }
    }

    public void delete(int key) {
        SkipListNode[] update = new SkipListNode[MAX_LEVEL + 1];
        SkipListNode x = header;

        for (int i = level; i >= 0; i--) {
            while (x.forward[i] != null && x.forward[i].key < key) {
                x = x.forward[i];
            }
            update[i] = x;
        }

        x = x.forward[0];

        if (x != null && x.key == key) {
            for (int i = 0; i <= level; i++) {
                if (update[i].forward[i] != x) {
                    break;
                }
                update[i].forward[i] = x.forward[i];
            }

            while (level > 0 && header.forward[level] == null) {
                level--;
            }
        }
    }
}
