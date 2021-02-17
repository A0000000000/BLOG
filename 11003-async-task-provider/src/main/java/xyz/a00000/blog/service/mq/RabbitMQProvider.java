package xyz.a00000.blog.service.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import xyz.a00000.blog.bean.common.MessageBean;

@EnableBinding(Source.class)
@Slf4j
public class RabbitMQProvider {

    @Autowired
    private MessageChannel output;

    public void sendEmail(String id) {
        log.info("构建发送邮件的消息.");
        MessageBean bean = new MessageBean(MessageBean.TYPE_EMAIL, id);
        Message<MessageBean> build = MessageBuilder.withPayload(bean).build();
        output.send(build);
        log.info("成功将消息发送至消息队列.");
    }

    public void sendImage(String id) {
        log.info("构建发送图片的消息.");
        MessageBean bean = new MessageBean(MessageBean.TYPE_IMAGE, id);
        Message<MessageBean> build = MessageBuilder.withPayload(bean).build();
        output.send(build);
        log.info("成功将消息发送至消息队列.");
    }

}
