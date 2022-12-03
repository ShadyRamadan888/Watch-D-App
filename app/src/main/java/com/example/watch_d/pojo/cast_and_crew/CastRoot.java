package com.example.watch_d.pojo.cast_and_crew;

import java.util.List;

public class CastRoot {

    public int id;
    public List<Cast> cast;
    public List<Crew> crew;

    public List<Cast> getCast() {
        return cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }
}
