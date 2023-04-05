package ddd.caffeine.ratrip.module.memo.presentation.dto.request;

import javax.validation.constraints.NotEmpty;

import ddd.caffeine.ratrip.module.memo.application.dto.CreateMemoDto;
import ddd.caffeine.ratrip.module.place.domain.Address;
import ddd.caffeine.ratrip.module.place.domain.Category;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateMemoRequestDto {

	@NotEmpty(message = "DayPlanId must not be empty")
	private Long dayPlanId;

	@NotEmpty(message = "Name must not be empty")
	private String name;

	@NotEmpty(message = "Address must not be empty")
	private Address address;

	@NotEmpty(message = "Category must not be empty")
	private Category category;

	@NotEmpty(message = "sequence must not be empty")
	private int sequence;

	private String content;

	public CreateMemoDto toServiceDto() {
		return CreateMemoDto.of(dayPlanId, name, address, category, sequence, content);
	}
}
