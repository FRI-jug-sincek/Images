package si.fri.rso.samples.images.config;

import javax.enterprise.context.ApplicationScoped;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

@ConfigBundle("app-properties")
@ApplicationScoped
public class AppProperties {

    @ConfigValue(watch = true)
    private String recognitionKey;


    public String getRecognitionKey() {
        return this.recognitionKey;
    }

    public void setRecognitionKey(final String recognitionKey) {
        this.recognitionKey = recognitionKey;
    }

    @ConfigValue(watch = true)
    private Boolean useApis;


    public Boolean getUseApis() {
        return this.useApis;
    }

    public void setUseApis(final Boolean useApis) {
        this.useApis = useApis;
    }
}