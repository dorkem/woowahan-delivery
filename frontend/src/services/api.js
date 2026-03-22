export function createApi() {
  const BASE_URL = '/api/v1';

  async function fetchApi(endpoint, options = {}) {
    const defaultHeaders = {
      'Content-Type': 'application/json',
    };
    
    // Attempt to grab auth token if we implement login later
    const token = localStorage.getItem('accessToken');
    if (token) {
      defaultHeaders['Authorization'] = `Bearer ${token}`;
    }

    const config = {
      ...options,
      headers: {
        ...defaultHeaders,
        ...options.headers,
      },
    };

    try {
      const response = await fetch(`${BASE_URL}${endpoint}`, config);
      if (!response.ok) {
        let errMsg = `API Error: ${response.status}`;
        try {
          const errBody = await response.json();
          if (errBody && errBody.message) errMsg = errBody.message;
        } catch (e) {}
        throw new Error(errMsg);
      }
      return await response.json();
    } catch (error) {
      console.error('API Fetch failed:', error);
      throw error;
    }
  }

  return {
    getStores: async (categoryId, cursor = null, size = 15) => {
      let url = `/stores?size=${size}`;
      if (categoryId !== 1) url += `&categoryId=${categoryId}`;
      if (cursor) url += `&cursor=${cursor}`;
      return fetchApi(url);
    },
    getMenus: async (storeId) => {
      return fetchApi(`/stores/${storeId}/menus`);
    },
    createOrder: async (orderData) => {
      return fetchApi(`/orders/order-create`, {
        method: 'POST',
        body: JSON.stringify(orderData)
      });
    },
    getCart: async () => {
      return fetchApi(`/cart`);
    },
    addCartItem: async (menuId, quantity) => {
      return fetchApi(`/cart/items`, {
        method: 'POST',
        body: JSON.stringify({ menuId, quantity })
      });
    },
    updateCartItem: async (cartItemId, quantityChange) => {
      return fetchApi(`/cart/items/${cartItemId}`, {
        method: 'PATCH',
        body: JSON.stringify({ quantity: quantityChange })
      });
    },
    removeCartItem: async (cartItemId) => {
      return fetchApi(`/cart/items/${cartItemId}`, {
        method: 'DELETE'
      });
    },
    signup: async (signupData) => {
      return fetchApi(`/users/auth/signup`, {
        method: 'POST',
        body: JSON.stringify(signupData)
      });
    },
    login: async (email, password) => {
      const res = await fetchApi(`/users/auth/login`, {
        method: 'POST',
        body: JSON.stringify({ email, password })
      });
      if (res.data && res.data.accessToken) {
        localStorage.setItem('accessToken', res.data.accessToken);
        if (res.data.refreshToken) localStorage.setItem('refreshToken', res.data.refreshToken);
      }
      return res;
    },
    logout: () => {
      localStorage.removeItem('accessToken');
      localStorage.removeItem('refreshToken');
    },
    isAuthenticated: () => {
      return !!localStorage.getItem('accessToken');
    },
    getKakaoLoginUrl: async () => {
      return fetchApi('/users/oauth/kakao/login');
    },
    kakaoCallback: async (code) => {
      const res = await fetchApi(`/users/oauth/kakao/callback?code=${code}`);
      if (res && res.accessToken) {
        localStorage.setItem('accessToken', res.accessToken);
        if (res.refreshToken) localStorage.setItem('refreshToken', res.refreshToken);
      }
      return res;
    }
  };
}

export const api = createApi();
