// Mock Data Generator
// Derived from definitions in swagger.json

export function getStores(categoryId = 1, cursor = 0, size = 15) {
  const categories = ['Korean', 'Chinese', 'Pizza', 'Chicken', 'Fast Food', 'Dessert'];
  const data = Array.from({ length: size }).map((_, i) => {
    const id = cursor + i + 1;
    const rating = (Math.random() * (5 - 3.5) + 3.5).toFixed(1);
    const revCount = Math.floor(Math.random() * 500) + 10;
    
    // Distribute categories
    const catName = categoryId === 1 ? categories[id % categories.length] : categories[categoryId - 2] || 'Korean';
    
    // Different visual styling placeholders
    const placeholderColors = ['FF5A00', '1CC88A', '228BE6', 'FAB005', '7950F2', 'FA5252'];
    const color = placeholderColors[id % placeholderColors.length];

    return {
      storeId: id,
      storeName: `Delicious ${catName} ${id}`,
      thumbnail: `https://via.placeholder.com/400x200/${color}/FFFFFF?text=${catName}`,
      averageRating: parseFloat(rating),
      reviewCount: revCount,
      minOrderAmount: (Math.floor(Math.random() * 2) + 1) * 10000 // 10000 or 20000
    };
  });

  return data;
}

export function getMenus(storeId) {
  return [
    {
      id: 101,
      menuName: "Signature Dish",
      price: 15000,
      description: "Our famous signature dish with fresh ingredients.",
      image: "https://via.placeholder.com/100x100/FF5A00/FFF?text=Menu1"
    },
    {
      id: 102,
      menuName: "Spicy Combo",
      price: 18000,
      description: "A spicy and flavorful combination.",
      image: "https://via.placeholder.com/100x100/FA5252/FFF?text=Menu2"
    },
    {
      id: 103,
      menuName: "Family Pack",
      price: 35000,
      description: "Great for 3-4 people.",
      image: "https://via.placeholder.com/100x100/FAB005/FFF?text=Menu3"
    }
  ];
}
