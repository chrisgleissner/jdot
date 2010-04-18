package com.googlecode.jdot.test.mapper;

public class PersonMapper implements com.googlecode.jdot.mapper.DomainMapper<com.googlecode.jdot.test.domain.Person, com.googlecode.jdot.test.view.PersonView> {

    // TypeMappers
    private final com.googlecode.jdot.mapper.DateTimeMapper mapper0 = new com.googlecode.jdot.mapper.DateTimeMapper();

    public com.googlecode.jdot.test.view.PersonView toView(com.googlecode.jdot.test.domain.Person domain) {
        com.googlecode.jdot.test.view.PersonView view = new com.googlecode.jdot.test.view.PersonView();
        view.setBirthday(mapper0.toView(domain.getBirthday()));
        view.setChildren(domain.getChildren());
        view.setGardenContents(domain.getGardenContents());
        view.setName(domain.getName());
        view.setParents(domain.getParents());
        return view;
    }

    public com.googlecode.jdot.test.domain.Person toDomain(com.googlecode.jdot.test.view.PersonView view) {
        com.googlecode.jdot.test.domain.Person.Builder builder = new com.googlecode.jdot.test.domain.Person.Builder();
        builder.withBirthday(mapper0.toDomain(view.getBirthday()));
        builder.withChildren(view.getChildren());
        builder.withGardenContents(view.getGardenContents());
        builder.withName(view.getName());
        builder.withParents(view.getParents());
        return builder.build();
    }

}