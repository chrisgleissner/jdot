package com.googlecode.jdot.test.view;
public class PlantView extends com.googlecode.jdot.test.view.ViewObject {
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

    public void setDivision(com.googlecode.jdot.test.domain.PlantDivision division) {
        this.division = division;
    }

    public void setMaximumGrowthPerYear(double[] maximumGrowthPerYear) {
        this.maximumGrowthPerYear = maximumGrowthPerYear;
    }

    public void setMaximumHeight(double maximumHeight) {
        this.maximumHeight = maximumHeight;
    }

    public void setMaximumWidth(float maximumWidth) {
        this.maximumWidth = maximumWidth;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public void setRelatedPlants(java.util.Set<com.googlecode.jdot.test.domain.Plant> relatedPlants) {
        this.relatedPlants = relatedPlants;
    }

    public void setShortCut(char shortCut) {
        this.shortCut = shortCut;
    }

    public void setPerennial(boolean perennial) {
        this.perennial = perennial;
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
        PlantView other = (PlantView) o;
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
