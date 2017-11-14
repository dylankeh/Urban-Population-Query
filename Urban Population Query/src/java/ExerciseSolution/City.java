/*
 * This class is for storing city name, country code, 
 * district, and population.
 *
 * @author Qing Ge Phoenix
 */
package ExerciseSolution;


public class City {
    private String name, countryCode, district;
    private int population;
    
    public City(String name, String countryCode, String district, 
            int population){
        this.name = name;
        this.countryCode = countryCode;
        this.district = district;
        this.population = population;
    }
    
    /**
     * Retrieves name
     *
     * @return name
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Retrieves country code
     *
     * @return country code
     */
    public String getCountryCode() {
        return this.countryCode;
    }

    /**
     * Retrieves district
     *
     * @return district
     */
    public String getDistrict() {
        return this.district;
    }
    
    /**
     * Retrieves population
     *
     * @return population
     */
    public int getPopulation() {
        return this.population;
    }


    
    
}
