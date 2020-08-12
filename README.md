# Cloud Foundry Firehose Prometheus Proxy
Proxy to be put in front of https://github.com/bosh-prometheus/firehose_exporter to handle custom metrics from sources like Spring Boot Actuator.

# Deployment Instructions
## Build the application
```
» ./mvnw package
...
[INFO] Results:
[INFO]
[INFO] Tests run: 11, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO]
[INFO] --- maven-jar-plugin:3.2.0:jar (default-jar) @ cf-prometheus-proxy ---
[INFO] Building jar: target/cf-prometheus-proxy-0.0.1-SNAPSHOT.jar
[INFO]
[INFO] --- spring-boot-maven-plugin:2.3.2.RELEASE:repackage (repackage) @ cf-prometheus-proxy ---
[INFO] Replacing main artifact with repackaged archive
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  6.759 s
[INFO] Finished at: 2020-08-12T17:31:38-04:00
[INFO] ------------------------------------------------------------------------
```

## Create user provided service, pointing to firehose exporter
```
» cf cups metric-endpoint -p '{"uri":"https://firehose-exporter.myapps.com/metrics"}'

Creating user provided service metric-endpoint in org prometheus-nozzle / space prod as admin...
OK
```
## Deploy application
```
» cf push
...
Waiting for app to start...

name:              prometheus-proxy
requested state:   started
routes:            prometheus-proxy.myapps.com
last uploaded:     Wed 12 Aug 17:34:30 EDT 2020
stack:             cflinuxfs3
buildpacks:        java_buildpack_offline

type:            web
instances:       1/2
memory usage:    1024M
start command:   JAVA_OPTS="-agentpath:$PWD/.java-buildpack/open_jdk_jre/bin/jvmkill-1.16.0_RELEASE=printHeapHistogram=1 -Djava.io.tmpdir=$TMPDIR -XX:ActiveProcessorCount=$(nproc)
                 -Djava.ext.dirs=$PWD/.java-buildpack/container_security_provider:$PWD/.java-buildpack/open_jdk_jre/lib/ext -Djava.security.properties=$PWD/.java-buildpack/java_security/java.security $JAVA_OPTS" &&
                 CALCULATED_MEMORY=$($PWD/.java-buildpack/open_jdk_jre/bin/java-buildpack-memory-calculator-3.13.0_RELEASE -totMemory=$MEMORY_LIMIT -loadedClasses=12863 -poolType=metaspace -stackThreads=250 -vmOptions="$JAVA_OPTS") &&
                 echo JVM Memory Configuration: $CALCULATED_MEMORY && JAVA_OPTS="$JAVA_OPTS $CALCULATED_MEMORY" && MALLOC_ARENA_MAX=2 SERVER_PORT=$PORT eval exec $PWD/.java-buildpack/open_jdk_jre/bin/java $JAVA_OPTS -cp $PWD/.
                 org.springframework.boot.loader.JarLauncher
     state      since                  cpu    memory         disk           details
#0   running    2020-08-12T21:34:48Z   0.0%   170.9M of 1G   127.4M of 1G
#1   starting   2020-08-12T21:34:33Z   0.0%   34.9K of 1G    127.4M of 1G
```

## Test newly created metric endpoint, to be used by Prometheus
```
» curl https://prometheus-proxy.myapps.com/metrics
...
```


