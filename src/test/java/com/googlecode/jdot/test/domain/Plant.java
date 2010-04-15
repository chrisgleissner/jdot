/*
 * Copyright (C) 2010 Christian Gleissner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.jdot.test.domain;

import java.util.Set;

import com.googlecode.jdot.annotation.Domain;
import com.googlecode.jdot.annotation.Id;

// TODO gleissc Change this to AbstractPlant and generate Plant subclass. This approach makes more sense from
// a development point of view and additionally causes no problems with the Maven compiler plugin

@Domain
public class Plant extends AbstractPlant {

    @Id
    PlantDivision division;

    double[] maximumGrowthPerYear;

    double maximumHeight;

    float maximumWidth;

    @Id
    String name;

    boolean perennial;

    Set<Plant> relatedPlants;

    char shortCut;

    public PlantDivision getDivision() {
        return division;
    }

    public double[] getMaximumGrowthPerYear() {
        return maximumGrowthPerYear;
    }

    public double getMaximumHeight() {
        return maximumHeight;
    }

    public float getMaximumWidth() {
        return maximumWidth;
    }

    public String getName() {
        return name;
    }

    public Set<Plant> getRelatedPlants() {
        return relatedPlants;
    }

    public char getShortCut() {
        return shortCut;
    }

    public boolean isPerennial() {
        return perennial;
    }
}
