package fastcampus.issueservice.config

import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@Configuration
class WebConfig(
    private val authUserHandlerArgumentResolver: AuthUserHandlerArgumentResolver
) : WebMvcConfigurationSupport() {

    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        // 아규먼트 리졸버 설정
        argumentResolvers.apply {
            // 스프링에서 내부적으로 HandlerMethodArgumentResolver 에 대해 등록한 리스트를 반복하여
            // 내부에서 supportsParameter 조건에 부합한다고 하면 하위에 있는 resolveArgument 를 통해
            // 컨트롤러의 인자로 들어가는 객체를 자동으로 넣어주게 됨.
            // 지금은 AuthUser
            add(authUserHandlerArgumentResolver)
        }
    }
}

@Component
class AuthUserHandlerArgumentResolver : HandlerMethodArgumentResolver {

    /**
     * HandlerMethodArgumentResolver
     * 컨트롤러의 인자로 들어오는 객체 또는 annotation 에 대해 supportsParameter 메서드를 통해 조건에 맞는다고 하면
     * resolveArgument 를 통해 객체 혹은 annotation 에 값 세팅하거나 조작하는 기능 제공하는 인터페이스.
     *
     * 나중에 JWT 인증 개발할 때, 스프링시큐리티나 인터셉터를 사용하지 않고
     * 좀 더 간단하게 연동하기 위하여 HandlerMethodArgumentResolver 를 구현하여 사용할 목적.
     */

    override fun supportsParameter(parameter: MethodParameter): Boolean =
        AuthUser::class.java.isAssignableFrom(parameter.parameterType)


    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {

        return AuthUser(
            userId = 1,
            username = "dummy-user", // 추후 인증 서버 개발 후 변경
        )
    }
}

data class AuthUser(
    val userId: Long,
    val username: String,
    val profileUrl: String? = null,
)
