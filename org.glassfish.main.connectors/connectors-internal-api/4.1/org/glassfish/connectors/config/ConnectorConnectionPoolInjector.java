
package org.glassfish.connectors.config;

import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.config.InjectionTarget;
import org.jvnet.hk2.config.NoopConfigInjector;

@Service(name = "connector-connection-pool", metadata = "target=org.glassfish.connectors.config.ConnectorConnectionPool,@resource-adapter-name=optional,@resource-adapter-name=datatype:java.lang.String,@resource-adapter-name=leaf,@connection-definition-name=optional,@connection-definition-name=datatype:java.lang.String,@connection-definition-name=leaf,@steady-pool-size=optional,@steady-pool-size=default:8,@steady-pool-size=datatype:java.lang.String,@steady-pool-size=leaf,@max-pool-size=optional,@max-pool-size=default:32,@max-pool-size=datatype:java.lang.String,@max-pool-size=leaf,@max-wait-time-in-millis=optional,@max-wait-time-in-millis=default:60000,@max-wait-time-in-millis=datatype:java.lang.String,@max-wait-time-in-millis=leaf,@pool-resize-quantity=optional,@pool-resize-quantity=default:2,@pool-resize-quantity=datatype:java.lang.String,@pool-resize-quantity=leaf,@idle-timeout-in-seconds=optional,@idle-timeout-in-seconds=default:300,@idle-timeout-in-seconds=datatype:java.lang.String,@idle-timeout-in-seconds=leaf,@fail-all-connections=optional,@fail-all-connections=default:false,@fail-all-connections=datatype:java.lang.Boolean,@fail-all-connections=leaf,@transaction-support=optional,@transaction-support=datatype:java.lang.String,@transaction-support=leaf,@is-connection-validation-required=optional,@is-connection-validation-required=default:false,@is-connection-validation-required=datatype:java.lang.Boolean,@is-connection-validation-required=leaf,@validate-atmost-once-period-in-seconds=optional,@validate-atmost-once-period-in-seconds=default:0,@validate-atmost-once-period-in-seconds=datatype:java.lang.String,@validate-atmost-once-period-in-seconds=leaf,@connection-leak-timeout-in-seconds=optional,@connection-leak-timeout-in-seconds=default:0,@connection-leak-timeout-in-seconds=datatype:java.lang.String,@connection-leak-timeout-in-seconds=leaf,@connection-leak-reclaim=optional,@connection-leak-reclaim=default:false,@connection-leak-reclaim=datatype:java.lang.Boolean,@connection-leak-reclaim=leaf,@connection-creation-retry-attempts=optional,@connection-creation-retry-attempts=default:0,@connection-creation-retry-attempts=datatype:java.lang.String,@connection-creation-retry-attempts=leaf,@connection-creation-retry-interval-in-seconds=optional,@connection-creation-retry-interval-in-seconds=default:10,@connection-creation-retry-interval-in-seconds=datatype:java.lang.String,@connection-creation-retry-interval-in-seconds=leaf,@lazy-connection-enlistment=optional,@lazy-connection-enlistment=default:false,@lazy-connection-enlistment=datatype:java.lang.Boolean,@lazy-connection-enlistment=leaf,@lazy-connection-association=optional,@lazy-connection-association=default:false,@lazy-connection-association=datatype:java.lang.Boolean,@lazy-connection-association=leaf,@associate-with-thread=optional,@associate-with-thread=default:false,@associate-with-thread=datatype:java.lang.Boolean,@associate-with-thread=leaf,@pooling=optional,@pooling=default:true,@pooling=datatype:java.lang.Boolean,@pooling=leaf,@match-connections=optional,@match-connections=default:true,@match-connections=datatype:java.lang.Boolean,@match-connections=leaf,@max-connection-usage-count=optional,@max-connection-usage-count=default:0,@max-connection-usage-count=datatype:java.lang.String,@max-connection-usage-count=leaf,@description=optional,@description=datatype:java.lang.String,@description=leaf,<security-map>=collection:org.glassfish.connectors.config.SecurityMap,<property>=collection:org.jvnet.hk2.config.types.Property,<property>=collection:org.jvnet.hk2.config.types.Property,@name=optional,@name=datatype:java.lang.String,@name=leaf,key=@name,keyed-as=com.sun.enterprise.config.serverbeans.ResourcePool,@ping=optional,@ping=default:false,@ping=datatype:java.lang.Boolean,@ping=leaf,@object-type=optional,@object-type=default:user,@object-type=datatype:java.lang.String,@object-type=leaf,@deployment-order=optional,@deployment-order=default:100,@deployment-order=datatype:java.lang.Integer,@deployment-order=leaf")
@InjectionTarget(ConnectorConnectionPool.class)
public class ConnectorConnectionPoolInjector
    extends NoopConfigInjector
{


}
