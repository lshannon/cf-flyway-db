# Spring Boot + FlyWay + JPA + H2 (Dev) + Postgres on PCF

The goal of this sample is to show how develop locally on a JPA + Flyway Spring Boot application using H2. The deployment will be Postgres on the Cloud/PCF.

My over all conclusion is to not use embedded DB's for development/ local testing. Test against Postgres locally. I will explain this at the end.

But for now, here is how I decided H2 was the embedded DB I wanted to use.

## Selecting an Embedded DB

### H2

My development environment is Linux: https://github.com/lshannon/ASUS-K501U-Ubuntu-Set-Up

Intially something strange was going on with H2 on Linux as can be seen with the class loading error below.

I wrestled with this extensively and could not figure out a way around it (tried adding more Jars, playing with all the new Spring Boot 1.4.1 testing annotations, etc).

Sadly it turned out to be a corrupt Jar in my local Maven repo (thanks to Stephane Nicol of the Spring team for pointing out the obvious)

Here is the error:

```shell

Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'dataSource': Invocation of init method failed; nested exception is java.lang.IllegalStateException: Cannot determine embedded database for tests. If you want an embedded database please put a supported one on the classpath.
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1583)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:545)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:482)
	at org.springframework.beans.factory.support.AbstractBeanFactory$1.getObject(AbstractBeanFactory.java:306)
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:230)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:302)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:202)
	at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:207)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1128)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1056)
	at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:835)
	at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:741)
	at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:189)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1148)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1051)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:510)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:482)
	at org.springframework.beans.factory.support.AbstractBeanFactory$1.getObject(AbstractBeanFactory.java:306)
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:230)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:302)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:197)
	at org.springframework.beans.factory.support.ConstructorResolver.instantiateUsingFactoryMethod(ConstructorResolver.java:372)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.instantiateUsingFactoryMethod(AbstractAutowireCapableBeanFactory.java:1128)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1023)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:510)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:482)
	at org.springframework.beans.factory.support.AbstractBeanFactory$1.getObject(AbstractBeanFactory.java:306)
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:230)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:302)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:197)
	at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1076)
	at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:851)
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:541)
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:761)
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:371)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:315)
	at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:111)
	at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:98)
	at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:116)
	at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:83)
	at org.springframework.test.context.web.ServletTestExecutionListener.setUpRequestContextIfNecessary(ServletTestExecutionListener.java:189)
	at org.springframework.test.context.web.ServletTestExecutionListener.prepareTestInstance(ServletTestExecutionListener.java:131)
	at org.springframework.test.context.TestContextManager.prepareTestInstance(TestContextManager.java:230)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.createTest(SpringJUnit4ClassRunner.java:228)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner$1.runReflectiveCall(SpringJUnit4ClassRunner.java:287)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.methodBlock(SpringJUnit4ClassRunner.java:289)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.runChild(SpringJUnit4ClassRunner.java:247)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.runChild(SpringJUnit4ClassRunner.java:94)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.springframework.test.context.junit4.statements.RunBeforeTestClassCallbacks.evaluate(RunBeforeTestClassCallbacks.java:61)
	at org.springframework.test.context.junit4.statements.RunAfterTestClassCallbacks.evaluate(RunAfterTestClassCallbacks.java:70)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.run(SpringJUnit4ClassRunner.java:191)
	at org.apache.maven.surefire.junit4.JUnit4Provider.execute(JUnit4Provider.java:283)
	at org.apache.maven.surefire.junit4.JUnit4Provider.executeWithRerun(JUnit4Provider.java:173)
	at org.apache.maven.surefire.junit4.JUnit4Provider.executeTestSet(JUnit4Provider.java:153)
	at org.apache.maven.surefire.junit4.JUnit4Provider.invoke(JUnit4Provider.java:128)
	at org.apache.maven.surefire.booter.ForkedBooter.invokeProviderInSameClassLoader(ForkedBooter.java:203)
	at org.apache.maven.surefire.booter.ForkedBooter.runSuitesInProcess(ForkedBooter.java:155)
	at org.apache.maven.surefire.booter.ForkedBooter.main(ForkedBooter.java:103)
Caused by: java.lang.IllegalStateException: Cannot determine embedded database for tests. If you want an embedded database please put a supported one on the classpath.
	at org.springframework.util.Assert.state(Assert.java:392)
	at org.springframework.boot.test.autoconfigure.orm.jpa.TestDatabaseAutoConfiguration$EmbeddedDataSourceFactory.getEmbeddedDatabase(TestDatabaseAutoConfiguration.java:189)
	at org.springframework.boot.test.autoconfigure.orm.jpa.TestDatabaseAutoConfiguration$EmbeddedDataSourceFactoryBean.afterPropertiesSet(TestDatabaseAutoConfiguration.java:154)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1642)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1579)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:545)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:482)
	at org.springframework.beans.factory.support.AbstractBeanFactory$1.getObject(AbstractBeanFactory.java:306)
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:230)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:302)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:202)
	at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:207)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1128)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1056)
	at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:835)
	at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:741)
	at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:189)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1148)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1051)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:510)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:482)
	at org.springframework.beans.factory.support.AbstractBeanFactory$1.getObject(AbstractBeanFactory.java:306)
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:230)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:302)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:197)
	at org.springframework.beans.factory.support.ConstructorResolver.instantiateUsingFactoryMethod(ConstructorResolver.java:372)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.instantiateUsingFactoryMethod(AbstractAutowireCapableBeanFactory.java:1128)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1023)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:510)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:482)
	at org.springframework.beans.factory.support.AbstractBeanFactory$1.getObject(AbstractBeanFactory.java:306)
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:230)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:302)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:197)
	at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1076)
	at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:851)
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:541)
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:761)
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:371)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:315)
	at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:111)
	at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:98)
	at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:116)
	at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:83)
	at org.springframework.test.context.web.ServletTestExecutionListener.setUpRequestContextIfNecessary(ServletTestExecutionListener.java:189)
	at org.springframework.test.context.web.ServletTestExecutionListener.prepareTestInstance(ServletTestExecutionListener.java:131)
	at org.springframework.test.context.TestContextManager.prepareTestInstance(TestContextManager.java:230)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.createTest(SpringJUnit4ClassRunner.java:228)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner$1.runReflectiveCall(SpringJUnit4ClassRunner.java:287)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.methodBlock(SpringJUnit4ClassRunner.java:289)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.runChild(SpringJUnit4ClassRunner.java:247)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.runChild(SpringJUnit4ClassRunner.java:94)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.springframework.test.context.junit4.statements.RunBeforeTestClassCallbacks.evaluate(RunBeforeTestClassCallbacks.java:61)
	at org.springframework.test.context.junit4.statements.RunAfterTestClassCallbacks.evaluate(RunAfterTestClassCallbacks.java:70)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.run(SpringJUnit4ClassRunner.java:191)
	at org.apache.maven.surefire.junit4.JUnit4Provider.execute(JUnit4Provider.java:283)
	at org.apache.maven.surefire.junit4.JUnit4Provider.executeWithRerun(JUnit4Provider.java:173)
	at org.apache.maven.surefire.junit4.JUnit4Provider.executeTestSet(JUnit4Provider.java:153)
	at org.apache.maven.surefire.junit4.JUnit4Provider.invoke(JUnit4Provider.java:128)
	at org.apache.maven.surefire.booter.ForkedBooter.invokeProviderInSameClassLoader(ForkedBooter.java:203)
	at org.apache.maven.surefire.booter.ForkedBooter.runSuitesInProcess(ForkedBooter.java:155)
	at org.apache.maven.surefire.booter.ForkedBooter.main(ForkedBooter.java:103)
```
Once I deleted the corrupt jar and down loaded a new one, the test successed.

