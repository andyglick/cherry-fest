package io.magentys.cherry.reactive;

import io.magentys.Agent;
import io.magentys.Mission;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class MultiMissionEvent implements CherryEvent<Agent,List<Mission>> {

    private final String name;
    private final Agent agent;
    private final List<Mission> metadata;

    public MultiMissionEvent(final String name, final Agent agent) {
        this.name = name;
        this.agent = agent;
        this.metadata = new ArrayList<>();
    }

    public MultiMissionEvent(final String name, final Agent agent, final Mission... missions) {
        this.name = name;
        this.agent = agent;
        this.metadata = asList(missions);
    }

    public static MultiMissionEvent eventOf(final String name, final Agent agent){
        return new MultiMissionEvent(name, agent);
    }


    public static MultiMissionEvent eventOf(final String name, final Agent agent, final Mission... missions){
        return new MultiMissionEvent(name, agent, missions);
    }


    @Override
    public Agent body() {
        return agent;
    }


    @Override
    public String name() {
        return name;
    }


    @Override
    public List<Mission> metadata() {
        return metadata;
    }


}
