1. SSO是什么
    1.1 单点登录(SingleSignOn，SSO)，就是通过用户的一次性鉴别登录。
    一处登录，处处登录；一处注销，处处注销
    1.2 就是在多个应用系统中，用户只需要登录一次就可以访问所有相互信任的应用系统，
    即用户只需要记住一组用户名和密码就可以登录所有有权限的系统
2. 什么场景下会用到
    2.1 一个系统由多个业务子系统或独立的子系统组成，用户在使用一个系统时会与其它子系统
    有交互，同一个用户请求不同的子系统时，只需要登录一次，就可以访问所有关联子系统，
    不需要每访问一个子系统时都登录一次
3. 如何实现sso单点登录
    3.1 基于cas标准，结合OATH2认证标准实现
    3.2 cas标准请求流程
        3.2.1 用户通过浏览器点击按钮访问业务子系统APP，请求URL
        3.2.2 业务子系统APP识别用户请求url未包含JSESSIONID，未授权，
              向浏览器发送重定向URL\login?APP_ID，重定向至cas服务器进行授权
        3.2.3 浏览器接受重定向URL\login?APP_ID，向cas服务器发起认证登录请求
        3.2.4 cas服务器未通过TGT找到当前用户的session信息，向浏览器返回登录表单
              LOGIN_FORM.HTML
        3.2.5 浏览器展示登录表单页面，用户录入登录信息，点击登录按钮
              请求URL\login?user+password+APP_ID
        3.2.6 登录请求发送至cas服务器，检验密码通过后用户认证通过，
              同时生成认证中心CAS session，并生成与cookie关联的TGT，
              TGT是cas session的主键key，作为用户的SSO session，
              将TGT设置到浏览器cookie中，并指示浏览器重定向到URL+TGT
        3.2.7 浏览器带着TGT，向URL+TGT发送请求，请求业务子系统APP
        3.2.8 业务子系统接受到请求，带着ticket去cas服务器校验是否授权
        3.2.9 cas服务器接受到请求，校验ticket通过，返回成功信息，并返回用户认证信息
        3.2.10 业务子系统APP收到CAS返回的成功信息后，为用户生成SESSION，并将JSESSIONID
               设置到浏览器cookie中，向浏览器发送重定向地址URL并将URL中的TGT信息剔除
               掉，防止TGT信息直接展示在浏览器的地址栏中
        3.2.11 浏览器带着cookie中的JSESSIONID访问业务子系统APP，请求URL?JSESSIONID,
               业务子系统校验JSESSIONID通过后，将用户URL需要的资源返回给用户
        3.2.12 用户在浏览器看到请求的数据展示出来
        3.2.13 用户第二次请求业务子系统APP，请求URL?JSESSIONID
        3.2.14 业务子系统APP校验session有效，返回访问URL所需得资源
        3.2.15 用户第一次访问业务子系统APP2，请求URL2?JSESSIONID
        3.2.16 业务子系统APP2，未找到session，向浏览器发送重定向URL\login?APP_ID
        3.2.17 浏览器接受重定向URL\login?APP_ID+TGT，向cas服务器发起认证登录请求
        3.2.18 cas服务器，校验TGT，通过校验，向浏览器发送重定向地址URL+TGT
        3.2.19 浏览器重定向至业务子系统APP2，请求URL+TGT
        3.2.20 业务子系统APP2带着TGT去cas认证，认证通过
        3.2.21 业务子系统APP2收到认证成功响应，为用户生成SESSION，并将JSESSIONID
               设置到浏览器cookie中，向浏览器发送重定向地址URL并将URL中的TGT信息剔除
               掉，防止TGT信息直接展示在浏览器的地址栏中
        3.2.22 浏览器带着cookie中的JSESSIONID访问业务子系统APP2，请求URL?JSESSIONID,
               业务子系统校验JSESSIONID通过后，将用户URL需要的资源返回给用户
    3.3 OAUTH2 标准
        3.3.1  OAUTH2 中规定了四个角色，CLIENT, RESOURCE OWNER, AUTHORIZATION SEVER,
               RESOURCE SERVER, 四个角色之间得交互流程
                   +--------+                               +---------------+
                   |        |--(A)- Authorization Request ->|   Resource    |
                   |        |                               |     Owner     |
                   |        |<-(B)-- Authorization Grant ---|               |
                   |        |                               +---------------+
                   |        |
                   |        |                               +---------------+
                   |        |--(C)-- Authorization Grant -->| Authorization |
                   | Client |                               |     Server    |
                   |        |<-(D)----- Access Token -------|               |
                   |        |                               +---------------+
                   |        |
                   |        |                               +---------------+
                   |        |--(E)----- Access Token ------>|    Resource   |
                   |        |                               |     Server    |
                   |        |<-(F)--- Protected Resource ---|               |
                   +--------+                               +---------------+
               
                  (A)  The client requests authorization from the resource owner.  The
                       authorization request can be made directly to the resource owner
                       (as shown), or preferably indirectly via the authorization
                       server as an intermediary.
               
                  (B)  The client receives an authorization grant, which is a
                       credential representing the resource owner's authorization,
                       expressed using one of four grant types defined in this
                       specification or using an extension grant type.  The
                       authorization grant type depends on the method used by the
                       client to request authorization and the types supported by the
                       authorization server.
               
                  (C)  The client requests an access token by authenticating with the
                       authorization server and presenting the authorization grant.
               
                  (D)  The authorization server authenticates the client and validates
                       the authorization grant, and if valid, issues an access token.
               
               
               
               
               
               
               
               
               Hardt                        Standards Track                    [Page 7]
               
               RFC 6749                        OAuth 2.0                   October 2012
               
               
                  (E)  The client requests the protected resource from the resource
                       server and authenticates by presenting the access token.
               
                  (F)  The resource server validates the access token, and if valid,
                       serves the request.
4. 技术实现
    4.1 采用SpringSecurityCas框架实现cas标准
    4.2 采用SpringSecurityOauth2框架实现Oauth2标准  Reference https://projects.spring.io/spring-security-oauth/docs/oauth2.html
        4.2.1 OAUTH2 Provider实现
                4.2.1.1 Authorization Service Provider 认证服务提供者
                    AuthorizationEndPoint is used to service requests for authorization. Default URL: /oauth/authorize
                    TokenEndpoint is used to service requests for access tokens. Default URL: /oauth/token
                    4.2.1.1.1 Configuration
                        1) 在授权服务器中，创建配置类（添加注解@Configuration && @EnableAuthorizationServer），并实现AuthorizationServerConfigurer接口
                        2) 在configure(ClientDetailsServiceConfigurer clientDetailsServiceConfigurer)方法中配置客户端client详情
                        3) 在configure(AuthorizationServerSecurityConfigurer authorizationServerSecurityConfigurer)方法中配置token访问端点的限制，如设置密码加密类
                        4) 在configure(AuthorizationServerEndpointsConfigurer authorizationServerEndpointsConfigurer)方法中配置token的访问端点，如获取token和刷新token的URL，以及token的生成和存储service
                4.2.1.2 Resource Service Provider 资源服务提供者
                    The following filter is required to implement an OAuth 2.0 Resource Server:
                    The OAuth2AuthenticationProcessingFilter is used to load the Authentication for the request given an authenticated access token
        4.2.2 OAUTH2 Client实现
