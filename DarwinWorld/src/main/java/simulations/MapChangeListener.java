package simulations;

import components.MapStatistics;
import maps.WorldMap;

public interface MapChangeListener {

    void mapChanged(MapStatistics statistics);
}
