#!/bin/sh

version="$1"
base="core-js-"
type=".jar"
echo ${base}${version}${type}
cd ./core/build/libs/
# Unzip Jar File
jar xf ${base}${version}${type}
# Find and replace
sed -i -e 's/kotlinx_serialization_kotlinx_serialization_runtime_jsLegacy/kotlinx_serialization_kotlinx_serialization_runtime_jslegacy/g' SevenFacette-core.js
sed -i -e 's/kotlinx_serialization_kotlinx_serialization_runtime_jsLegacy/kotlinx_serialization_kotlinx_serialization_runtime_jslegacy/g' SevenFacette-core.meta.js
# Clean up
rm SevenFacette-core.js-e
rm SevenFacette-core.meta.js-e
