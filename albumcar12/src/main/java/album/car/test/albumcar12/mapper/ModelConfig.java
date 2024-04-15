package album.car.test.albumcar12.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ModelConfig {
    
    @Bean
    public ModelMapper getModel(){
        return new ModelMapper();
    }


}
