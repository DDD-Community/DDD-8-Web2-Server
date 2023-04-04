package ddd.caffeine.ratrip.common.util;

import java.util.UUID;

import com.fasterxml.uuid.Generators;

public class SequentialUUIDGenerator {
	public static UUID generate() {
		//sequential uuid 생성
		UUID uuid = Generators.timeBasedGenerator().generate();
		String[] uuidArr = uuid.toString().split("-");
		String uuidStr = uuidArr[2] + uuidArr[1] + uuidArr[0] + uuidArr[3] + uuidArr[4];
		StringBuilder sb = new StringBuilder(uuidStr);
		sb.insert(8, "-");
		sb.insert(13, "-");
		sb.insert(18, "-");
		sb.insert(23, "-");
		return UUID.fromString(sb.toString());
	}

}