#### Configuring H2 for Test

In my application.yml I have a profile for Cloud and nothing in the way of a Data Source for 'default'.

```yml

spring:
  jpa:
    hibernate:
      naming:
        strategy: org.hibernate.cfg.DefaultNamingStrategy
    show-sql: true
  datasource:
    username: postgres
    password: postgres
    url: jdbc:postgresql://localhost:5432/test
  profiles:
    active: cloud

```
When the Spring Boot application is pushed up the credentials in the from the Postgres managed DB in PCF are injected into the datasource values in the Cloud.

H2 is the winning option.

### Derby

Derby does not have the SQL support one might hope for. Specifically IF EXIST is missing. If feel like this could be common in Flyway migrations. Derby is out.

```shell

Caused by: org.apache.derby.iapi.error.StandardException: Syntax error: Encountered "EXISTS" at line 1, column 18.
	at org.apache.derby.iapi.error.StandardException.newException(Unknown Source)
	at org.apache.derby.iapi.error.StandardException.newException(Unknown Source)
	at org.apache.derby.impl.sql.compile.ParserImpl.parseStatementOrSearchCondition(Unknown Source)
	at org.apache.derby.impl.sql.compile.ParserImpl.parseStatement(Unknown Source)
	at org.apache.derby.impl.sql.GenericStatement.prepMinion(Unknown Source)
	at org.apache.derby.impl.sql.GenericStatement.prepare(Unknown Source)
	at org.apache.derby.impl.sql.conn.GenericLanguageConnectionContext.prepareInternalStatement(Unknown Source)
	at org.apache.derby.impl.jdbc.EmbedStatement.execute(Unknown Source)
	at org.apache.derby.impl.jdbc.EmbedStatement.execute(Unknown Source)
	at org.flywaydb.core.internal.dbsupport.JdbcTemplate.executeStatement(JdbcTemplate.java:238)
	at org.flywaydb.core.internal.dbsupport.SqlScript.execute(SqlScript.java:114)
	at org.flywaydb.core.internal.resolver.sql.SqlMigrationExecutor.execute(SqlMigrationExecutor.java:71)
	at org.flywaydb.core.internal.command.DbMigrate$5.doInTransaction(DbMigrate.java:284)
	at org.flywaydb.core.internal.command.DbMigrate$5.doInTransaction(DbMigrate.java:282)
	at org.flywaydb.core.internal.util.jdbc.TransactionTemplate.execute(TransactionTemplate.java:72)
	at org.flywaydb.core.internal.command.DbMigrate.applyMigration(DbMigrate.java:282)
	at org.flywaydb.core.internal.command.DbMigrate.access$800(DbMigrate.java:46)
	at org.flywaydb.core.internal.command.DbMigrate$2.doInTransaction(DbMigrate.java:207)
	at org.flywaydb.core.internal.command.DbMigrate$2.doInTransaction(DbMigrate.java:156)
	at org.flywaydb.core.internal.util.jdbc.TransactionTemplate.execute(TransactionTemplate.java:72)
	at org.flywaydb.core.internal.command.DbMigrate.migrate(DbMigrate.java:156)
	at org.flywaydb.core.Flyway$1.execute(Flyway.java:1059)
	at org.flywaydb.core.Flyway$1.execute(Flyway.java:1006)
	at org.flywaydb.core.Flyway.execute(Flyway.java:1418)
	at org.flywaydb.core.Flyway.migrate(Flyway.java:1006)
	at org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer.afterPropertiesSet(FlywayMigrationInitializer.java:66)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1642)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1579)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:545)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:482)
	at org.springframework.beans.factory.support.AbstractBeanFactory$1.getObject(AbstractBeanFactory.java:306)
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:230)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:302)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:197)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:296)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:197)
	at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1076)
	at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:851)
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:541)
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:761)
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:371)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:315)
	at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:111)
	at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:98)
	at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:116)
	at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:83)
	at org.springframework.test.context.web.ServletTestExecutionListener.setUpRequestContextIfNecessary(ServletTestExecutionListener.java:189)
	at org.springframework.test.context.web.ServletTestExecutionListener.prepareTestInstance(ServletTestExecutionListener.java:131)
	at org.springframework.test.context.TestContextManager.prepareTestInstance(TestContextManager.java:230)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.createTest(SpringJUnit4ClassRunner.java:228)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner$1.runReflectiveCall(SpringJUnit4ClassRunner.java:287)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.methodBlock(SpringJUnit4ClassRunner.java:289)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.runChild(SpringJUnit4ClassRunner.java:247)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.runChild(SpringJUnit4ClassRunner.java:94)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.springframework.test.context.junit4.statements.RunBeforeTestClassCallbacks.evaluate(RunBeforeTestClassCallbacks.java:61)
	at org.springframework.test.context.junit4.statements.RunAfterTestClassCallbacks.evaluate(RunAfterTestClassCallbacks.java:70)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.run(SpringJUnit4ClassRunner.java:191)
	at org.apache.maven.surefire.junit4.JUnit4Provider.execute(JUnit4Provider.java:283)
	at org.apache.maven.surefire.junit4.JUnit4Provider.executeWithRerun(JUnit4Provider.java:173)
	at org.apache.maven.surefire.junit4.JUnit4Provider.executeTestSet(JUnit4Provider.java:153)
	at org.apache.maven.surefire.junit4.JUnit4Provider.invoke(JUnit4Provider.java:128)
	at org.apache.maven.surefire.booter.ForkedBooter.invokeProviderInSameClassLoader(ForkedBooter.java:203)
	at org.apache.maven.surefire.booter.ForkedBooter.runSuitesInProcess(ForkedBooter.java:155)
	at org.apache.maven.surefire.booter.ForkedBooter.main(ForkedBooter.java:103)



```


