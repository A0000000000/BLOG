package xyz.a00000.blog.bean.common;

import lombok.*;
import xyz.a00000.blog.bean.orm.Email;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmailBean implements Serializable {

    @NonNull
    private String receiver;
    private String subject;
    private String text;

    public EmailBean(Email email) {
        this.receiver = email.getReceiver();
        this.subject = email.getSubject();
        this.text = email.getText();
    }

}
