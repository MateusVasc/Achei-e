package com.upe.br.acheie.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

	ELECTRONIC("Electronic"),
	CLOTHING("Clothing"),
	KEYS("Keys"),
	DOCUMENTS("Documents"),
	WALLET("Wallet"),
	GLASSES("Glasses"),
	BAG_BACKPACK("Bag/Backpack"),
	ACCESSORIES("Accessories"),
	BOOKS("Books"),
	TOYS("Toys"),
	MUSICAL_INSTRUMENTS("Musical Instruments"),
	WRITING_INSTRUMENTS("Writing Instruments"),
	SPORTS_ITEMS("Sports Items"),
	TOOLS("Tools"),
	PHOTOGRAPHIC_EQUIPMENT("Photographic Equipment"),
	KITCHEN_APPLIANCES("Kitchen Appliances"),
	STATIONERY_ITEMS("Stationery Items"),
	BEAUTY_PRODUCTS("Beauty Products"),
	OTHERS("Others");

  private final String value;
  
  public static boolean eCategory(String word) {
	  Category[] categories = Category.values();
	  for (Category category : categories) {
		  if (category.value.equals(word)) {
			  return true;
		  }
	  }
	  return false;
  }
  
}