### HSQL

With HSQL, again SQL support was lacking. I like to use the Serial type for my Domain objects. It seems to work great with Postgres Sequences, which the flyway script can generate.

```shell

Migration V1__init.sql failed
-----------------------------
SQL State  : 42509
Error Code : -5509
Message    : type not found or user lacks privilege: SERIAL
Location   : db/migration/V1__init.sql (/mnt/data/source/cf-flyway-db/cf-flyway-db/target/classes/db/migration/V1__init.sql)
Line       : 7
Statement  : CREATE TABLE customer (
	id serial primary key,
	name varchar(100)
)


```

### Conclusion

H2 works well, but to make like easier I would be tempted to just have Postgres in the application.yml and remove the embedded databases from the pom.xml file.

Here is why I like to test on Postgres:

1. With a persistent DB I was able to configure the maven flyway plugin. This allows me to run some of those commands, such as 'clean'

```shell
 <plugin>
 	<groupId>org.flywaydb</groupId>
	<artifactId>flyway-maven-plugin</artifactId>
	<version>4.0.3</version>
	<configuration>
		<url>jdbc:postgresql://localhost:5432/test</url>
		<user>postgres</user>
		<password>postgres</password>
	</configuration>

</plugin>
```

2. SQL scripts have a higher likely hood of working in the Cloud having developed them against Postgres locally. I am not too confident with Embedded SQL support vs what Postgres can do

3. Having a persistent DB gives me a chance to inspect after the test with commonly used DB tool (PgAdmin for example)




