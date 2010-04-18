package com.googlecode.jdot.test.view;
public class PersonView extends com.googlecode.jdot.test.view.ViewObject {
    private java.util.Date birthday;
    private java.util.Collection<? extends com.googlecode.jdot.test.domain.PersonIntf> children;
    private java.util.Collection<com.googlecode.jdot.test.domain.Plant> gardenContents;
    private java.lang.String name;
    private java.util.Collection<? extends com.googlecode.jdot.test.domain.PersonIntf> parents;

    public java.util.Date getBirthday() {
        return this.birthday;
    }

    public java.util.Collection<? extends com.googlecode.jdot.test.domain.PersonIntf> getChildren() {
        return this.children;
    }

    public java.util.Collection<com.googlecode.jdot.test.domain.Plant> getGardenContents() {
        return this.gardenContents;
    }

    public java.lang.String getName() {
        return this.name;
    }

    public java.util.Collection<? extends com.googlecode.jdot.test.domain.PersonIntf> getParents() {
        return this.parents;
    }

    public void setBirthday(java.util.Date birthday) {
        this.birthday = birthday;
    }

    public void setChildren(java.util.Collection<? extends com.googlecode.jdot.test.domain.PersonIntf> children) {
        this.children = children;
    }

    public void setGardenContents(java.util.Collection<com.googlecode.jdot.test.domain.Plant> gardenContents) {
        this.gardenContents = gardenContents;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public void setParents(java.util.Collection<? extends com.googlecode.jdot.test.domain.PersonIntf> parents) {
        this.parents = parents;
    }
    /**
     * Identifying fields: birthday, name
    */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        PersonView other = (PersonView) o;
        if (birthday == null) {
            if (other.birthday != null)
                return false;
        } else if (!birthday.equals(other.birthday))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    /**
     * Identifying fields: birthday, name
    */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime;
        result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

}
