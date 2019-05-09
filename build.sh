version="1.0.0a"
mcVersion="1.7.10"
lt=("api" "core" "deco" "hologram" "launcher" "light" "logic" "machines" "net" "teleport" "transport")

mkdir pc_Build

sh gradlew build 

for t in ${lt[@]};
do
	mkdir pc_Build/pc_tmp
	if [ "$t" != "launcher" ]; then
		name="$t"_"$mcVersion"_"$version";
	else
		name="$mcVersion"_"$version";
	fi
	cp build/libs/modid-1.0.jar pc_Build/PowerCraft2_$name.jar 
	cd pc_Build/pc_tmp
	jar xf ../PowerCraft2_$name.jar
	for a in ${lt[@]};
	do
		if [ "$t" != "$a" ]; then
			rm -rf "powercraft/$a"
		fi
		if [ "$t" != "launcher" ]; then
			rm -rf "assets"
		fi
	done
	rm -rf PowerCraft2_$name.jar
	jar cf PowerCraft2_$name.jar ./
	cp PowerCraft2_$name.jar ../PowerCraft2_$name.jar
	cd ../../
	rm -rf pc_Build/pc_tmp
done