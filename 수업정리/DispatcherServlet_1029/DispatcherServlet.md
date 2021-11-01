# DispatcherServlet

스프링 MVC는 모든 요청(Request)을 받아 실제 작업은 다른 컴포넌트로 위임하는 `DispatcherServlet` 을 두어 **프론트 컨트롤러 패턴**으로 디자인되었습니다.

`DispatcherServlet` 은 `Servlet` 사양에 맞게 선언되어야 하고 매핑되어야 합니다.

스프링에서는 `web.xml` 파일에 정의하고, 요새는 스프링과 스프링부트에서는 자바 설정을 사용해서 정의합니다.

결과적으로, `DispatcherServlet` 은 스프링 설정을 사용하여 위임할 컴포넌트를 찾습니다. (해당 컴포넌트는 Request Mapping, View Resolution, Exception handling, ...의 작업을 합니다.)

아래 코드는 자바 스프링 설정을 이용한 `DispatcherServlet` 의 생성과정입니다.

```java
public class MyWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletCxt) {

        // Load Spring web application configuration
        AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
        ac.register(AppConfig.class);
        ac.refresh();

        // Create and register the DispatcherServlet
        DispatcherServlet servlet = new DispatcherServlet(ac);
        ServletRegistration.Dynamic registration = servletCxt.addServlet("app", servlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/app/*");
    }
}
```

아래 코드는 `web.xml` 을 이용한 `DispatcherServlet` 의 생성 과정입니다.

```xml
<web-app>
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>

    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>/WEB-INF/app-context.xml</param-value>
    </context-param>

    <servlet>
        <servlet-name>app</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value></param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>app</servlet-name>
        <url-pattern>/app/*</url-pattern>
    </servlet-mapping>
</web-app>
```

스프링부트는 스프링과 다른 초기화 과정을 거칩니다.

스프링에서는 위와 같이 `Servlet Container` 의 생명 주기에 `listener`로 연결하고, 스프링부트는 스프링 설정을 이용하여 스프링 애플리케이션과 내장된 `Servlet Container` 를 실행시킵니다.

`DispatcherServlet` 에 대한 간단한 Overview를 마칩니다.

## Context 계층 구조

`DispatcherServlet` 은 일반적으로 계층 구조를 갖습니다.

많은 애플리케이션에는 단일 `DispatcherServlet`, 단일 `WebApplicationContext` 를 갖는 간단한 스타일로 만듭니다.

`WebApplicationContext` 를 루트 컨텍스트(부모 컨텍스트)라고 부르고 `DispatcherServlet` 은 자식 컨텍스트 구조를 갖습니다.

`DispatcherServlet` 은 요청에 대응할 수 있는 `Controller`, `ViewResolver`, `HandlerMapping` 과 같은 스프링 빈(Beans)을 구성하고, `WebApplicationContext` 에는 모든 서블릿이 공유할 수 있는 `Service`, `Repository` 와 같은 스프링 빈을 구성합니다.

## 특별한 빈 타입

- HandlerMapping
  - 요청(Request)을 handler(=controller)로 매핑합니다. 전/후 처리를 위한 `interceptor` 리스트를 포함합니다.
  - 매핑은 몇 가지 기준을 기반으로 합니다.
  - 두 가지 핵심 구현체가 있습니다. `RequestMappingHandlerMapping` 은 `@RequestMapping` 애노테이션을 지원하고, `SimpleUrlHandlerMapping` 은 URI 경로 패턴으로 명시적인 핸들러 등록 기능을 지원합니다.
- HandlerAdapter
  - `DispatcherServlet` 이 요청에 매핑된 handler를 호출할 수 있도록 도와줍니다. 실질적인 컨트롤러 호출 방식은 `DispatcherServlet` 이 몰라도 되게 해줍니다.
- HandlerExceptionResolver
  - Exception을 해결하기 위한 전략으로 예외가 발생했을 때, 컨트롤러나 HTML Error view 또는 다른 것으로 결정해줍니다.
