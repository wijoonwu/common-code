package company.ejm.commoncode.api.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Message {
        private StatusEnum status;
        private String message;
        private Object data;
}
