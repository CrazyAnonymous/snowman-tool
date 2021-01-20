package io.github.snowythinker.test;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {

	private Long id;
	
	private String name;
	
	private String sku;
	
	private Date createTime;
}
