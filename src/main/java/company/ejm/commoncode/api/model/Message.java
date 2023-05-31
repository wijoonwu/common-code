package company.ejm.commoncode.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Message<T> {
        private int code;
        private String message;
        private T data;

        public static <T> MessageBuilder<T> builder() {
                return new MessageBuilder<>();
        }

        public static class MessageBuilder<T> {
                private int code;
                private String message;
                private T data;

                private MessageBuilder() {}

                public MessageBuilder<T> code(int code) {
                        this.code = code;
                        return this;
                }

                public MessageBuilder<T> message(String message) {
                        this.message = message;
                        return this;
                }

                public MessageBuilder<T> data(T data) {
                        this.data = data;
                        return this;
                }

                public Message<T> build() {
                        return new Message<>(code, message, data);
                }
        }
}
