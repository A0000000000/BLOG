package xyz.a00000.blog.bean.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageBean implements Serializable {

    @NonNull
    private String fileBucket;
    @NonNull
    private String filename;
    @NonNull
    private String type;
    private Long size;
    private byte[] data;

    public static final String TYPE_SAVE = "SAVE";
    public static final String TYPE_DELETE = "DELETE";


}
