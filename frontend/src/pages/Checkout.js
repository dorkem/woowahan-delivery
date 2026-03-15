import { createInput } from '../components/Input.js';
import { createButton } from '../components/Button.js';
import { createCard } from '../components/Card.js';
import { api } from '../services/api.js';

export async function renderCheckout(container) {
  let cartData = null;
  
  container.innerHTML = '<div class="loader-container"><div class="loader"></div></div>';
  
  try {
    const res = await api.getCart();
    cartData = res.data;
  } catch (err) {
    console.error("Failed to load cart for checkout", err);
    container.innerHTML = '<div style="padding: 20px; color: red;">Failed to load checkout data.</div>';
    return;
  }

  const items = cartData?.items || [];
  if (items.length === 0) {
    container.innerHTML = `
      <div style="text-align: center; padding: 100px 20px;">
        <h2 style="font-size: 1.5rem; margin-bottom: 20px;">Your cart is empty</h2>
        <button class="btn btn-primary" onclick="document.dispatchEvent(new CustomEvent('navigate', { detail: { page: 'home' }}))">Browse Stores</button>
      </div>
    `;
    return;
  }

  container.innerHTML = '';

  // Title
  const title = document.createElement('h1');
  title.className = 'section-title';
  title.textContent = 'Checkout';

  const layout = document.createElement('div');
  layout.className = 'checkout-layout';

  // Left Column - Forms
  const formColumn = document.createElement('div');
  formColumn.className = 'checkout-form-col';

  // 1. Delivery Address Card
  const addressCard = createCard({ className: 'checkout-section-card' });
  const addressHeader = document.createElement('div');
  addressHeader.className = 'card-header';
  addressHeader.innerHTML = `<h2 class="card-title">Delivery Address</h2>`;
  
  const addressBody = document.createElement('div');
  addressBody.className = 'card-body flex-col';
  
  addressBody.appendChild(createInput({ label: 'Base Address', placeholder: 'Select your address...', value: '123 Delivery Street, Tech City', required: true }));
  addressBody.appendChild(createInput({ label: 'Detailed Address', placeholder: 'Apt, Suite, Floor' }));
  addressBody.appendChild(createInput({ label: 'Entrance Access Method / Password', placeholder: 'e.g. *1234#' }));

  addressCard.appendChild(addressHeader);
  addressCard.appendChild(addressBody);

  // 2. Order Requests
  const requestCard = createCard({ className: 'checkout-section-card mt-lg' });
  const requestHeader = document.createElement('div');
  requestHeader.className = 'card-header';
  requestHeader.innerHTML = `<h2 class="card-title">Requests</h2>`;
  
  const requestBody = document.createElement('div');
  requestBody.className = 'card-body flex-col';
  
  requestBody.appendChild(createInput({ label: 'To Store', placeholder: 'e.g. Please make it extra spicy' }));
  requestBody.appendChild(createInput({ label: 'To Rider', placeholder: 'e.g. Please leave it at the door' }));
  
  // Options (from Swagger: noCutlery, noSideDish)
  const optionsDiv = document.createElement('div');
  optionsDiv.className = 'checkout-options mt-md';
  optionsDiv.innerHTML = `
    <label class="checkbox-label">
      <input type="checkbox" id="noCutlery" checked />
      <span>Environment Friendly: <strong>No Cutlery</strong></span>
    </label>
    <label class="checkbox-label">
      <input type="checkbox" id="noSideDish" />
      <span><strong>No Side Dishes</strong> (Kimchi, Pickles etc.)</span>
    </label>
  `;
  requestBody.appendChild(optionsDiv);

  requestCard.appendChild(requestHeader);
  requestCard.appendChild(requestBody);

  formColumn.appendChild(addressCard);
  formColumn.appendChild(requestCard);

  // Right Column - Summary & Payment
  const summaryColumn = document.createElement('div');
  summaryColumn.className = 'checkout-summary-col';

  const summaryCard = createCard({ className: 'checkout-summary-card' });
  
  const summaryHeader = document.createElement('div');
  summaryHeader.className = 'card-header';
  summaryHeader.innerHTML = `<h2 class="card-title">Order Summary</h2>`;
  
  const summaryBody = document.createElement('div');
  summaryBody.className = 'card-body summary-body';
  
  // Dynamic Order Items
  const itemsHtml = items.map(item => `
    <li class="summary-item">
      <span class="item-name">${item.menuName} <span class="text-muted">x${item.quantity}</span></span>
      <span class="item-price">${item.subtotal.toLocaleString()}원</span>
    </li>
  `).join('');

  const orderAmount = cartData.totalPrice || 0;
  const deliveryFee = 3000; // Mock delivery fee for now
  const totalPay = orderAmount + deliveryFee;

  summaryBody.innerHTML = `
    <ul class="summary-items">
      ${itemsHtml}
    </ul>
    
    <div class="summary-calc mt-lg">
      <div class="calc-row">
        <span class="text-muted">Order Amount</span>
        <span>${orderAmount.toLocaleString()}원</span>
      </div>
      <div class="calc-row">
        <span class="text-muted">Delivery Fee</span>
        <span>${deliveryFee.toLocaleString()}원</span>
      </div>
    </div>
  `;

  const summaryFooter = document.createElement('div');
  summaryFooter.className = 'card-footer summary-footer flex-col';
  
  summaryFooter.innerHTML = `
    <div class="summary-total row-between mb-md">
      <span>Total to Pay</span>
      <span class="total-price text-primary">${totalPay.toLocaleString()}원</span>
    </div>
  `;

  // Submit Button
  const payBtn = createButton({ text: `Pay ${totalPay.toLocaleString()}원`, variant: 'primary', size: 'lg', className: 'btn-full' });
  payBtn.classList.add('w-full', 'pay-btn');
  
  payBtn.onclick = async () => {
    try {
      // Basic visual feedback
      payBtn.innerHTML = '<div class="loader" style="width:20px; height:20px; border-width:2px; display:block; margin:auto;"></div>';
      payBtn.disabled = true;

      // Construct payload according to Swagger OrderCreateRequest
      const payload = {
        storeId: cartData.storeId,
        items: items.map(item => ({ menuId: item.menuId, quantity: item.quantity })),
        deliveryAddressRequest: {
          address: '123 Delivery Street, Tech City',
          addressDetail: 'Apt 4B',
          requestToRider: 'Please leave it at the door',
          entranceAccessPassword: '*1234#',
          deliveryDirections: ''
        },
        requestToStore: 'Please make it extra spicy',
        noCutlery: document.getElementById('noCutlery')?.checked || false,
        noSideDish: document.getElementById('noSideDish')?.checked || false
      };

      await api.createOrder(payload);
      
      // Navigate to tracker
      // If the API returns a simulated order Id, we'd use that. Fallback to ORD-12345.
      document.dispatchEvent(new CustomEvent('navigate', { detail: { page: 'tracker', id: 'ORD-12345' }}));
      
    } catch (err) {
      console.error(err);
      alert('Payment / Order formulation failed. Ensure backend localhost:8080 is running.');
      payBtn.textContent = 'Retry Payment';
      payBtn.disabled = false;
    }
  };

  summaryFooter.appendChild(payBtn);
  
  summaryCard.appendChild(summaryHeader);
  summaryCard.appendChild(summaryBody);
  summaryCard.appendChild(summaryFooter);
  
  summaryColumn.appendChild(summaryCard);

  layout.appendChild(formColumn);
  layout.appendChild(summaryColumn);

  container.appendChild(title);
  container.appendChild(layout);
}
