package it.si2001.backup;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MockContract {

    private String id;
    private String fullName;
    private String typeContract;
    private String dateSigning;
    private String expirationDate;
    private String acquiredPackages;
    private String consumedPackages;
    private String residualPackages;

}
