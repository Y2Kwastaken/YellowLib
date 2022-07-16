package sh.miles.yellowlib.items.immutable;

import java.util.Set;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ImmutablePersistentDataContainer {
    
    private PersistentDataContainer pdc;

    public ImmutablePersistentDataContainer(PersistentDataContainer pdc) {
        this.pdc = pdc;
    }

    public <T, Z> Z get(NamespacedKey key, PersistentDataType<T, Z> type){
        return pdc.get(key, type);
    }

    public Set<NamespacedKey> getKeys(){
        return pdc.getKeys();
    }

    public <T, Z> boolean has(NamespacedKey key, PersistentDataType<T, Z> type){
        return pdc.has(key, type);
    }
    
    public boolean isEmpty(){
        return pdc.isEmpty();
    }
}
