#!/bin/sh

version="$VERSION"
base="core-js-"
type=".jar"
echo ${base}${version}${type}
cd ./core/build/libs/
# Unzip Jar File
jar xf ${base}${version}${type}
# Find and replace
sed -i -e 's/kotlinx_serialization_kotlinx_serialization_runtime_jsLegacy/kotlinx_serialization_kotlinx_serialization_runtime_jslegacy/g' SevenFacette-core.js
sed -i -e 's/ktor_ktor_http_jsLegacy/ktor_ktor_http_jslegacy/g' SevenFacette-core.js
sed -i -e 's/ktor_ktor_client_core_jsLegacy/ktor_ktor_client_core_jslegacy/g' SevenFacette-core.js
sed -i -e 's/ktor_ktor_client_auth_jsLegacy/ktor_ktor_client_auth_jslegacy/g' SevenFacette-core.js
sed -i -e 's/ktor_ktor_utils_jsLegacy/ktor_ktor_utils_jslegacy/g' SevenFacette-core.js
sed -i -e 's/ktor_ktor_client_json_jsLegacy/ktor_ktor_client_json_jslegacy/g' SevenFacette-core.js
sed -i -e 's/ktor_ktor_client_serialization_jsLegacy/ktor_ktor_client_serialization_jslegacy/g' SevenFacette-core.js
sed -i -e 's/ktor_ktor_io_jsLegacy/ktor_ktor_io_jslegacy/g' SevenFacette-core.js
sed -i -e 's/ktor-ktor-http-jsLegacy/ktor-ktor-http-jslegacy/g' SevenFacette-core.js
sed -i -e 's/ktor-ktor-client-core-jsLegacy/ktor-ktor-client-core-jslegacy/g' SevenFacette-core.js
sed -i -e 's/kotlinx-serialization-kotlinx-serialization-runtime-jsLegacy/kotlinx-serialization-kotlinx-serialization-runtime-jslegacy/g' SevenFacette-core.js
sed -i -e 's/ktor-ktor-client-core-jsLegacy/ktor-ktor-client-core-jslegacy/g' SevenFacette-core.js
sed -i -e 's/ktor-ktor-client-auth-jsLegacy/ktor-ktor-client-auth-jslegacy/g' SevenFacette-core.js
sed -i -e 's/ktor-ktor-utils-jsLegacy/ktor-ktor-utils-jslegacy/g' SevenFacette-core.js
sed -i -e 's/ktor-ktor-client-json-jsLegacy/ktor-ktor-client-json-jslegacy/g' SevenFacette-core.js
sed -i -e 's/ktor-ktor-client-serialization-jsLegacy/ktor-ktor-client-serialization-jslegacy/g' SevenFacette-core.js
sed -i -e 's/ktor-ktor-io-jsLegacy/ktor-ktor-io-jslegacy/g' SevenFacette-core.js
# Clean up
rm SevenFacette-core.js-e
