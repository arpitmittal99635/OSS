
<!--
DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

Copyright 1997-2009 Sun Microsystems, Inc. All rights reserved.

The contents of this file are subject to the terms of either the GNU
General Public License Version 2 only ("GPL") or the Common Development
and Distribution License("CDDL") (collectively, the "License").  You
may not use this file except in compliance with the License. You can obtain
a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
language governing permissions and limitations under the License.

When distributing the software, include this License Header Notice in each
file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
Sun designates this particular file as subject to the "Classpath" exception
as provided by Sun in the GPL Version 2 section of the License file that
accompanied this code.  If applicable, add the following below the License
Header, with the fields enclosed by brackets [] replaced by your own
identifying information: "Portions Copyrighted [year]
[name of copyright owner]"

Contributor(s):

If you wish your version of this file to be governed by only the CDDL or
only the GPL Version 2, indicate your decision by adding "[Contributor]
elects to include this software in this distribution under the [CDDL or GPL
Version 2] license."  If you don't indicate a single choice of license, a
recipient has the option to distribute your version of this file under
either the CDDL, the GPL Version 2 or to extend the choice of license to
its licensees as provided above.  However, if you add GPL Version 2 code
and therefore, elected the GPL Version 2 license, then the option applies
only if the new code is made subject to such option by the copyright
holder.
-->
<!-- jca/connectorConnectionPoolAttr.inc -->
<!-- used by connectorConnectionPoolNew2.jsf -->