- ViewResolver
  - 컨트롤러에서 리턴된 문자열 기반의 `View` 이름을 기준으로 실제로 렌더링할 뷰 객체를 결정해줍니다.
- LocaleResolver
  - 국제화된 View를 제공하기위해서 클라이언트의 타임존과 `Locale` 을 결정해줍니다.
- ThemeResolver
  - 웹 애플리케이션에서 사용할 수 있는 테마를 결정해줍니다.
- MultipartResolver
  - 멀티파트 파싱 라이브러리를 이용하여 멀티파트 요청(파일업로드와 같은 요청)을 파싱하기위한 추상화
- FlashMapManager
  - 한 요청에서 다른 요청(redirect)으로 속성을 전달하는데 사용할 수 있는 Input, Output 을 FlashMap에 저장하고 검색합니다.

## Web MVC Config

위에서 설명한 요청을 처리하기 위해 필요한 특별한 빈 타입 리스트에 애플리케이션은 인프라 Bean을 선언할 수 있습니다.

`DispatcherServlet` 은 각 특별한 빈에 대하여 `WebApplicationContext` 를 검사합니다. 만약 매칭되는 빈이 없으면 DispatcherServlet.properties 파일에 나열된 디폴트 타입으로 대체됩니다.

스프링부트는 MVC 자바 설정에 의존하여 Spring MVC를 구성하고 많은 편리한 옵션을 제공합니다.

## Processing

`DispatcherServlet` 에서 진행되는 과정

- `WebApplicationContext` 를 컨트롤러 같은 프로세스의 다른 요소가 사용할 수 있는 속성으로 요청에 바인딩합니다. 기본적으로 `DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE` 라는 키에 바인딩됩니다. (**request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, getWebApplicationContext()**)
- `locale resolver` 는 프로세스의 요소가 뷰 렌더링, 데이터 준비, ... 등을 위한 요청을 처리할 때 사용할 locale(지역 정보)을 결정할 수 있도록 요청에 바인딩됩니다. (**request.setAttribute(LOCALE_RESOLVER_ATTRIBUTE, this.localeResolver)**)
- `theme resolver` 는 사용할 테마를 결정할 수 있도록 요청에 바인딩됩니다. (**request.setAttribute(THEME_RESOLVER_ATTRIBUTE, this.themeResolver)**)
- 요청이 멀티파트인지 검사하고 멀티파트 요청이라면 프로세스의 다른 요소에 의해 추가 처리가 될 수 있도록 `MultipartHttpServletRequest` 로 래핑됩니다.
- 앞선 과정이 끝나면, 요청을 처리할 적절한 컨트롤러(핸들러) 검색합니다. 핸들러를 찾으면 이 핸들러에게 요청을 전달하기 위해 적당한 `HandlerAdapter` 를 가져와서 실행시킵니다. 물론 전/후처리기가 있으면 요청을 컨트롤러로 위임하기 전/후에 실행됩니다.
- 적당한 뷰를 찾고 model에 있는 데이터를 매핑합니다.
- 결과를 응답(Response)에 담아 넣습니다.

`WebApplicationContext`에 선언된 `HandlerExceptionResolver` 빈은 요청이 처리되는 동안 발생한 예외를 결정하는데 사용됩니다.

`DispatcherServlet` 은 Servlet API에 지정된대로 `last-modification-date` 리턴도 지원합니다.

특수 요청에 대한 마지막 수정 날짜를 결정하는 과정은 다음과 같이 간단합니다.

→ `DispatcherServlet` 은 적절한 핸들러 매핑을 검색하고 발견된 핸들러가 LastModified 인터페이스를 구현하는지 테스트합니다. LastModified 인터페이스의 `getLastModified(request)` 메서드의 결과같이 클라이언트로 리턴됩니다.

