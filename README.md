Apache Shiro integration with Spring Boot
=========================================
[![Build Status](https://travis-ci.org/johntostring/spring-boot-shiro.svg?branch=master)](https://travis-ci.org/johntostring/spring-boot-shiro)

**Github：**[https://github.com/johntostring/spring-boot-shiro](https://github.com/johntostring/spring-boot-shiro)

## Usage

首先安装到本地仓库。

分支`v2.0`下为`2.0.0`版本，主要将Spring Boot升级到2.0.4.RELEASE，Shiro 升级到 1.4.0

`mvn clean install`

### pom.xml

```xml
<dependency>
    <groupId>com.millinch</groupId>
    <artifactId>spring-boot-shiro-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

### QuickStart
官方推荐采用YAML的方式配置，这里我也建议使用YAML来配置，一方面是因为在配置filter-chain-definitions时，通常我们是希望保证顺序的，使用properties的方式无法保证顺序(继承自Hashtable)。
后面将会列出配置的默认约定，按照你的需要，你只需要配置你想要修改的项就可以了。
关于SpringBoot详细使用方式，你可以查看[官方文档](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#common-application-properties)，或者通过[Github开源地址](https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples)查看一些示例项目。

首先，默认提供JdbcRealm 配置，不能满足需求的情况下再配置自定义Realm：
```yaml
shiro:
  realm-class:  #默认空，指定类全名com.your.company.YourRealm
```

**1. 配置DataSource**

以MySQL为例，添加好JDBC驱动依赖后，这是 Spring Boot 默认提供的方式配置DataSource：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/yourdbname
    username: #you know
    password: #you know
    driver-class-name: com.mysql.jdbc.Driver
```

**2. 开启JdbcRealm**

首先设shiro.realm.jdbc.enabled为true，否则就会查找自定义的realm-class，最后配置好SQL查询语句。

默认配置及描述：
```yaml
shiro:
  realm:
    jdbc:
      enabled: true
      authentication-query: SELECT password FROM sys_user where user_name = ? #根据用户名获取密码
      salt: no_salt #可选值：no_salt, crypt(源码中未实现), column(上面这个SQL中第二列中获取salt), external(需继承JdbcRealm重写getSaltForUser()方法)
      user-roles-query: SELECT r.code
                        FROM sys_user_role ur
                        LEFT JOIN sys_role r ON r.id = ur.role_id
                        LEFT JOIN sys_user u ON ur.user_id = u.id AND u.user_name = ? #根据用户名获取角色
      permissions-query: SELECT re.permission
                         FROM sys_role_resource rr
                         LEFT JOIn sys_resource re ON rr.resource_id = re.id
                         LEFT JOIN sys_role r ON rr.role_id = r.id
                         WHERE r.code = ? #根据角色获取权限
  login-url: /login #登录入口URL
  success-url: /index #登录成功跳转URL
  unauthorized-url: /unauthorized #当访问未授权页面时跳转至该URL，将为filter chain中的每个AuthorizationFilter设置跳转URL（如果目标没有指定）
  sign-in:
    user-param: username #用户名参数名称
    password-param: password #密码参数名称
    remember-me-param: rememberMe #记住我参数名称
  hash-iterations: 1 #加密迭代次数，强制设为至少1次（即使设置0或负数）
  hash-algorithm-name: MD5 #加密算法名称，如：MD2/SHA-1/SHA-256/SHA-384/SHA-512
  filters:
    demo: your.packagepath.to.DemoFilter
  filter-chain-definitions: #默认为空，一般如下配置
    /login: authc
    /logout: logout
    /favicon.ico: anon
    /index: anon
    /assets/**: anon
    /**: authc
```

**注意：在升级到Spring Boot 2+时我才发现`filter-chain-definitions`的配置中，key如果包含特殊字符会被强行去除掉，需要如下写法才可以：**

```yaml
filter-chain-definitions:
  '[/sayHi]': authc, demo
```

参考：https://stackoverflow.com/questions/34070987/escaping-a-dot-in-a-map-key-in-yaml-in-spring-boot/42285949

#### Cookie

```properties
shiro.cookie.cipher-key= #对称加密时使用(默认)，为空则Shrio自动提供一个
shiro.cookie.decryption-cipher-key= #非对称加密时必需
shiro.cookie.encryption-cipher-key= #非对称加密时必需
shiro.cookie.name=rememberMe
shiro.cookie.http-only=true #建议开启
shiro.cookie.max-age=31536000 #1年
shiro.cookie.secure=false #安全传输cookie
shiro.cookie.version=-1
```

#### Session
```properties
shiro.session.active-sessions-cache-name=shiro-acciveSessionCache
shiro.session.delete-invalid-sessions=true
shiro.session.global-session-timeout=36000 #Session超时时长，默认1小时
shiro.session.id-generator=org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator #可指定自定义的实现
shiro.session.validation-interval=36000 #每隔1小时验证Session
shiro.session.validation-scheduler-enabled=true #是否开启定时验证Session
```

**关于SessionValidationScheduler**

默认配置ExecutorServiceSessionValidationScheduler，这是一个JDK的并发包API的实现。
若在classpath发现Quartz，则自动使用QuartzSessionValidationScheduler。即添加dependency：
```xml
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-quartz</artifactId>
    <version>${shiro.version}</version>
</dependency>
```

最后，你也可以配置一个自定义的`SessionValidationScheduler`实现：
```java
@Bean(name = "sessionValidationScheduler")
public SessionValidationScheduler yourSessionValidationScheduler() {
    return new YourSessionValidationScheduler();
}
```

#### Cache
```properties
shiro.ehcache.cache-manager-config-file=classpath:org/apache/shiro/cache/ehcache/ehcache.xml #开启Ehcache时可指定
```

**CacheManager**

默认配置`MemoryConstrainedCacheManager`，不建议用于生产。
若在classpath发现`org.apache.shiro.cache.ehcache.EhCacheManager`，则自动使用EhCacheManager。即添加dependency：
```xml
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-ehcache</artifactId>
    <version>${shiro.version}</version>
</dependency>
```
同样地，CacheManager也可以配置自定义的实现，比如Redis等分布式缓存的实现，来覆盖默认配置，其他的如Realm、CredentialsMatcher、SessionDAO同样可配置Bean来覆盖默认配置。

**最后，会继续完善的，Demo也会抽时间做一个。有不足的地方请及时指出。**
