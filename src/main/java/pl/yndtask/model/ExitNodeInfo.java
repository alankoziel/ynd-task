package pl.yndtask.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExitNodeInfo {
    private String exitNode;
    private String published;
    private String lastStatus;
    private String exitAddress;
    private String exitAddressTime;
}