`DispatcherServlet` 은 초기화 파라미터(`init-param`)를 이용하여 입맛에 맞게 커스터마이징할 수도 있습니다.

## Interception

모든 `HandlerMapping` 의 구현체는 특정 요청에 특정 기능을 적용하기 좋은 핸들러 인터셉터를 지원합니다.

인터셉터는 org.springframework.web.servlet 패키지에있는 `HandlerInterceptor` 인터페이스를 구현해야 합니다. (전/후처리에 좋은 세 가지 메서드가 있음)

- `preHandle(...)` : 컨트롤러(=핸들러)를 실행하기 전에 실행
- `postHandle(...)` : 컨트롤러를 실행하고난 후에 실행
- `afterCompletion(...)` : 온전하게 요청이 끝난 후에 실행

`preHandle(...)` 메소드는 boolean 값을 리턴합니다. 이것을 사용해서 실행체인을 그만둘지, 계속할지를 결정할 수 있습니다.

만약 `true` 를 리턴한다면, 실행체인은 계속됩니다. 만약 `false` 를 리턴한다면, `DispatcherServlet` 은 인터셉터가 스스로 요청을 잘 처리했다고 여기고 실제 실행 체인에서 다른 인터셉터와 실제 컨트롤러를 계속 실행하지 않습니다.

`postHandle(...)` 메소드는 실제 컨트롤러에서 이미 응답(Response)가 작성되기 때문에 상대적으로 덜 유용합니다. (헤더 추가하는 등의 작업을 하기엔 늦음)

그렇기 때문에 그런 부분을 해결하려면 `ResponseBodyAdvice` 를 구현하고 이를 `ControllerAdvice` 빈으로 선언하거나 `RequestMappingHandlerAdapter` 에서 직접 구성하면 된다.

## Exceptions

요청을 매핑하는 중에 예외가 발생하거나 실제 컨트롤러로부터 예외가 발생하면 `DispatcherServlet` 은 `HandlerExceptionResolver` 빈의 체인에 예외를 처리를 위임한다.

사용할 수 있는 `HandlerExceptionResolver` 구현체는 다음과 같습니다.

(체인 순서 : ExceptionHandlerExceptionResolver → ResponseStatusExceptionResolver → DefaultHandlerExceptionResolver)

