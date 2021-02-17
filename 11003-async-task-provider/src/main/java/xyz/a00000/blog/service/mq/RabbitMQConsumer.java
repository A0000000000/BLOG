package xyz.a00000.blog.service.mq;

import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.messaging.Message;
import xyz.a00000.blog.bean.common.EmailBean;
import xyz.a00000.blog.bean.common.ImageBean;
import xyz.a00000.blog.bean.common.MessageBean;

@EnableBinding(Sink.class)
@Slf4j
public class RabbitMQConsumer {

    @Autowired
    private RedisTemplate<String, EmailBean> emailRedisTemplate;
    @Autowired
    private RedisTemplate<String, ImageBean> imageRedisTemplate;

    @Autowired
    private MinioClient minioClient;

    @StreamListener(Sink.INPUT)
    public void inputMessageBean(Message<MessageBean> message) {
        log.info("接受到来自消息队列的消息.");
        MessageBean payload = message.getPayload();
        switch (payload.getType()) {
            case MessageBean.TYPE_EMAIL -> handleEmail(payload.getId());
            case MessageBean.TYPE_IMAGE -> handleImage(payload.getId());
            default -> {
                log.info("未知类型的消息.");
                log.info("类型: " + payload.getType());
            }
        }
        log.info("消息处理完毕.");
    }

    private void handleImage(String id) {
        log.info("向minio中保存或删除一张图片.");
        log.info("从redis中取得待操作信息及数据.");
        if (!imageRedisTemplate.hasKey(id)) {
            log.info("图片数据不存在, 保存/删除失败.");
            return;
        }
        ValueOperations<String, ImageBean> ops = imageRedisTemplate.opsForValue();
        ImageBean imageBean = ops.get(id);
        log.info("删除redis中的临时数据.");
        imageRedisTemplate.delete(id);
        switch (imageBean.getType()) {
            case ImageBean.TYPE_SAVE -> {
                try {
                    if (!minioClient.bucketExists(imageBean.getFileBucket())) {
                        minioClient.makeBucket(imageBean.getFileBucket());
                    }
                    PutObjectOptions options = new PutObjectOptions(imageBean.getSource().getSize(), PutObjectOptions.MIN_MULTIPART_SIZE);
                    minioClient.putObject(imageBean.getFileBucket(), imageBean.getFilename(), imageBean.getSource().getInputStream(), options);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            case ImageBean.TYPE_DELETE -> {
                try {
                    if (minioClient.bucketExists(imageBean.getFileBucket())) {
                        minioClient.removeObject(imageBean.getFileBucket(), imageBean.getFilename());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            default -> {
                log.info("未知操作类型.");
                log.info("操作类型为: " + imageBean.getType());
            }
        }
    }

    private void handleEmail(String id) {
        log.info("处理发送邮件.");
        if (!emailRedisTemplate.hasKey(id)) {
            log.info("数据不存在, 发送邮件失败.");
            log.info("id: " + id);
        } else {
            ValueOperations<String, EmailBean> ops = emailRedisTemplate.opsForValue();
            EmailBean emailBean = ops.get(id);
            emailRedisTemplate.delete(id);
            log.info(emailBean.toString());
        }
    }

}
