import './styles/design-system.css'
import './styles/layout.css'
import './styles/components.css'
import './styles/header.css'
import './styles/cart.css'
import './styles/footer.css'

import { createHeader } from './components/Header.js'
import { createFooter } from './components/Footer.js'
import { createCartSidebar } from './components/CartSidebar.js'

import { renderHome } from './pages/Home.js';
import { renderStoreDetail } from './pages/StoreDetail.js';
import { renderCheckout } from './pages/Checkout.js';
import { renderOrderTracker } from './pages/OrderTracker.js';
import { api } from './services/api.js';
import './styles/home.css';
import './styles/store.css';
import './styles/checkout.css';
import './styles/tracker.css';
import './styles/login.css';

function renderApp() {
  const path = window.location.pathname;
  if (path === '/oauth/kakao/callback') {
    const params = new URLSearchParams(window.location.search);
    const code = params.get('code');
    if (code) {
      const app = document.querySelector('#app');
      app.innerHTML = `
        <div style="display:flex; flex-direction:column; align-items:center; justify-content:center; height:100vh;">
          <h2 style="margin-bottom:20px;">Kakao Login...</h2>
          <div class="loader"></div>
        </div>
      `;
      api.kakaoCallback(code).then(() => {
        window.location.replace('/');
      }).catch(err => {
        alert('Kakao Login Failed: ' + err.message);
        window.location.replace('/');
      });
      return;
    }
  }

  const app = document.querySelector('#app')
  app.className = 'app-container'

  // Create Main Content Area
  const main = document.createElement('main')
  main.className = 'main-content'
  main.id = 'main-content'

  // Build Shell
  app.appendChild(createHeader())

  if (api.isAuthenticated()) {
    app.appendChild(createCartSidebar([]))
  }

  app.appendChild(main)
  app.appendChild(createFooter())

  // Attach Router Listener
  document.addEventListener('navigate', (e) => {
    const { page, id, categoryId, isPopState, replace } = e.detail;
    main.innerHTML = ''; // clear DOM
    window.scrollTo(0, 0); // scroll to top

    if (!isPopState) {
      let url = '/';
      if (page === 'home' && categoryId && categoryId !== 1 && categoryId !== '1') {
        url = `/?page=home&categoryId=${categoryId}`;
      }
      if (page !== 'home' && page !== 'landing') {
        url = `/?page=${page}${id ? `&id=${id}` : ''}`;
      }
      if (replace) {
        history.replaceState({ page, id, categoryId }, '', url);
      } else {
        history.pushState({ page, id, categoryId }, '', url);
      }
    }

    if (page === 'home') {
      renderHome(main, categoryId);
    } else if (page === 'store') {
      renderStoreDetail(main, id);
    } else if (page === 'checkout') {
      renderCheckout(main);
    } else if (page === 'tracker') {
      renderOrderTracker(main, id);
    } else if (page === 'landing') {
      main.innerHTML = `
        <div style="padding: 100px 20px; text-align: center; height: 60vh; display: flex; flex-direction: column; justify-content: center; align-items: center;">
          <h2 style="font-size: 2rem; margin-bottom: 20px; color: var(--text-main);">Welcome to Woowahan Delivery</h2>
          <p style="color: var(--text-muted); margin-bottom: 30px;">Please log in to browse delicious stores and order food.</p>
        </div>
      `;
    }
  });

  window.addEventListener('popstate', (e) => {
    if (e.state) {
      document.dispatchEvent(new CustomEvent('navigate', {
        detail: { page: e.state.page, id: e.state.id, categoryId: e.state.categoryId, isPopState: true }
      }));
    } else {
      document.dispatchEvent(new CustomEvent('navigate', {
        detail: { page: 'landing', isPopState: true }
      }));
    }
  });

  // Initial Route
  if (!api.isAuthenticated()) {
    document.dispatchEvent(new CustomEvent('navigate', {
      detail: { page: 'landing', isPopState: false, replace: true }
    }));
  } else {
    const params = new URLSearchParams(window.location.search);
    const initialPage = params.get('page') || 'home';
    const initialId = params.get('id');
    const initialCategoryId = params.get('categoryId');
    document.dispatchEvent(new CustomEvent('navigate', {
      detail: { page: initialPage, id: initialId, categoryId: initialCategoryId, isPopState: false, replace: true }
    }));
  }

  // Logo navigation
  const logo = app.querySelector('.header-logo');
  if (logo) {
    logo.addEventListener('click', (e) => {
      e.preventDefault();
      document.dispatchEvent(new CustomEvent('navigate', { detail: { page: 'home' } }));
    });
  }

  // Attach Cart Toggle Logic
  setupCartToggle();
}

function setupCartToggle() {
  const cartBtn = document.querySelector('.cart-toggle');
  const cartSidebar = document.querySelector('.cart-sidebar');
  const closeBtn = document.querySelector('.cart-close');

  if (cartBtn && cartSidebar && closeBtn) {
    cartBtn.addEventListener('click', () => {
      cartSidebar.classList.add('open');
    });
    closeBtn.addEventListener('click', () => {
      cartSidebar.classList.remove('open');
    });
  }
}

// Render app when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
  renderApp(); // 그냥 바로 실행
});