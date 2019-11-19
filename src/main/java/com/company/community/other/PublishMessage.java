package com.company.community.other;

import com.company.community.models.Publish;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PublishMessage {
   private Publish publish;

}
