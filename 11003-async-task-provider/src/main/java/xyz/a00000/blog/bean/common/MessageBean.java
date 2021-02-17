package xyz.a00000.blog.bean.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageBean implements Serializable {

    public static final String TYPE_EMAIL = "EMAIL";
    public static final String TYPE_IMAGE = "IMAGE";

    @NonNull
    private String type;
    @NonNull
    private String id;

}
