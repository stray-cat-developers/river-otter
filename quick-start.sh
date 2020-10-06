docker-compose up &

VERSION=$(./gradlew version -q)

./gradlew clean

if [ ! -f build/libs/river-otter-"${VERSION}".jar ]; then
	./gradlew build -x test
fi

java \
	-XX:MaxMetaspaceSize=100m \
	-Xmx512m \
	-jar build/libs/river-otter-"${VERSION}".jar
