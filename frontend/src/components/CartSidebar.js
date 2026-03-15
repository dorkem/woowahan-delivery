import { createButton } from './Button.js';
import { api } from '../services/api.js';

export function createCartSidebar() {
  const sidebar = document.createElement('aside');
  sidebar.className = 'cart-sidebar';
  
  // Header
  const header = document.createElement('div');
  header.className = 'cart-header';
  const title = document.createElement('h2');
  title.className = 'cart-title';
  title.textContent = 'Your Order';
  const closeBtn = document.createElement('button');
  closeBtn.className = 'cart-close';
  closeBtn.innerHTML = `<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12"></path></svg>`;
  
  header.appendChild(title);
  header.appendChild(closeBtn);

  // Body / Item List
  const body = document.createElement('div');
  body.className = 'cart-body';

  // Footer / Total
  const footer = document.createElement('div');
  footer.className = 'cart-footer';
  
  const totalRow = document.createElement('div');
  totalRow.className = 'cart-total-row';
  
  const checkoutBtn = createButton({ text: 'Checkout', variant: 'primary', size: 'lg', className: 'btn-full w-full' });
  checkoutBtn.classList.add('w-full');
  checkoutBtn.onclick = () => {
    sidebar.classList.remove('open');
    document.dispatchEvent(new CustomEvent('navigate', { detail: { page: 'checkout' }}));
  };
  
  footer.appendChild(totalRow);
  footer.appendChild(checkoutBtn);

  sidebar.appendChild(header);
  sidebar.appendChild(body);
  sidebar.appendChild(footer);

  async function renderCartData() {
    if (!api.isAuthenticated()) {
      body.innerHTML = `
        <div class="cart-empty">
          <div class="empty-icon">🛒</div>
          <p>Please log in to use cart</p>
        </div>
      `;
      footer.style.display = 'none';
      return;
    }

    try {
      body.innerHTML = '<div class="loader-container"><div class="loader"></div></div>';
      const res = await api.getCart();
      const items = res.data?.items || [];
      const totalPrice = res.data?.totalPrice || 0;

      if (items.length === 0) {
        body.innerHTML = `
          <div class="cart-empty">
            <div class="empty-icon">🛒</div>
            <p>Your cart is empty</p>
            <span class="text-sm text-muted">Add items from a restaurant</span>
          </div>
        `;
        footer.style.display = 'none';
      } else {
        footer.style.display = 'block';
        
        let storeBadge = '';
        if (res.data.storeName) {
          storeBadge = `<div style="padding: 12px; background: var(--surface-hover); font-weight: 600; font-size: 14px; border-bottom: 1px solid var(--border);">${res.data.storeName}</div>`;
        }

        const itemList = document.createElement('ul');
        itemList.className = 'cart-items';
        
        items.forEach(item => {
          const li = document.createElement('li');
          li.className = 'cart-item';
          li.innerHTML = `
            <div class="cart-item-header">
              <span class="cart-item-name">${item.menuName}</span>
              <button class="cart-item-remove" data-id="${item.cartItemId}">
                <svg viewBox="0 0 24 24" width="16" height="16" stroke="currentColor" stroke-width="2" fill="none"><path d="M18 6L6 18M6 6l12 12"></path></svg>
              </button>
            </div>
            <div class="cart-item-footer">
              <span class="cart-item-price">${item.subtotal.toLocaleString()}원</span>
              <div class="cart-qty-controls">
                <button class="qty-btn minus" data-id="${item.cartItemId}">-</button>
                <span class="qty-value">${item.quantity}</span>
                <button class="qty-btn plus" data-id="${item.cartItemId}">+</button>
              </div>
            </div>
          `;
          itemList.appendChild(li);
        });

        body.innerHTML = storeBadge;
        body.appendChild(itemList);
        
        totalRow.innerHTML = `
          <span>Subtotal</span>
          <span class="font-bold">${totalPrice.toLocaleString()}원</span>
        `;

        // Attach listeners
        body.querySelectorAll('.cart-item-remove').forEach(btn => {
          btn.onclick = async () => {
            btn.innerHTML = '...';
            await api.removeCartItem(btn.dataset.id);
            document.dispatchEvent(new CustomEvent('cartUpdated'));
          };
        });

        body.querySelectorAll('.qty-btn.minus').forEach(btn => {
          btn.onclick = async () => {
            const item = items.find(i => i.cartItemId.toString() === btn.dataset.id);
            if (item.quantity > 1) {
              await api.updateCartItem(item.cartItemId, -1);
              document.dispatchEvent(new CustomEvent('cartUpdated'));
            } else {
              await api.removeCartItem(item.cartItemId);
              document.dispatchEvent(new CustomEvent('cartUpdated'));
            }
          };
        });

        body.querySelectorAll('.qty-btn.plus').forEach(btn => {
          btn.onclick = async () => {
             const item = items.find(i => i.cartItemId.toString() === btn.dataset.id);
             await api.updateCartItem(item.cartItemId, 1);
             document.dispatchEvent(new CustomEvent('cartUpdated'));
          };
        });

      }
    } catch (e) {
      console.error(e);
      body.innerHTML = '<div style="padding: 20px; color: red;">Failed to load cart.</div>';
    }
  }

  document.addEventListener('cartUpdated', renderCartData);
  renderCartData();

  return sidebar;
}
