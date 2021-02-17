package xyz.a00000.blog.bean.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmailBean implements Serializable {

    private String sender;
    private String receiver;
    private String content;

}