- **SimpleMappingExceptionResolver** : 예외 클래스 명과 에러 뷰 이름을 매핑합니다. 브라우저에서 에러페이지를 렌더링할 때 유용합니다. (Web.xml 같은데다가 error-page하고 지정한 것을 얘가 처리해준다. 예외 이름과 뷰 이름을 하나의 쌍으로 정의하고 예외 발생시 처리한다.)
- **ExceptionHandlerExceptionResolver** : `@Controller` 또는 `@ControllerAdvice` 안에 있는 `@ExceptionHandler` 애노테이션이 적용된 메소드에 의해 예외가 처리됩니다.
- **ResponseStatusExceptionResolver** : `@ResponseStatus` 애노테이션으로 예외를 처리하고 값을 기반으로 HTTP 상태 코드에 매핑합니다. (@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Permission Denied") 이런 적용을 해봤을 것이다. 예외발생시에 이 애노테이션이 적용된 메소드로 예외를 처리한다.)
- **DefaultHandlerExceptionResolver** : Spring MVC가 발생한 예외를 해결하고 이것을 HTTP 상태 코드에 매핑합니다. 그러니까 스프링에서 최소한의 예외처리는 해주는 객체로 만들어 놓은 것이다. (페이지가 없으면 뜨는 404 Not Found 예외같은 것을 내가 정의한 적 없는데 스프링 애플리케이션에서 페이지를 못 찾으면 뜬다. 이것을 DefaultHandlerExceptionResolver가 처리해준다.)

예외 처리를 커스터마이징하고 싶으면 위의 클래스들 처럼 `HandlerExceptionResolver`를 구현한 빈을 생성하고 체인의 순서를 정해주면 된다. 우선순위가 높을수록 나중에 위치합니다.

```kotlin
public interface HandlerExceptionResolver {

    @Nullable
    ModelAndView resolveException(
            HttpServletRequest request, HttpServletResponse response, @Nullable Object handler, Exception ex);

}
```

- `ModelAndView` 는 에러 뷰를 가리킵니다.
- 해당 ExceptionResolver에서 예외가 처리되었다면 `ModelAndView` 는 비어있게됩니다.
- 예외가 처리되지 못하고 아직 남아있다면 `ModelAndView` 는 `null` 입니다. 다음 ExceptionResolver까지 계속 타고가다가 끝까지 처리가 안되면 결국에는 `Servlet Container` 까지 예외가 전파될 수 있습니다.

## View Resolution

Spring MVC는 특정 뷰 기술에 의존하지 않고 브라우저에서 모델을 렌더링할 수 있는 `ViewResolver` 와 `View` 인터페이스를 정의합니다.

`ViewResolver` 는 뷰 이름과 실제 뷰와의 매핑을 제공합니다.

마찬가지로 `ViewResolver` 구현체를 살펴봅니다.

- **AbstractCachingViewResolver** : `AbstractCachingViewResolver` 의 하위 클래스는 결정하는 `View` 객체 인스턴스를 캐시합니다. 뷰를 캐싱하여 성능을 끌어올리는 역할을 하며 필요하면 캐시를 활성화하지 않을 수 있습니다. 대부분의 뷰리졸버들이 상속합니다.
- **XmlViewResolver** : DTD같은 XML로 쓰여진 설정 파일을 통한 뷰를 결정합니다.
- **ResourceBundleViewResolver** : `ResourceBundle` 에 bean definitions를 사용하여 결정합니다. 기본적으로 views.properties 파일을 사용합니다. 결정해야하는 각 뷰에 대해 [viewname].class 속성 값을 뷰 클래스로 사용하고 [viewname].url의 값을 뷰의 URL로 사용합니다.
- **UrlBasedViewResolver** : 명시적인 정의없이 URL에 대한 논리적 뷰 이름으로 결정합니다.
- **InternalResourceViewResolver** : Servlet, JSP같은 `InternalResourceView` 를 지원합니다.
- **FreeMarkerViewResolver** : `FreeMarkerView` 를 지원합니다.
- **ContentNegotiatingViewResolver** : 요청 파일 이름이나 `Accept` 헤더를 기준으로 뷰를 결정합니다.

**Handling**

더 다양한 뷰 리졸버 빈을 선언하여 뷰 리졸버들을 체인으로 엮을 수 있습니다.

적당한 뷰를 찾을 수 없음을 나타내기 위해 `null` 을 리턴할 수 있습니다.

**Redirecting**

`redirect:` prefix를 뷰 이름에 붙이면 리다이렉트를 실행시켜줍니다. UrlBasedViewResolver와 이를 상속한 하위 클래스는 prefix를 붙이면 리다이렉트가 필요하다는 명령으로 인식합니다.

`@ResponseStatus` 애노테이션을 달면 `RedirectView` 에서 설정한 응답 상태보다 우선하는 것을 주의해야합니다.

**Forwarding**

`forward:` prefix를 사용하면 궁극적으로 `UrlBasedViewResolver` 와 그 하위클래스에 의해 뷰가 결정됩니다.

forward를 사용하면 `InternalResourceView` 를 생성합니다.

**Content Negotiation**

`ContentNegotiationViewResolver` 는 뷰를 결정하지 않습니다. 다른 뷰리졸버에게 위임하고 클라이언트 요청에서 `Accept` 헤더 또는 쿼리 파라미터로부터 결정할 수 있습니다.

미디어 타입(`Content-Type`)을 비교하여 적절한 뷰를 찾습니다.

## Locale

Spring MVC에서도 국제화를 지원합니다.

`DispatcherServlet` 은 클라이언트의 locale을 사용하여 자동으로 메시지를 결정합니다. (`LocaleResolver`)

클라이언트로부터 요청이 오면, `DispatcherServlet` 은 locale resolver를 찾습니다. 찾으면 locale을 설정하려고 시도합니다.

`RequestContext.getLocale()` 메서드를 사용해서 locale resolver가 결정한 locale에 항상 접근할 수 있습니다.

locale resolver와 인터셉터는 `org.springframework.web.servlet.i18n 패키지에 정의되어 있습니다. 일반적인 방식으로 application context에 구성됩니다.

- Time Zone
  - `LocaleContextResolver` 인터페이스는 표준 시간대 정보를 포함할 수 있는 더 풍부한 LocaleContext를 제공할 수 있는 확장을 제공합니다. `RequestContext.getTimeZone()` 메서드를 사용하여 사용자의 TimeZone을 가져올 수 있습니다.
- AcceptHeaderLocaleResolver
  - locale resolver는 Request에서 `Accept-language` 헤더를 분석합니다. 보통 헤더 필드에는 클라이언트의 운영체제의 locale이 포함되어있습니다. 대신 이 리졸버는 표준시간대정보를 지원하지 않습니다.
- CookieLocaleResolver
  - `Cookie` 를 조사해서 `Locale` 이나 `TimeZone` 이 명시되어 있으면 그것을 사용합니다. 최초에는 한 번 setLocale() 메서드를 통해서 쿠키에 값을 저장해야합니다. 쿠키에 값이 존재하지 않으면 디폴트 설정을 따라가고 그 마저 안되면 `Accept-language` 를 따라갑니다.
- SessionLocaleResolver
  - `SessionLocaleResolver` 는 연관된 유저의 요청으로부터 얻은 세션에서 `Locale` 과 `TimeZone` 을 조회합니다. 마찬가지로 최초에 setLocale() 메서드를 통해서 세션에 Locale을 저장해야합니다. 세션에 값이 존재하지 않으면 디폴트 설정을 따라가고 그 마저 안되면 `Accept-language` 를 따라갑니다.
- LocaleChangeInterceptor
  - `LocaleChangeInterceptor` 를 이용하여 Locale 변경을 할 수 있습니다. 요청(Request)의 파라미터를 찾고 locale을 변경합니다.

## Themes

스타일시트나 이미지같은 정적인 자원의 모음을 테마라고 합니다. `ThemeResolver`와 `ThemeSource` 를 적용하여 애플리케이션의 외적인 스타일을 꾸밀 수 있습니다.

실질적으로 테마에 대한 처리는 `ResourceBundleThemeSource` 로 위임하고 이것은 프로퍼티 파일을 참고하여 테마를 적용시켜줍니다. 프로퍼티 파일 내부는 다음과 같습니다.

```kotlin
styleSheet=/themes/cool/style.css
background=/themes/cool/img/coolBg.jpg
```

이렇게 프로퍼티 파일을 정의하고 난 후에 `DispatcherServlet` 은 `themeResolver` 라는 이름을 갖는 빈을 찾습니다. 그리고 그 themeResolver가 요청에 대하여 테마를 적용해줍니다. (잘 안 쓰이니 간단히 그렇구나하고 넘어갑니다.)

## Multipart Resolver

`MultipartResolver` 는 파일업로드가 포함된 멀티파트 요청을 파싱하는 것에 대한 전략입니다.

멀티파트 요청을 처리하기 위해서 `MultipartResolver` 라는 이름을 갖는 `MultipartResolver` 빈이 필요합니다.

`DispatcherServlet` 은 HTTP POST요청에 `content-type` 이 `multipart/form-data` 인 요청을 받았을 때 `MultipartResolver` 를 찾아 요청을 처리한다.

`MultipartResolver` 는 내용을 파싱하고 현재 요청(HttpServletRequest)에 래핑하여 해당 멀티파트 내용을 사용할 수 있도록 합니다.

