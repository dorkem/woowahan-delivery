import { api } from '../services/api.js';
import { createButton } from '../components/Button.js';
import { createCard } from '../components/Card.js';

export async function renderStoreDetail(container, storeId) {
  
  // Show Loading State initially
  container.innerHTML = '<div class="loader-container"><div class="loader"></div></div>';
  
  // Mock store data since we just clicked it and the schema doesn't have a GET /stores/{id} root endpoint listed directly
  const storeData = {
    id: storeId,
    name: 'Selected Store',
    thumbnail: 'https://via.placeholder.com/1200x400/FF5A00/FFFFFF?text=Premium+Cover',
    rating: 4.8,
    reviews: 245,
    minOrder: 15000,
    deliveryFee: 3000,
    time: '25-35 min'
  };

  let menuData = [];
  try {
    const response = await api.getMenus(storeId);
    // MenuListResponse => .menus
    menuData = response.data?.menus || [];
  } catch(e) {
    console.error("Failed to load menus", e);
  }

  container.innerHTML = '';

  // Store Header (Cover)
  const headerWrapper = document.createElement('div');
  headerWrapper.className = 'store-detail-header';
  
  const cover = document.createElement('div');
  cover.className = 'store-cover';
  cover.style.backgroundImage = `url(${storeData.thumbnail})`;

  const infoCard = document.createElement('div');
  infoCard.className = 'store-info-card';
  infoCard.innerHTML = `
    <h1 class="store-title">${storeData.name}</h1>
    <div class="store-meta-flex mb-4">
      <span class="store-rating-large">
        <svg viewBox="0 0 24 24" fill="currentColor" class="star"><path d="M12 2l3.09 6.26L22 9.27l-5 4.87 1.18 6.88L12 17.77l-6.18 3.25L7 14.14 2 9.27l6.91-1.01L12 2z"/></svg>
        ${storeData.rating}
      </span>
      <span class="text-muted">(${storeData.reviews} reviews)</span>
    </div>
    <div class="store-stats">
      <div class="stat-item">
        <span class="stat-label">Min. Order</span>
        <span class="stat-value">${storeData.minOrder.toLocaleString()}원</span>
      </div>
      <div class="stat-item">
        <span class="stat-label">Delivery Fee</span>
        <span class="stat-value">${storeData.deliveryFee.toLocaleString()}원</span>
      </div>
      <div class="stat-item">
        <span class="stat-label">Est. Time</span>
        <span class="stat-value">${storeData.time}</span>
      </div>
    </div>
  `;

  headerWrapper.appendChild(cover);
  headerWrapper.appendChild(infoCard);
  
  // Menu Section
  const menuSection = document.createElement('div');
  menuSection.className = 'menu-section';
  menuSection.innerHTML = `<h2 class="section-title">Popular Items</h2>`;
  
  const menuGrid = document.createElement('div');
  menuGrid.className = 'grid grid-cols-2';

  menuData.forEach(menu => {
    const card = createCard({ hoverable: true, className: 'menu-card' });
    card.onclick = async () => {
      try {
        const btn = cardBody.querySelector('.menu-add-btn');
        if (btn) {
          const originalContent = btn.innerHTML;
          btn.innerHTML = '<div class="loader" style="width:16px; height:16px; border-width:2px; display:inline-block;"></div>';
          
          await api.addCartItem(menu.id, 1);
          
          btn.innerHTML = '<svg viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" fill="none"><path stroke-linecap="round" stroke-linejoin="round" d="M5 13l4 4L19 7"/></svg>';
          setTimeout(() => { btn.innerHTML = originalContent; }, 1000);
          
          document.dispatchEvent(new CustomEvent('cartUpdated'));
        }
      } catch (err) {
        console.error(err);
        alert('Failed to add item. Ensure you are logged in.');
      }
    };

    const cardBody = document.createElement('div');
    cardBody.className = 'menu-card-body flex-row justify-between';
    
    cardBody.innerHTML = `
      <div class="menu-info flex-col justify-center">
        <h3 class="menu-name">${menu.menuName}</h3>
        <p class="menu-desc text-sm text-muted">${menu.description}</p>
        <span class="menu-price">${menu.price.toLocaleString()}원</span>
      </div>
      <div class="menu-image">
        <img src="${menu.image}" alt="${menu.menuName}" />
        <button class="menu-add-btn">
          <svg viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" fill="none"><path stroke-linecap="round" stroke-linejoin="round" d="M12 6v12m-6-6h12"/></svg>
        </button>
      </div>
    `;

    card.appendChild(cardBody);
    menuGrid.appendChild(card);
  });

  menuSection.appendChild(menuGrid);

  container.appendChild(headerWrapper);
  container.appendChild(menuSection);
}
