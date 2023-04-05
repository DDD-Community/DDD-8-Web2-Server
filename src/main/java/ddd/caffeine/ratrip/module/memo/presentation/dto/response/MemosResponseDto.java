package ddd.caffeine.ratrip.module.memo.presentation.dto.response;

import java.util.List;

import ddd.caffeine.ratrip.module.memo.domain.Memo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemosResponseDto {
	private List<Memo> memos;

	public static MemosResponseDto of(List<Memo> memos) {
		return new MemosResponseDto(memos);
	}
}
