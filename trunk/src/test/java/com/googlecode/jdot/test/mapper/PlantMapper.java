package com.googlecode.jdot.test.mapper;

public class PlantMapper implements com.googlecode.jdot.mapper.DomainMapper<com.googlecode.jdot.test.domain.Plant, com.googlecode.jdot.test.view.PlantView> {

    // TypeMappers
    private final com.googlecode.jdot.mapper.DateTimeMapper mapper0 = new com.googlecode.jdot.mapper.DateTimeMapper();

    public com.googlecode.jdot.test.view.PlantView toView(com.googlecode.jdot.test.domain.Plant domain) {
        com.googlecode.jdot.test.view.PlantView view = new com.googlecode.jdot.test.view.PlantView();
        view.setDivision(domain.getDivision());
        view.setMaximumGrowthPerYear(domain.getMaximumGrowthPerYear());
        view.setMaximumHeight(domain.getMaximumHeight());
        view.setMaximumWidth(domain.getMaximumWidth());
        view.setName(domain.getName());
        view.setRelatedPlants(domain.getRelatedPlants());
        view.setShortCut(domain.getShortCut());
        view.setPerennial(domain.isPerennial());
        return view;
    }

    public com.googlecode.jdot.test.domain.Plant toDomain(com.googlecode.jdot.test.view.PlantView view) {
        com.googlecode.jdot.test.domain.Plant.Builder builder = new com.googlecode.jdot.test.domain.Plant.Builder();
        builder.withDivision(view.getDivision());
        builder.withMaximumGrowthPerYear(view.getMaximumGrowthPerYear());
        builder.withMaximumHeight(view.getMaximumHeight());
        builder.withMaximumWidth(view.getMaximumWidth());
        builder.withName(view.getName());
        builder.withRelatedPlants(view.getRelatedPlants());
        builder.withShortCut(view.getShortCut());
        builder.withPerennial(view.isPerennial());
        return builder.build();
    }

}