<!-- PropertySheet .... -->
<sun:propertySheet id="propertySheet">
    <sun:propertySheetSection id="generalPropertySheet"  label="$resource{i18n.common.GeneralSettings}">
        <sun:property id="jndiProp"  labelAlign="left" noWrap="#{true}" overlapLabel="#{false}" label="$resource{i18n.common.Name}">
            <sun:staticText escape="true" id="Jndi" text="#{wizardPoolExtra.Name}" />
        </sun:property>

        <sun:property id="resAdapterNewProp" rendered="#{!edit}"  labelAlign="left" noWrap="#{false}" overlapLabel="#{false}" label="$resource{i18n.common.resourceAdapter}"  >
            <sun:staticText escape="true" id="resAdapter" text="#{wizardPoolExtra.ResourceAdapterName}"  />
        </sun:property>

        <sun:property id="connectionDefNewProp"  labelAlign="left" noWrap="#{true}" overlapLabel="#{false}" label="$resource{i18njca.connectorConnectionPool.connectionDefinitionName}" >
            <sun:staticText escape="true" id="connectionDef" text="#{wizardPoolExtra.ConnectionDefinitionName}"  />
        </sun:property>

        <sun:property id="pingProp"  labelAlign="left" noWrap="#{true}" overlapLabel="#{false}" label="$resource{i18njca.jcaPool.ping}" helpText="$resource{i18njca.jcaPool.pingHelp}">
            <sun:checkbox label="$resource{i18n.common.Enabled}" selected="#{sessionScope.wizardMap.Ping}" selectedValue="true" />
        </sun:property>

        <sun:property id="descProp"  labelAlign="left" noWrap="#{false}" overlapLabel="#{false}" label="$resource{i18n.common.description}" >
            <sun:textField id="desc" columns="$int{60}" text="#{sessionScope.wizardMap.Description}" />
        </sun:property>

        "<br /><br />
    </sun:propertySheetSection>

    <sun:propertySheetSection id="poolSettingsPropertySheet" label="$resource{i18njca.jcaPool.poolSettings}">
        <sun:property id="initSizeProp"  labelAlign="left" noWrap="#{false}" overlapLabel="#{false}"
            label="$resource{i18njca.jcaPool.initPoolSize}" helpText="$resource{i18njca.jcaPool.initPoolSizeHelp}">
            <sun:textField id="ds" styleClass="integer" columns="$int{10}" text="#{sessionScope.wizardMap.SteadyPoolSize}" />
            <sun:staticText escape="true" id="con1" style="padding: 8pt" text="$resource{i18n.common.Connections}"/>
        </sun:property>

        <sun:property id="maxProp"  labelAlign="left" noWrap="#{false}" overlapLabel="#{false}" label="$resource{i18njca.jcaPool.maxPoolSize}" helpText="$resource{i18njca.jcaPool.maxPoolSizeHelp}">
            <sun:textField id="ds2" styleClass="integer" columns="$int{10}" text="#{sessionScope.wizardMap.MaxPoolSize}" />
            <sun:staticText escape="true" id="con2" style="padding: 8pt" text="$resource{i18n.common.Connections}"/>
        </sun:property>

        <sun:property id="resizeProp"  labelAlign="left" noWrap="#{false}" overlapLabel="#{false}" label="$resource{i18njca.jcaPool.poolResize}" helpText="$resource{i18njca.jcaPool.poolResizeHelp}">
            <sun:textField id="ds3" styleClass="integer" columns="$int{10}" text="#{sessionScope.wizardMap.PoolResizeQuantity}" />
            <sun:staticText escape="true" id="con3" style="padding: 8pt" text="$resource{i18n.common.Connections}"/>
        </sun:property>

        <sun:property id="idleProp"  labelAlign="left" noWrap="#{false}" overlapLabel="#{false}" label="$resource{i18njca.jcaPool.idleTimeout}" helpText="$resource{i18njca.jcaPool.idleTimeoutHelp}">
            <sun:textField id="ds" styleClass="integer" columns="$int{10}" text="#{sessionScope.wizardMap.IdleTimeoutInSeconds}" />
            <sun:staticText escape="true" id="sec" style="padding: 8pt" text="$resource{i18n.common.Seconds}"/>
        </sun:property>

        <sun:property id="maxWaitProp"  labelAlign="left" noWrap="#{false}" overlapLabel="#{false}" label="$resource{i18njca.jcaPool.maxWait}" helpText="$resource{i18njca.jcaPool.maxWaitHelp}">
            <sun:textField id="ds" styleClass="integer" columns="$int{10}" text="#{sessionScope.wizardMap.MaxWaitTimeInMillis}" />
            <sun:staticText escape="true" id="mil" style="padding: 8pt" text="$resource{i18n.common.Milliseconds}"/>
        </sun:property>
    </sun:propertySheetSection>

    <sun:propertySheetSection id="poolPropertySheet" label="$resource{i18njca.jcaPool.Validation}">
        <sun:property id="conValidationProp"  labelAlign="left" noWrap="#{true}" overlapLabel="#{false}" label="$resource{i18njca.jcaPool.conValidation}" helpText="$resource{i18njca.connectorConnectionPool.conValidationHelp}">
            <sun:checkbox label="$resource{i18n.common.Required}" selected="#{sessionScope.wizardMap.IsConnectionValidationRequired}" selectedValue="true" />
        </sun:property>

        <sun:property id="failProp"  labelAlign="left" noWrap="#{true}" overlapLabel="#{false}" label="$resource{i18njca.connectorConnectionPool.onFail}" helpText="$resource{i18njca.connectorConnectionPool.onFailHelp}">
            <sun:checkbox label="$resource{i18njca.connectorConnectionPool.closeAll}" selected="#{sessionScope.wizardMap.FailAllConnections}" selectedValue="true" />
        </sun:property>

        <sun:property>
            "<br /><br />
        </sun:property>

        <sun:property id="transprop"  labelAlign="left" noWrap="#{true}" overlapLabel="#{false}" label="$resource{i18njca.connectorConnectionPool.transSupport}" helpText="$resource{i18njca.connectorConnectionPool.transSupportHelp}" >
            <sun:dropDown id="trans" selected="#{sessionScope.wizardMap.TransactionSupport}" labels={"" "XATransaction"  "LocalTransaction"  "NoTransaction"}/>
        </sun:property>

        <sun:property>
            "<br /><br />
        </sun:property>
    </sun:propertySheetSection>
</sun:propertySheet>  