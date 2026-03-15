import { createButton } from './Button.js';
import { api } from '../services/api.js';
import { createLoginModal } from './LoginModal.js';
export function createHeader() {
  const header = document.createElement('header');
  header.className = 'app-header';
  
  const container = document.createElement('div');
  container.className = 'header-container';

  // Logo Area
  const logo = document.createElement('a');
  logo.href = '/';
  logo.className = 'header-logo';
  logo.innerHTML = `<span class="logo-text">Woowahan<span class="logo-accent">Delivery</span></span>`;

  // Search / Location Mock (Centered)
  const searchArea = document.createElement('div');
  searchArea.className = 'header-search';
  const addressBtn = document.createElement('button');
  addressBtn.className = 'address-selector';
  addressBtn.innerHTML = `
    <svg viewBox="0 0 24 24" fill="none" class="icon" stroke="currentColor" stroke-width="2"><path stroke-linecap="round" stroke-linejoin="round" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"></path><path stroke-linecap="round" stroke-linejoin="round" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"></path></svg>
    <span class="address-text">역삼동 123-45</span>
    <svg viewBox="0 0 24 24" fill="none" class="icon icon-sm" stroke="currentColor" stroke-width="2"><path stroke-linecap="round" stroke-linejoin="round" d="M19 9l-7 7-7-7"></path></svg>
  `;
  searchArea.appendChild(addressBtn);

  // Actions Area (Right)
  const actions = document.createElement('div');
  actions.className = 'header-actions';
  
  const loginBtn = createButton({ 
    text: api.isAuthenticated() ? 'Log Out' : 'Log In', 
    variant: 'ghost', 
    size: 'sm' 
  });

  loginBtn.onclick = () => {
    if (api.isAuthenticated()) {
      api.logout();
      window.location.reload();
    } else {
      const modal = createLoginModal({ 
        onSuccess: () => window.location.reload() 
      });
      document.body.appendChild(modal);
    }
  };
  
  // Cart Button
  const cartBtn = document.createElement('button');
  cartBtn.className = 'cart-toggle';
  cartBtn.innerHTML = `
    <svg viewBox="0 0 24 24" fill="none" class="icon" stroke="currentColor" stroke-width="2">
      <path stroke-linecap="round" stroke-linejoin="round" d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z"></path>
    </svg>
    </svg>
    <span class="cart-badge">${api.isAuthenticated() ? '0' : '!'}</span>
  `;

  async function updateCartBadge() {
    if (!api.isAuthenticated()) return;
    try {
      const res = await api.getCart();
      const count = res.data?.items?.reduce((sum, item) => sum + item.quantity, 0) || 0;
      const badge = cartBtn.querySelector('.cart-badge');
      if (badge) badge.textContent = count;
    } catch (e) {
      console.error('Failed to fetch cart for badge', e);
    }
  }

  document.addEventListener('cartUpdated', updateCartBadge);
  updateCartBadge();

  if (!api.isAuthenticated()) {
    cartBtn.style.opacity = '0.5';
    cartBtn.onclick = (e) => {
      e.stopPropagation();
      e.preventDefault();
      const modal = createLoginModal({ 
        onSuccess: () => window.location.reload() 
      });
      document.body.appendChild(modal);
    };
  }

  actions.appendChild(loginBtn);
  actions.appendChild(cartBtn);

  container.appendChild(logo);
  container.appendChild(searchArea);
  container.appendChild(actions);
  
  header.appendChild(container);
  return header;
}
