package neuroverkko.Utils.DataStructures.Map;
import neuroverkko.Utils.DataStructures.List;
public class Map<K,V> {

    private List<Pair<K, V>>[] values;
    private int valuesAdded;

    public Map() {
        this.values = new List[32];
        this.valuesAdded = 0;
    }

    public V get(K key) {
        int hashcode = Math.abs(key.hashCode() % this.values.length);
        if (this.values[hashcode] == null) {
            return null;
        }

        List<Pair<K,V>> valuesInIndex = this.values[hashcode];

        for (int i = 0; i < valuesInIndex.size(); i++) {
            if (valuesInIndex.get(i).getKey().equals(key)) {
                return valuesInIndex.get(i).getValue();
            }
        }
        return null;
    }

    private List<Pair<K,V>> getListForKey(K key) {
        int hashcode = Math.abs(key.hashCode() % values.length);
        if (values[hashcode] == null) {
            values[hashcode] = new List<>();
        }
        return values[hashcode];
    }

    private int getKeyIndex(List<Pair<K,V>> list, K key) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getKey().equals(key)) {
                return i;
            }
        }
        return -1;
    }

    public void add(K key, V value) {
        List<Pair<K,V>> valuesInIndex = getListForKey(key);
        int index = getKeyIndex(valuesInIndex, key);

        if (index < 0) {
            valuesInIndex.add(new Pair<>(key, value));
            this.valuesAdded++;
        } else {
            valuesInIndex.get(index).setValue(value);
        }
    }

    public void copy(List<Pair<K,V>>[] neww, int fromIndex) {
        for (int i = 0; i< this.values[fromIndex].size(); i++) {
            Pair <K,V> value = this.values[fromIndex].get(i);
            
            int hashcode = Math.abs(value.getKey().hashCode() % neww.length);
            if (neww[hashcode] == null) {
                neww[hashcode] = new List<>();
            }

            neww[hashcode].add(value);
        }
    }

    // // private void grow(, int fromIndex) {
    //     List<Pair<K,V>>[] neww = new List[this.values.length * 2];

    //     for (int i = 0; i < this.valuesAdded.length; i++) {
    //         grow(neww, fromIndex);
    //     }

    //     this.values = neww;
    // }






    
}
