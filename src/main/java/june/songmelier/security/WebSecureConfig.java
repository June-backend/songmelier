package june.songmelier.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configurable
@RequiredArgsConstructor
public class WebSecureConfig extends WebSecurityConfigurerAdapter {

    //이걸로 안막으면 인증이 필요없는 부분도 필터를 탐 (forbidden으로 막히지는 않음)
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/**");
    }
}

