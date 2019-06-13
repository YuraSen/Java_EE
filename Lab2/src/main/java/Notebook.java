/**
 * @author Yurii Senin
 * Class Notebook for creating object about notebook
 */
public class Notebook {
    /**
     * Name of brand
     */
    private String brand;
    /**
     * Processor of notebook
     */
    private String processor;
    /**
     * Videocard of notebook
     */
    private String videocard;
    /**
     * Series of notebook
     */
    private final int series;

    /**
     * Constructor for creating object about notebook
     *
     * @param brand - name of notebook
     * @param processor  - processor of notebook
     * @param videocard  - videocard of notebook
     * @param series  - series of notebook
     */
    public Notebook(String brand, String processor, String videocard, int series) {
        this.brand = brand;
        this.processor = processor;
        this.videocard = videocard;
        this.series = series;
    }

    /**
     * Setter of name notebook
     *
     * @param brand set name brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Setter of processor of notebook
     *
     * @param processor set processor of notebook
     */
    public void setProcessor(String processor) {
        this.processor = processor;
    }

    /**
     * Setter of videocard
     *
     * @param videocard set videocard
     */
    public void setVideocard(String videocard) {
        this.videocard = videocard;
    }

    /**
     * Getter of brand
     *
     * @return brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Getter of processor
     *
     * @return processor
     */
    public String getProcessor() {
        return processor;
    }

    /**
     * Getter of videocard
     *
     * @return videocard
     */
    public String getVideocard() {
        return videocard;
    }

    /**
     * Getter of series
     *
     * @return series
     */
    public int getSeries() {
        return series;
    }
}
