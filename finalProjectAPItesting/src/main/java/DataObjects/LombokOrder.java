package DataObjects;

import lombok.Data;

@Data
public class LombokOrder {
    private String id;
    private String petId;
    private String quantity;
    private String shipDate;
    private String status;
    private boolean complete;
}
