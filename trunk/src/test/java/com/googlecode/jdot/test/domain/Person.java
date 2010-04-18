package com.googlecode.jdot.test.domain;
public class Person extends com.googlecode.jdot.test.domain.DomainObject implements com.googlecode.jdot.test.domain.PersonIntf {
    public final static class Builder {
        private com.googlecode.jdot.test.domain.Person template;
        public Builder() {
            this.template = new com.googlecode.jdot.test.domain.Person();
        }
        public Builder(com.googlecode.jdot.test.domain.Person o) {
            try {
                this.template = (com.googlecode.jdot.test.domain.Person) o.clone();
            } catch (Exception e) {
                throw new RuntimeException("Cloning failed: " + o, e);
            }
        }

        public Builder withBirthday(org.joda.time.DateTime birthday) {
            template.birthday = birthday;
            return this;
        }

        public Builder withChildren(java.util.Collection<? extends com.googlecode.jdot.test.domain.PersonIntf> children) {
            template.children = children;
            return this;
        }

        public Builder withGardenContents(java.util.Collection<com.googlecode.jdot.test.domain.Plant> gardenContents) {
            template.gardenContents = gardenContents;
            return this;
        }

        public Builder withName(java.lang.String name) {
            template.name = name;
            return this;
        }

        public Builder withParents(java.util.Collection<? extends com.googlecode.jdot.test.domain.PersonIntf> parents) {
            template.parents = parents;
            return this;
        }

        public com.googlecode.jdot.test.domain.Person build() {
            return this.template;
        }
    }

    private org.joda.time.DateTime birthday;
    private java.util.Collection<? extends com.googlecode.jdot.test.domain.PersonIntf> children;
    private java.util.Collection<com.googlecode.jdot.test.domain.Plant> gardenContents;
    private java.lang.String name;
    private java.util.Collection<? extends com.googlecode.jdot.test.domain.PersonIntf> parents;

    public org.joda.time.DateTime getBirthday() {
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
        Person other = (Person) o;
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
