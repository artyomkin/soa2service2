mvn install:install-file \
   -Dfile=/home/welllet/payara-embedded-all-6.2023.10.jar  \
   -DgroupId=fish.payara.extras \
   -DartifactId=payara-embedded-all \
   -Dversion=6.2023.10 \
   -Dpackaging=jar \
   -DgeneratePom=true
