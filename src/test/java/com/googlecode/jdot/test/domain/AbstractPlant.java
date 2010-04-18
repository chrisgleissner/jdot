package com.googlecode.jdot.test.domain;
public abstract class AbstractPlant extends com.googlecode.jdot.test.domain.DomainObject {
    public final static class Builder {
        private com.googlecode.jdot.test.domain.Plant template;
        public Builder() {
            this.template = new com.googlecode.jdot.test.domain.Plant();
        }
        public Builder(com.googlecode.jdot.test.domain.Plant o) {
            try {
                this.template = (com.googlecode.jdot.test.domain.Plant) o.clone();
            } catch (Exception e) {
                throw new RuntimeException("Cloning failed: " + o, e);
            }
        }

        public Builder withDivision(com.googlecode.jdot.test.domain.PlantDivision division) {
            template.division = division;
            return this;
        }

        public Builder withMaximumGrowthPerYear(double[] maximumGrowthPerYear) {
            template.maximumGrowthPerYear = maximumGrowthPerYear;
            return this;
        }

        public Builder withMaximumHeight(double maximumHeight) {
            template.maximumHeight = maximumHeight;
            return this;
        }

        public Builder withMaximumWidth(float maximumWidth) {
            template.maximumWidth = maximumWidth;
            return this;
        }

        public Builder withName(java.lang.String name) {
            template.name = name;
            return this;
        }

        public Builder withRelatedPlants(java.util.Set<com.googlecode.jdot.test.domain.Plant> relatedPlants) {
            template.relatedPlants = relatedPlants;
            return this;
        }

        public Builder withShortCut(char shortCut) {
            template.shortCut = shortCut;
            return this;
        }

        public Builder withPerennial(boolean perennial) {
            template.perennial = perennial;
            return this;
        }

        public com.googlecode.jdot.test.domain.Plant build() {
            return this.template;
        }
    }

    private com.googlecode.jdot.test.domain.PlantDivision division;
    private double[] maximumGrowthPerYear;
    private double maximumHeight;
    private float maximumWidth;
    private java.lang.String name;
    private java.util.Set<com.googlecode.jdot.test.domain.Plant> relatedPlants;
    private char shortCut;
    private boolean perennial;

    public com.googlecode.jdot.test.domain.PlantDivision getDivision() {
        return this.division;
    }

    public double[] getMaximumGrowthPerYear() {
        return this.maximumGrowthPerYear;
    }

    public double getMaximumHeight() {
        return this.maximumHeight;
    }

    public float getMaximumWidth() {
        return this.maximumWidth;
    }

    public java.lang.String getName() {
        return this.name;
    }

    public java.util.Set<com.googlecode.jdot.test.domain.Plant> getRelatedPlants() {
        return this.relatedPlants;
    }

    public char getShortCut() {
        return this.shortCut;
    }

    public boolean isPerennial() {
        return this.perennial;
    }
    /**
     * Identifying fields: division, name
    */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        AbstractPlant other = (AbstractPlant) o;
        if (division == null) {
            if (other.division != null)
                return false;
        } else if (!division.equals(other.division))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    /**
     * Identifying fields: division, name
    */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime;
        result = prime * result + ((division == null) ? 0 : division.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

}
