import model.Faction;
import model.Unit;

import java.util.Comparator;

/**
 * Created by Gena on 11/19/2016.
 */
public class ComparableUnit implements Comparable<Unit>{
    public final Unit unit;

    ComparableUnit(Unit unit) {this.unit = unit;}

    @Override
    public int compareTo(Unit o) {
        return 0;
    }
}
