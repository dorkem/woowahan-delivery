import { createChip } from '../components/Chip.js';
import { createCard } from '../components/Card.js';
import { api } from '../services/api.js';

export function renderHome(container) {
  container.innerHTML = '';
  
  // Hero Section
  const hero = document.createElement('section');
  hero.className = 'home-hero';
  hero.innerHTML = `
    <div class="hero-content">
      <h1 class="hero-title">Delicious food, <br/>delivered fast.</h1>
      <p class="hero-subtitle">Satisfy your cravings with our wide selection of local favorites.</p>
    </div>
    <div class="hero-image-wrapper">
      <div class="hero-image-placeholder">🍔🍣🍕</div>
    </div>
  `;
  container.appendChild(hero);

  // Category Filter
  const categories = [
    { id: 1, name: 'All' },
    { id: 2, name: 'Korean' },
    { id: 3, name: 'Chinese' },
    { id: 4, name: 'Pizza' },
    { id: 5, name: 'Chicken' },
    { id: 6, name: 'Fast Food' },
    { id: 7, name: 'Dessert' }
  ];

  const categorySection = document.createElement('section');
  categorySection.className = 'home-categories';
  const chipContainer = document.createElement('div');
  chipContainer.className = 'chip-container';
  
  let activeCategoryId = 1;

  categories.forEach(cat => {
    const chip = createChip({
      text: cat.name,
      active: cat.id === activeCategoryId,
      onClick: (e, isActive) => {
        if(isActive) return; // Ignore if clicking already active
        
        // Reset all chips visual state
        const allChips = chipContainer.querySelectorAll('.chip');
        allChips.forEach(c => c.classList.remove('chip-active'));
        e.target.classList.add('chip-active');
        
        activeCategoryId = cat.id;
        renderStoreFeed(activeCategoryId);
      }
    });
    chipContainer.appendChild(chip);
  });
  
  categorySection.appendChild(chipContainer);
  container.appendChild(categorySection);

  // Store Feed Section
  const feedSection = document.createElement('section');
  feedSection.className = 'home-feed';
  
  const feedHeader = document.createElement('div');
  feedHeader.className = 'feed-header flex-row justify-between';
  feedHeader.innerHTML = `
    <h2 class="section-title">Featured Restaurants</h2>
    <div class="feed-filters">
      <span class="text-sm text-muted">Sort by: Recommended</span>
    </div>
  `;
  
  const gridContainer = document.createElement('div');
  gridContainer.className = 'grid grid-cols-3 store-grid';
  gridContainer.id = 'store-grid';

  feedSection.appendChild(feedHeader);
  feedSection.appendChild(gridContainer);
  container.appendChild(feedSection);

  // Initial Render
  renderStoreFeed(activeCategoryId);
}

async function renderStoreFeed(categoryId) {
  const grid = document.getElementById('store-grid');
  if(!grid) return;
  
  grid.innerHTML = '<div class="loader-container"><div class="loader"></div></div>'; // Loader
  
  try {
    const response = await api.getStores(categoryId);
    const storePage = response.data; // ResponseDtoStorePageResponse -> StorePageResponse
    const stores = storePage.stores;
    
    grid.innerHTML = ''; // Clear loader
    
    if (!stores || stores.length === 0) {
      grid.innerHTML = '<p class="text-muted text-center w-full" style="grid-column: 1 / -1; padding: 40px 0;">No stores found in this category.</p>';
      return;
    }

    stores.forEach(store => {
      const card = createCard({ hoverable: true, className: 'store-card' });
      card.onclick = () => {
        // Navigate to store detail event
        const event = new CustomEvent('navigate', { detail: { page: 'store', id: store.storeId }});
        document.dispatchEvent(event);
      };

      // Card Thumbnail
      const thumbnail = document.createElement('div');
      thumbnail.className = 'store-thumbnail';
      // 백엔드에서 제공하는 기본 이미지를 바로 사용합니다.
      const bgUrl = store.thumbnail;
      if (bgUrl) {
        thumbnail.style.backgroundImage = `url(${bgUrl})`;
      } else {
        thumbnail.style.backgroundColor = '#EEEEEE';
      }
      
      if (store.averageRating > 4.7) {
        const bestBadge = document.createElement('div');
        bestBadge.className = 'store-badge badge badge-solid-primary';
        bestBadge.textContent = 'Top Rated';
        thumbnail.appendChild(bestBadge);
      }
      
      // Card Body
      const body = document.createElement('div');
      body.className = 'card-body store-info';
      body.innerHTML = `
        <div class="flex-row justify-between align-start">
          <h3 class="store-name">${store.storeName}</h3>
          <div class="store-rating">
            <svg viewBox="0 0 24 24" fill="currentColor"><path d="M12 2l3.09 6.26L22 9.27l-5 4.87 1.18 6.88L12 17.77l-6.18 3.25L7 14.14 2 9.27l6.91-1.01L12 2z"/></svg>
            <span>${store.averageRating ? store.averageRating.toFixed(1) : 'New'}</span>
            <span class="text-muted">(${store.reviewCount || 0})</span>
          </div>
        </div>
        <div class="store-meta text-sm text-muted mt-2">
          <span>Min Order: ${store.minOrderAmount ? store.minOrderAmount.toLocaleString() : 0}원</span>
        </div>
      `;

      card.appendChild(thumbnail);
      card.appendChild(body);
      grid.appendChild(card);
    });
  } catch (err) {
    grid.innerHTML = '<p class="text-danger">Failed to load stores. Make sure the backend API is running.</p>';
  }
}
