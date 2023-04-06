package ddd.caffeine.ratrip.module.memo.presentation.dto.response;

import java.util.List;

import ddd.caffeine.ratrip.module.memo.domain.repository.dao.MemoDao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemosResponseDto {
	private List<MemoDao> memos;

	public static MemosResponseDto of(List<MemoDao> memos) {
		return new MemosResponseDto(memos);
	}
}
