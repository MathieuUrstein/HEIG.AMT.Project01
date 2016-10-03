FROM jboss/wildfly:10.1.0.Final

MAINTAINER Mathieu Urstein <mathieu.urstein@heig-vd.ch> Sébastien Boson <sebastien.boson@heig-vd.ch>

RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]

COPY Projet01.war /opt/jboss/wildfly/standalone/deployments/
