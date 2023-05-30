package company.ejm.commoncode.api.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Message {
        private int code;
        private String message;
        private Object data;
}
